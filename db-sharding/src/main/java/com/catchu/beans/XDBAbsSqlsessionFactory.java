package com.catchu.beans;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;

@Data
public abstract class XDBAbsSqlsessionFactory<T> {

    private T params;

    public XDBAbsSqlsessionFactory() {
    }

    public XDBAbsSqlsessionFactory(T params) {
        this.params = params;
    }

    public abstract SqlSessionFactory getSqlsessionFactoryW();
}
