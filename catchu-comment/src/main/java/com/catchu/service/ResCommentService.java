package com.catchu.service;

import com.alibaba.fastjson.JSONObject;
import com.catchu.beans.enums.ResourceTypeEnum;
import com.catchu.beans.model.ResCommentRecordModel;
import com.catchu.beans.ro.*;
import com.catchu.dao.ResCommentDaoForDB;
import com.catchu.dao.ResCommentDaoForES;
import com.catchu.helper.ResCommentHelper;
import com.catchu.proxys.XDBProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 评论组件SERVICE
 */
@Slf4j
@Service
public class ResCommentService {

    @Autowired
    private ResCommentDaoForES resCommentDaoForES;

    private ResCommentHelper resCommentHelper = new ResCommentHelper();

    public int insert(ResCommentInsertRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "insert", JSONObject.toJSONString(param));

        /*
         * DB
         */
        ResCommentRecordModel rcr = new ResCommentRecordModel();
        BeanUtils.copyProperties(param, rcr);
        rcr.setId(resCommentHelper.generatePrimaryKey(rcr.getResourceId(), rcr.getResourceType()));

        int dbNumber = resCommentHelper.generateDbNumber(rcr.getResourceId(), rcr.getResourceType());
        String tableName = resCommentHelper.generateTableName(rcr.getResourceId(), rcr.getResourceType());
        ResCommentDaoForDB resCommentDaoForDB = XDBProxy.getInstance(dbNumber, ResCommentDaoForDB.class).getProxy();
        log.info("uuid:{}] dbNumber:{}] tableName:{}]", param.getUuid(), dbNumber, tableName);

        int result = resCommentDaoForDB.insert(tableName, rcr);

