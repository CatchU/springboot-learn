package com.catchu.common.beans;

import lombok.Data;

@Data
public class PageQueryParam extends BaseRO {

    private Integer page;

    private Integer pageSize;

    public PageQuery buildPageQuery() {
        return PageQuery.build(page, pageSize);
    }
}
