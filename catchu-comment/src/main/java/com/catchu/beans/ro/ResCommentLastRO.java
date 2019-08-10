package com.catchu.beans.ro;

import com.catchu.common.beans.BaseRO;
import lombok.Data;

import java.util.Set;

@Data
public class ResCommentLastRO extends BaseRO {

    private long resourceId;

    private int resourceType;

    private long userId;

    private Set<Integer> verifyStatus;

    private int recordStatus;
}
