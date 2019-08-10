package com.catchu.beans.es;

import com.catchu.common.beans.BaseRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ESRO extends BaseRO {

    private String index;
    private String type;
    private int shardNum;
    private int replicaNum;
}
