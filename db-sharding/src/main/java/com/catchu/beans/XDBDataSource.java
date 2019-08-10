package com.catchu.beans;

import lombok.Data;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Data
public abstract class XDBDataSource<T> {

    private T params;

    public XDBDataSource(T params) {
        this.params = params;
    }

    public abstract List<DataSource> getDataSourceW();

    public abstract List<Map<Integer, DataSource>> getDataSourceR();
}
