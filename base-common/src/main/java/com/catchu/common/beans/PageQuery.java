package com.catchu.common.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = -7671373784246394500L;

    public static final int DEFAULT_PAGE = 1;

    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final int DEFAULT_TOP_SIZE = 10;

    private int page;

    private int pageSize;

    private int skipSize;

    private PageQuery(int page, int pageSize, int skipSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.skipSize = skipSize;
    }

    public static PageQuery build(Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            return null;
        }

        page = page > 0 ? page : DEFAULT_PAGE;
        pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
        int skipSize = (page - 1) * pageSize;

        return new PageQuery(page, pageSize, skipSize);
    }
}