        /*
         * ES
         */
        String index = resCommentHelper.generateEsIndex(ResourceTypeEnum.getById(rcr.getResourceType()), param.getCreateTime());
        String type = resCommentHelper.generateEsType(index);
        ResCommentRecordRO rcrRO = new ResCommentRecordRO();
        rcrRO.setIndex(index);
        rcrRO.setType(type);
        rcrRO.setRecordModel(rcr);
        resCommentDaoForES.saveComment(rcrRO);

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "insert", JSONObject.toJSONString(result));

        return result;
    }

    public int insertMulti(List<ResCommentInsertRO> params) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        log.info("uuid:{} - desc:{} - params:{}", uuid, "insertMulti", JSONObject.toJSONString(params));

        // 计数
        AtomicInteger count = new AtomicInteger();

        // 转换
        List<ResCommentRecordModel> rcrs = new ArrayList<>();
        for (ResCommentInsertRO param : params) {
            ResCommentRecordModel rcr = new ResCommentRecordModel();
            BeanUtils.copyProperties(param, rcr);
            rcr.setId(resCommentHelper.generatePrimaryKey(rcr.getResourceId(), rcr.getResourceType()));

            rcrs.add(rcr);
        }

        /*
         * DB
         */
        // 按db归类
        Map<Integer, List<ResCommentRecordModel>> rcrsDbMap = new HashMap<>();
        for (ResCommentRecordModel rcr : rcrs) {
            int dbNumber = resCommentHelper.generateDbNumber(rcr.getResourceId(), rcr.getResourceType());
            rcrsDbMap.putIfAbsent(dbNumber, new ArrayList<>());
            rcrsDbMap.get(dbNumber).add(rcr);
        }
        rcrsDbMap.forEach((dbNumber, rcrsDbList) -> {
            // 按表归类
            Map<String, List<ResCommentRecordModel>> rcrsTableMap = new HashMap<>();
            for (ResCommentRecordModel rcr : rcrsDbList) {
                String tableName = resCommentHelper.generateTableName(rcr.getResourceId(), rcr.getResourceType());
                rcrsTableMap.putIfAbsent(tableName, new ArrayList<>());
                rcrsTableMap.get(tableName).add(rcr);
            }
            // 根据db和表批量插入
            rcrsTableMap.forEach((tableName, rcrsTableList) -> {
                ResCommentDaoForDB resCommentDaoForDB = XDBProxy.getInstance(dbNumber, ResCommentDaoForDB.class).getProxy();
                int c = resCommentDaoForDB.insertMulti(tableName, rcrsTableList);
                count.addAndGet(c);
            });
        });

        /*
         * ES
         */
        List<ResCommentRecordRO> ros = new ArrayList<>();
        for (ResCommentRecordModel rcr : rcrs) {
            String index = resCommentHelper.generateEsIndex(ResourceTypeEnum.getById(rcr.getResourceType()), rcr.getCreateTime());
            String type = resCommentHelper.generateEsType(index);
            ResCommentRecordRO rcrRO = new ResCommentRecordRO();
            rcrRO.setIndex(index);
            rcrRO.setType(type);
            rcrRO.setRecordModel(rcr);

            ros.add(rcrRO);
        }
        resCommentDaoForES.saveComment(ros);

        log.info("uuid:{} - desc:{} - result:{}", uuid, "insertMulti", JSONObject.toJSONString(count.get()));

        return count.get();
    }

    public List<ResCommentRecordModel> list(ResCommentListRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "list", JSONObject.toJSONString(param));

        int dbNumber = resCommentHelper.generateDbNumber(param.getResourceId(), param.getResourceType());
        String tableName = resCommentHelper.generateTableName(param.getResourceId(), param.getResourceType());
        ResCommentDaoForDB resCommentDaoForDB = XDBProxy.getInstance(dbNumber, ResCommentDaoForDB.class).getProxy();

        List<ResCommentRecordModel> result = null;
        if (param.getListType() == 1) {
            result = resCommentDaoForDB.list1(tableName, param.buildPageQuery(), param.getResourceId(), param.getResourceType(), param.getVerifyStatus(), param.getRecordStatus());
        } else if (param.getListType() == 2) {
            result = resCommentDaoForDB.list2(tableName, param.buildPageQuery(), param.getResourceId(), param.getResourceType(), param.getVerifyStatus(), param.getRecordStatus());
        }

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "list", JSONObject.toJSONString(result));

        return result;
    }

    public List<ResCommentRecordModel> lastByResourceAndUser(ResCommentLastRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "lastByResourceAndUser", JSONObject.toJSONString(param));

        int dbNumber = resCommentHelper.generateDbNumber(param.getResourceId(), param.getResourceType());
        String tableName = resCommentHelper.generateTableName(param.getResourceId(), param.getResourceType());
        ResCommentDaoForDB resCommentDaoForDB = XDBProxy.getInstance(dbNumber, ResCommentDaoForDB.class).getProxy();

        List<ResCommentRecordModel> result = resCommentDaoForDB.lastByResourceAndUser(tableName, param.getResourceId(), param.getResourceType(), param.getUserId(), param.getVerifyStatus(), param.getRecordStatus());

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "lastByResourceAndUser", JSONObject.toJSONString(result));

        return result;
    }

    public int count(ResCommentCountRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "count", JSONObject.toJSONString(param));

        int dbNumber = resCommentHelper.generateDbNumber(param.getResourceId(), param.getResourceType());
        String tableName = resCommentHelper.generateTableName(param.getResourceId(), param.getResourceType());
        ResCommentDaoForDB resCommentDaoForDB = XDBProxy.getInstance(dbNumber, ResCommentDaoForDB.class).getProxy();

        int c = resCommentDaoForDB.count(tableName, param.getResourceId(), param.getResourceType(), param.getVerifyStatus(), param.getRecordStatus());

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "count", JSONObject.toJSONString(c));

        return c;
    }

//    public int increaseUpvoteNum(ResCommentIncreaseUpvoteNumRO param) {
//        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "increaseUpvoteNum", JSONObject.toJSONString(param));
//
//        int dbNumber = resCommentHelper.generateDbNumber(param.getResourceId(), param.getResourceType());
//        String tableName = resCommentHelper.generateTableName(param.getResourceId(), param.getResourceType());
//        ResCommentDaoForDB resCommentDaoForDB = XDBProxy.getInstance(dbNumber, ResCommentDaoForDB.class).getProxy();
//
//        int c = resCommentDaoForDB.increaseUpvoteNum(tableName, param.getId(), param.getIncNum());
//
//        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "increaseUpvoteNum", JSONObject.toJSONString(c));
//
//        return c;
//    }
}
