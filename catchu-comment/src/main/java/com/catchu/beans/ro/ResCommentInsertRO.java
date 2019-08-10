package com.catchu.beans.ro;

import com.catchu.common.beans.BaseRO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class ResCommentInsertRO extends BaseRO {

    // 资源ID
    private long resourceId;

    // 资源类型
    private int resourceType;

    // 评论者ID
    private long userId;

    // 评论者身份(1:学生 2:教师)
    private int userType;

    // 原生评论
    private String contentOriginal;

    // 最终评论(处理完)
    private String contentShow;

    /**
     * 评论类型
     * 1:文本
     * 2:图片
     * 3:语音
     */
    private int contentType;

    /**
     * 审核状态
     * 1:待审核
     * 2:审核不通过
     * 3:忽略
     * 4:通过)
     */
    private int verifyStatus;

    // 审核渠道(1:人工 2:系统)
    private int verifyChannel;

    // 置顶状态(0:否 1:是)
    private int stickStatus;

    // 记录状态(0:无效 1:有效)
    private int recordStatus;

    // 点赞数
    private long upvoteNum;

    // 创建时间
    private Date createTime;
}
