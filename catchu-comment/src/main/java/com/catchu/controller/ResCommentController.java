package com.catchu.controller;

import com.alibaba.fastjson.JSONObject;
import com.catchu.beans.model.ResCommentRecordModel;
import com.catchu.beans.ro.ResCommentCountRO;
import com.catchu.beans.ro.ResCommentInsertRO;
import com.catchu.beans.ro.ResCommentLastRO;
import com.catchu.beans.ro.ResCommentListRO;
import com.catchu.service.ResCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * 评论组件CONTROLLER
 */
@Slf4j
@RestController
@RequestMapping("/comment")
public class ResCommentController {

    @Autowired
    private ResCommentService resCommentService;

    @PostMapping("/insert")
    public int insert(@RequestBody ResCommentInsertRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "insert", JSONObject.toJSONString(param));

        int result = resCommentService.insert(param);

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "insert", JSONObject.toJSONString(result));

        return result;
    }

    @PostMapping("/insertMulti")
    public int insertMulti(@RequestBody List<ResCommentInsertRO> params) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        log.info("uuid:{} - desc:{} - params:{}", uuid, "insertMulti", JSONObject.toJSONString(params));

        int result = resCommentService.insertMulti(params);

        log.info("uuid:{} - desc:{} - result:{}", uuid, "insertMulti", JSONObject.toJSONString(result));

        return result;
    }

    @PostMapping("/list")
    public List<ResCommentRecordModel> list(@RequestBody ResCommentListRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "list", JSONObject.toJSONString(param));

        List<ResCommentRecordModel> result = resCommentService.list(param);

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "list", JSONObject.toJSONString(result));

        return result;
    }

    @PostMapping("/lastByResourceAndUser")
    public List<ResCommentRecordModel> lastByResourceAndUser(@RequestBody ResCommentLastRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "lastByResourceAndUser", JSONObject.toJSONString(param));

        List<ResCommentRecordModel> result = resCommentService.lastByResourceAndUser(param);

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "lastByResourceAndUser", JSONObject.toJSONString(result));

        return result;
    }

    @PostMapping("/count")
    public int count(@RequestBody ResCommentCountRO param) {
        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "count", JSONObject.toJSONString(param));

        int result = resCommentService.count(param);

        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "count", JSONObject.toJSONString(result));

        return result;
    }

//    @PostMapping("/increaseUpvoteNum")
//    public int increaseUpvoteNum(@RequestBody ResCommentIncreaseUpvoteNumRO param) {
//        log.info("uuid:{} - desc:{} - params:{}", param.getUuid(), "increaseUpvoteNum", JSONObject.toJSONString(param));
//
//        int result = resCommentService.increaseUpvoteNum(param);
//
//        log.info("uuid:{} - desc:{} - result:{}", param.getUuid(), "increaseUpvoteNum", JSONObject.toJSONString(result));
//
//        return result;
//    }
}
