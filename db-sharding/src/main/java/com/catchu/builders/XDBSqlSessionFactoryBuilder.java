package com.catchu.builders;

import com.catchu.beans.XDBAbsSqlsessionFactory;
import com.catchu.beans.XDBDuridDataSource;
import com.catchu.beans.XDBSqlsessionFactory;
import com.catchu.constants.XDBConstant;
import lombok.Data;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Data
public class XDBSqlSessionFactoryBuilder extends XDBBuilder<Object> {

    private List<XDBAbsSqlsessionFactory> XDBAbsSqlsessionFactoryList = null;

    private static class Assistant {
        final static XDBSqlSessionFactoryBuilder sqlSessionFactory = new XDBSqlSessionFactoryBuilder();
    }

    public static XDBSqlSessionFactoryBuilder getInstance() {
        return Assistant.sqlSessionFactory;
    }

    @Override
    public void build(Object params) {
        List<DataSource> dataSourceList = new XDBDuridDataSource(XDBConstant.KOALA_DISDB_PROPERTIES).getDataSourceW();
        XDBAbsSqlsessionFactoryList = new ArrayList<>();
        for (DataSource dataSource : dataSourceList) {
            XDBAbsSqlsessionFactoryList.add(new XDBSqlsessionFactory(dataSource));
        }
    }
}
