package com.catchu.beans.model;

import com.catchu.beans.enums.ContentTypeEnum;
import com.catchu.beans.enums.VerifyStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论实体类
 */
@Data
@Accessors(chain = true)
public class ResCommentRecordModel implements Serializable {

    private static final long serialVersionUID = 1049786894595187328L;

    // 主键
    private Long id;

    // 资源ID
    private Long resourceId;

    // 资源类型
    private Integer resourceType;

    // 评论者ID
    private Long userId;

    // 评论者身份(1:学生 2:教师)
    private Integer userType;

    // 原生评论
    private String contentOriginal;

    // 最终评论(处理完)
    private String contentShow;

    /**
     * 评论类型
     *
     * @see ContentTypeEnum
     */
    private Integer contentType;

    /**
     * 审核状态
     *
     * @see VerifyStatusEnum
     */
    private Integer verifyStatus;

    // 审核渠道(1:人工 2:系统)
    private Integer verifyChannel;

    // 置顶状态(0:否 1:是)
    private Integer stickStatus;

    // 记录状态(0:无效 1:有效)
    private Integer recordStatus;

    // 点赞数
    private Long upvoteNum;

    // 创建时间
    private Date createTime;
}
