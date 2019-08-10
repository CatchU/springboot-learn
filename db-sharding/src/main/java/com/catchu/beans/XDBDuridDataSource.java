package com.catchu.beans;

import com.alibaba.druid.pool.DruidDataSource;
import com.catchu.constants.XDBConstant;
import com.catchu.enums.XDBEnvEnums;
import com.google.common.base.Strings;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class XDBDuridDataSource extends XDBDataSource<Properties> {

    private List<DataSource> dataSourcesList = null;

    public XDBDuridDataSource(Properties params) {
        super(params);
        initDataSourceW();
    }

    @Override
    public List<DataSource> getDataSourceW() {
        return dataSourcesList;
    }

    private void initDataSourceW() {
        try {
            dataSourcesList = new ArrayList<>();
            Properties properties = getParams();
            String driverClassName = properties.getProperty(XDBConstant.KOALA_DISDB_DRIVERCLASSNAME.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String userName = properties.getProperty(XDBConstant.KOALA_DISDB_USERNAME.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String password = properties.getProperty(XDBConstant.KOALA_DISDB_PASSWORD.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String domain = properties.getProperty(XDBConstant.KOALA_DISDB_DOMAIN.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String database = properties.getProperty(XDBConstant.KOALA_DISDB_DATABASE.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String port = properties.getProperty(XDBConstant.KOALA_DISDB_PORT.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            port = Strings.isNullOrEmpty(port) ? XDBConstant.KOALA_DISDB_PORT_DEFAULT : port;
            String url = XDBConstant.KOALA_DISDB_URL_MYSQL.replace("{domainName}", domain).replace("{port}", port).replace("{database}", database);
            String initailSize = properties.getProperty(XDBConstant.KOALA_DISDB_INITIALSIZE.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String minIdle = properties.getProperty(XDBConstant.KOALA_DISDB_MINIDLE.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String maxActive = properties.getProperty(XDBConstant.KOALA_DISDB_MAXACTIVE.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String maxWait = properties.getProperty(XDBConstant.KOALA_DISDB_MAXWAIT.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String timeBetweenEvictionRunsMillis = properties.getProperty(XDBConstant.KOALA_DISDB_TIMEBETWEENEVICTIONRUNSMILLIS.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String minEvictableIdleTimeMillis = properties.getProperty(XDBConstant.KOALA_DISDB_MINEVICTABLEIDLETIMEMILLIS.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String validationQuery = properties.getProperty(XDBConstant.KOALA_DISDB_VALIDATIONQUERY.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String testWhileIdle = properties.getProperty(XDBConstant.KOALA_DISDB_TESTWHILEIDLE.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String testOnBorrow = properties.getProperty(XDBConstant.KOALA_DISDB_TESTONBORROW.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String testOnReturn = properties.getProperty(XDBConstant.KOALA_DISDB_TESTONRETURN.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String maxPoolPreparedStatementPerConnectionSize = properties.getProperty(XDBConstant.KOALA_DISDB_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            String filters = properties.getProperty(XDBConstant.KOALA_DISDB_FILTERS.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase()));
            Integer dbCount = Integer.valueOf(properties.getProperty(XDBConstant.KOALA_DISDB_DBCOUNT.replace("{}", XDBEnvEnums.WRITE.getEnviromentType().toLowerCase())));
            for (int i = 0; i < dbCount; i++) {
                DruidDataSource dataSource = new DruidDataSource();
                dataSource.setDriverClassName(driverClassName);
                dataSource.setUsername(userName);
                dataSource.setPassword(password);
                dataSource.setUrl(url.replace("{dbNum}", String.valueOf(i)));
                dataSource.setInitialSize(Strings.isNullOrEmpty(initailSize) ? Integer.valueOf(XDBConstant.KOALA_DISDB_INITIALSIZE_DEFAULT) : Integer.valueOf(initailSize));
                dataSource.setMinIdle(Strings.isNullOrEmpty(minIdle) ? Integer.valueOf(XDBConstant.KOALA_DISDB_MINIDLE_DEFAULT) : Integer.valueOf(minIdle));
                dataSource.setMaxActive(Strings.isNullOrEmpty(maxActive) ? Integer.valueOf(XDBConstant.KOALA_DISDB_MAXACTIVE_DEFAULT) : Integer.valueOf(maxActive));
                dataSource.setMaxWait(Strings.isNullOrEmpty(maxWait) ? Integer.valueOf(XDBConstant.KOALA_DISDB_MAXWAIT_DEFAULT) : Integer.valueOf(maxWait));
                dataSource.setTimeBetweenEvictionRunsMillis(Strings.isNullOrEmpty(timeBetweenEvictionRunsMillis) ? Integer.valueOf(XDBConstant.KOALA_DISDB_TIMEBETWEENEVICTIONRUNSMILLIS_DEFAULT) : Integer.valueOf(timeBetweenEvictionRunsMillis));
                dataSource.setMinEvictableIdleTimeMillis(Strings.isNullOrEmpty(minEvictableIdleTimeMillis) ? Integer.valueOf(XDBConstant.KOALA_DISDB_MINEVICTABLEIDLETIMEMILLIS_DEFAULT) : Integer.valueOf(minEvictableIdleTimeMillis));
                dataSource.setValidationQuery(Strings.isNullOrEmpty(validationQuery) ? XDBConstant.KOALA_DISDB_VALIDATIONQUERY_DEFAULT : validationQuery);
                dataSource.setTestWhileIdle(Strings.isNullOrEmpty(testWhileIdle) ? Boolean.valueOf(XDBConstant.KOALA_DISDB_TESTWHILEIDLE_DEFAULT) : Boolean.valueOf(testWhileIdle));
                dataSource.setTestOnBorrow(Strings.isNullOrEmpty(testOnBorrow) ? Boolean.valueOf(XDBConstant.KOALA_DISDB_TESTONBORROW_DEFAULT) : Boolean.valueOf(testOnBorrow));
                dataSource.setTestOnReturn(Strings.isNullOrEmpty(testOnReturn) ? Boolean.valueOf(XDBConstant.KOALA_DISDB_TESTONRETURN_DEFAULT) : Boolean.valueOf(testOnReturn));
                dataSource.setMaxPoolPreparedStatementPerConnectionSize(Strings.isNullOrEmpty(maxPoolPreparedStatementPerConnectionSize) ? Integer.valueOf(XDBConstant.KOALA_DISDB_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE_DEFAULT) : Integer.valueOf(maxPoolPreparedStatementPerConnectionSize));
                dataSource.setFilters(Strings.isNullOrEmpty(filters) ? XDBConstant.KOALA_DISDB_FILTERS_DEFAULT : filters);
                dataSourcesList.add(dataSource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<Integer, DataSource>> getDataSourceR() {
        return null;
    }
}
