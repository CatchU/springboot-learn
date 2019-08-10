package com.catchu.beans.ro;

import com.catchu.beans.model.ResCommentRecordModel;
import com.catchu.common.beans.BaseRO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class ResCommentRecordRO extends BaseRO {

    private String index;

    private String type;

    private ResCommentRecordModel recordModel;

    private int startIndex;

    private int pageSize;

}
