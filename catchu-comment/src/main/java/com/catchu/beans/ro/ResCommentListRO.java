package com.catchu.beans.ro;

import com.catchu.common.beans.PageQueryParam;
import lombok.Data;

import java.util.Set;

@Data
public class ResCommentListRO extends PageQueryParam {

    private long resourceId;

    private int resourceType;

    private Set<Integer> verifyStatus;

    private int recordStatus;

    private int listType;
}
