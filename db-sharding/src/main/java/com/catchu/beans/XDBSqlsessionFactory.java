package com.catchu.beans;

import com.catchu.constants.XDBConstant;
import com.catchu.enums.XDBEnvEnums;
import com.catchu.utils.MapperScannerUtil;
import com.google.common.base.Strings;
import lombok.Data;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class XDBSqlsessionFactory extends XDBAbsSqlsessionFactory<DataSource> {
    private SqlSessionFactory sqlSessionFactory;

    public XDBSqlsessionFactory(DataSource dataSource) {
        super(dataSource);
        initSqlsessionFactory();
    }

    @Override
    public SqlSessionFactory getSqlsessionFactoryW() {
        return sqlSessionFactory;
    }

    private void initSqlsessionFactory() {
        DataSource dataSource = getParams();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        org.apache.ibatis.mapping.Environment environment = new org.apache.ibatis.mapping.Environment(XDBEnvEnums.WRITE.getEnviromentType().toLowerCase(), transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        String mapCamel = XDBConstant.KOALA_DISDB_PROPERTIES.getProperty(XDBConstant.KOALA_DISDB_MAPCAMEL.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
        configuration.setMapUnderscoreToCamelCase(Strings.isNullOrEmpty(mapCamel) ? Boolean.valueOf(XDBConstant.KOALA_DISDB_MAPCAMEL_DEFAULT) : Boolean.valueOf(mapCamel));
        ConcurrentHashMap mappers = MapperScannerUtil.getInstance().getMappers();
        Iterator<Class<?>> classIterator = mappers.values().iterator();
        while (classIterator.hasNext()) {
            configuration.addMapper(classIterator.next());
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }
}
