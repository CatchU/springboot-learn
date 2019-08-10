package com.catchu.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.catchu.beans.es.ESRO;
import com.catchu.beans.model.ResCommentRecordModel;
import com.catchu.beans.ro.ResCommentRecordRO;
import com.catchu.config.ESAdminComponent;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * ES 评论业务 DAO组件
 */
@Slf4j
@Component
public class ResCommentDaoForES {

    /**
     * 持久化评论
     *
     * @param ro
     * @return
     */
    public boolean saveComment(ResCommentRecordRO ro) {
        log.info("uuid:{},desc:{},params:{}", ro.getUuid(), "持久化评论[DAO][ES]", JSONObject.toJSONString(ro));
        ESRO esro = new ESRO();
        esro.setIndex(ro.getIndex());
        esro.setType(ro.getType());
        esro.setShardNum(5);
        esro.setReplicaNum(2);
        if (checkIndexExist(esro)) {
            log.info("desc:index:{} is exist[DAO][ES]", ro.getIndex());
        } else {
            log.info("desc:index:{} is not exist[DAO][ES]", ro.getIndex());
            boolean isCreated = createIndex(esro);
            log.info("desc:create result : {}[DAO][ES]", isCreated);
        }
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("resourceId", ro.getRecordModel().getResourceId().longValue());
                builder.field("resourceType", ro.getRecordModel().getResourceType().intValue());
                builder.field("userId", ro.getRecordModel().getUserId().longValue());
                builder.field("userType", ro.getRecordModel().getUserType().intValue());
                builder.field("verifyStatus", ro.getRecordModel().getVerifyStatus().intValue());
                builder.field("verifyChannel", ro.getRecordModel().getVerifyChannel().intValue());
                builder.field("recordStatus", ro.getRecordModel().getRecordStatus().intValue());
                builder.field("createTime", sdf.format(ro.getRecordModel().getCreateTime()));
                builder.field("contentType", ro.getRecordModel().getContentType().intValue());
                builder.field("contentOriginal", ro.getRecordModel().getContentOriginal());
                builder.field("contentShow", ro.getRecordModel().getContentShow());
            }
            builder.endObject();
            IndexRequest indexRequest = new IndexRequest(ro.getIndex(), ro.getType(), ro.getRecordModel().getId().toString())
                    .source(builder);
            IndexResponse indexResponse = ESAdminComponent.getInstance().getRestHighLevelClient().index(indexRequest, RequestOptions.DEFAULT);
            log.info("uuid:{},desc:{},params:{}", ro.getUuid(), "持久化评论[DAO][ES][SAVE]", JSONObject.toJSONString(indexResponse));
            result = true;
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        log.info("uuid:{},desc:{},result:{}", ro.getUuid(), "持久化评论[DAO][ES]", result);
        return result;
    }

    /**
     * 持久化评论，批量插入
     */
    public boolean saveComment(List<ResCommentRecordRO> ros) {
        log.info("desc:{},params:{}", "持久化评论[DAO][ES]", JSONArray.toJSONString(ros));
        for (ResCommentRecordRO ro : ros) {
            ESRO esro = new ESRO();
            esro.setIndex(ro.getIndex());
            esro.setType(ro.getType());
            esro.setShardNum(5);
            esro.setReplicaNum(2);
            if (checkIndexExist(esro)) {
                log.info("desc:index:{} is exist[DAO][ES]", ro.getIndex());
            } else {
                log.info("desc:index:{} is not exist[DAO][ES]", ro.getIndex());
                boolean isCreated = createIndex(esro);
                log.info("desc:create result : {}[DAO][ES]", isCreated);
            }
        }
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            BulkRequest bulkRequest = new BulkRequest();
            for (ResCommentRecordRO ro : ros) {
                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject();
                {
                    builder.field("resourceId", ro.getRecordModel().getResourceId().longValue());
                    builder.field("resourceType", ro.getRecordModel().getResourceType().intValue());
                    builder.field("userId", ro.getRecordModel().getUserId().longValue());
                    builder.field("userType", ro.getRecordModel().getUserType().intValue());
                    builder.field("verifyStatus", ro.getRecordModel().getVerifyStatus().intValue());
                    builder.field("verifyChannel", ro.getRecordModel().getVerifyChannel().intValue());
                    builder.field("recordStatus", ro.getRecordModel().getRecordStatus().intValue());
                    builder.field("createTime", sdf.format(ro.getRecordModel().getCreateTime()));
                    builder.field("contentType", ro.getRecordModel().getContentType().intValue());
                    builder.field("contentOriginal", ro.getRecordModel().getContentOriginal());
                    builder.field("contentShow", ro.getRecordModel().getContentShow());
                }
                builder.endObject();

                IndexRequest indexRequest = new IndexRequest(ro.getIndex(), ro.getType(), ro.getRecordModel().getId().toString())
                        .source(builder);
                bulkRequest.add(indexRequest);
            }

            BulkResponse bulkResponse = ESAdminComponent.getInstance().getRestHighLevelClient().bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("desc:{},params:{}", "持久化评论[DAO][ES][SAVE]", JSONObject.toJSONString(bulkResponse));
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("desc:{},result:{}", "持久化评论[DAO][ES]", result);
        return result;
    }

    /**
     * 更新评论上下线状态
     *
     * @param ro
     * @return
     */
    public boolean updateCommentStatus(ResCommentRecordRO ro) {
        log.info("uuid:{},desc:{},params:{}", ro.getUuid(), "更新评论上下线状态[DAO][ES]", JSONObject.toJSONString(ro));
        boolean result = false;
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(ro.getIndex());
            updateRequest.type(ro.getType());
            updateRequest.id(ro.getRecordModel().getId().toString());
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                if (null != ro.getRecordModel().getRecordStatus()) {
                    builder.field("recordStatus", ro.getRecordModel().getRecordStatus());
                }
                if (null != ro.getRecordModel().getVerifyStatus()) {
                    builder.field("verifyStatus", ro.getRecordModel().getVerifyStatus());
                }
                if (null != ro.getRecordModel().getVerifyChannel()) {
                    builder.field("verifyChannel", ro.getRecordModel().getVerifyChannel());
                }
            }
            builder.endObject();
            updateRequest.doc(builder);
            UpdateResponse updateResponse = ESAdminComponent.getInstance().getRestHighLevelClient().update(updateRequest, RequestOptions.DEFAULT);
            result = (updateResponse.status() == RestStatus.OK);
            log.info("uuid:{},desc:{},params:{}", ro.getUuid(), "更新评论上下线状态[DAO][ES]", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("uuid:{},desc:{},result:{}", ro.getUuid(), "更新评论上下线状态[DAO][ES]", result);
        return true;
    }

    /**
     * 获取评论详情
     *
     * @param ro
     * @return
     */
    public ResCommentRecordModel getCommentRecordById(ResCommentRecordRO ro) {
        ResCommentRecordModel recordModel = null;
        try {
            GetRequest getRequest = new GetRequest();
            getRequest.index(ro.getIndex());
            getRequest.type(ro.getType());
            getRequest.id(ro.getRecordModel().getId().toString());
            GetResponse getResponse = ESAdminComponent.getInstance().getRestHighLevelClient().get(getRequest, RequestOptions.DEFAULT);
            recordModel = JSONObject.parseObject(getResponse.getSourceAsString(), ResCommentRecordModel.class);
            recordModel.setId(Long.valueOf(getResponse.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordModel;
    }

    /**
     * 分页查询评论记录
     *
     * @param ro
     */
    public void queryCommentsRecord(ResCommentRecordRO ro) {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("id", 10)));
            searchSourceBuilder.from(ro.getStartIndex()).size(ro.getPageSize());
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ro.getIndex());
            searchRequest.types(ro.getType());
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = ESAdminComponent.getInstance().getRestHighLevelClient().search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHit = searchResponse.getHits().getHits();
            for (SearchHit hit : searchHit) {
                ResCommentRecordModel recordModel = JSONObject.parseObject(hit.getSourceAsString(), ResCommentRecordModel.class);
                //TODO

            }
            System.out.println("======总记录数为=======>" + searchResponse.getHits().totalHits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 内部系统-分页查询评论记录
     *
     * @param ro
     */
    public SearchResponse queryCommentsRecord4Internal(ResCommentRecordRO ro) {
        log.info("uuid:{},desc:{},resourceType:{}", ro.getUuid(), "内部系统查询评论列表[DAO][ES]", ro.getType());
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("resourceType", ro.getRecordModel().getResourceType())));
            searchSourceBuilder.from(ro.getStartIndex()).size(ro.getPageSize());
            searchSourceBuilder.sort("createTime", SortOrder.DESC);
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ro.getIndex());
            searchRequest.types(ro.getType());
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = ESAdminComponent.getInstance().getRestHighLevelClient().search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("======总记录数为=======>" + searchResponse.getHits().totalHits);
            return searchResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 内部系统-更新评论上下线状态
     *
     * @param ro
     * @return
     */
    public boolean updateCommentStatus4Internal(ResCommentRecordRO ro) {
        log.info("uuid:{},desc:{},params:{}", ro.getUuid(), "内部系统更新评论上下线状态[DAO][ES]", JSONObject.toJSONString(ro));
        boolean result = false;
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(ro.getIndex());
            updateRequest.type(ro.getType());
            updateRequest.id(ro.getRecordModel().getId().toString());
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                if (null != ro.getRecordModel().getRecordStatus()) {
                    builder.field("recordStatus", ro.getRecordModel().getRecordStatus());
                }
                if (null != ro.getRecordModel().getVerifyStatus()) {
                    builder.field("verifyStatus", ro.getRecordModel().getVerifyStatus());
                }
                if (null != ro.getRecordModel().getVerifyChannel()) {
                    builder.field("verifyChannel", ro.getRecordModel().getVerifyChannel());
                }
            }
            builder.endObject();
            updateRequest.doc(builder);
            UpdateResponse updateResponse = ESAdminComponent.getInstance().getRestHighLevelClient().update(updateRequest, RequestOptions.DEFAULT);
            result = (updateResponse.status() == RestStatus.OK);
            log.info("uuid:{},desc:{},result:{}", ro.getUuid(), "内部系统审核评论状态结束[DAO][ES]", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateCommentStickStatus4Internal(ResCommentRecordRO ro) {
        boolean result = false;
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(ro.getIndex());
            updateRequest.type(ro.getType());
            updateRequest.id(ro.getRecordModel().getId().toString());
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                if (null != ro.getRecordModel().getStickStatus()) {
                    builder.field("stickStatus", ro.getRecordModel().getStickStatus());
                }
            }
            builder.endObject();
            updateRequest.doc(builder);
            UpdateResponse updateResponse = ESAdminComponent.getInstance().getRestHighLevelClient().update(updateRequest, RequestOptions.DEFAULT);
            result = (updateResponse.status() == RestStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkIndexExist(ESRO esro) {
        return ESAdminComponent.getInstance().existsIndex(esro);
    }

    public static boolean createIndex(ESRO esro) {
        return ESAdminComponent.getInstance().createIndex(esro);
    }
}
