package com.catchu.constants;

import java.util.Properties;

public class XDBConstant {

    public final static String DEFAULT_DISDB_MAPPER_SCAN_PACKAGE = "com.catchu";

    public final static String KOALA_DISDB_DBCOUNT = "koala.disdb.{}.dbCount";

    public final static String KOALA_DISDB_TBCOUNT = "koala.disdb.{}.tbCount";

    public final static String KOALA_DISDB_DRIVERCLASSNAME = "koala.disdb.{}.driverClassName";

    public final static String KOALA_DISDB_DOMAIN = "koala.disdb.{}.domain";

    public final static String KOALA_DISDB_DATABASE = "koala.disdb.{}.database";

    public final static String KOALA_DISDB_USERNAME = "koala.disdb.{}.userName";

    public final static String KOALA_DISDB_PASSWORD = "koala.disdb.{}.password";

    public final static String KOALA_DISDB_INITIALSIZE = "koala.disdb.{}.initialSize";

    public final static String KOALA_DISDB_INITIALSIZE_DEFAULT = "5";

    public final static String KOALA_DISDB_MINIDLE = "koala.disdb.{}.minIdle";

    public final static String KOALA_DISDB_MINIDLE_DEFAULT = "5";

    public final static String KOALA_DISDB_MAXACTIVE = "koala.disdb.{}.maxActive";

    public final static String KOALA_DISDB_MAXACTIVE_DEFAULT = "10";

    public final static String KOALA_DISDB_MAXWAIT = "koala.disdb.{}.maxWait";

    public final static String KOALA_DISDB_MAXWAIT_DEFAULT = "5000";

    public final static String KOALA_DISDB_TIMEBETWEENEVICTIONRUNSMILLIS = "koala.disdb.{}.timeBetweenEvictionRunsMillis";

    public final static String KOALA_DISDB_TIMEBETWEENEVICTIONRUNSMILLIS_DEFAULT = "60000";

    public final static String KOALA_DISDB_MINEVICTABLEIDLETIMEMILLIS = "koala.disdb.{}.minEvictableIdleTimeMillis";

    public final static String KOALA_DISDB_MINEVICTABLEIDLETIMEMILLIS_DEFAULT = "300000";

    public final static String KOALA_DISDB_VALIDATIONQUERY = "koala.disdb.{}.validationQuery";

    public final static String KOALA_DISDB_VALIDATIONQUERY_DEFAULT = "SELECT 1";

    public final static String KOALA_DISDB_TESTWHILEIDLE = "koala.disdb.{}.testWhileIdle";

    public final static String KOALA_DISDB_TESTWHILEIDLE_DEFAULT = "true";

    public final static String KOALA_DISDB_TESTONBORROW = "koala.disdb.{}.testOnBorrow";

    public final static String KOALA_DISDB_TESTONBORROW_DEFAULT = "false";

    public final static String KOALA_DISDB_TESTONRETURN = "koala.disdb.{}.testOnReturn";

    public final static String KOALA_DISDB_TESTONRETURN_DEFAULT = "false";

    public final static String KOALA_DISDB_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE = "koala.disdb.{}.maxPoolPreparedStatementPerConnectionSize";

    public final static String KOALA_DISDB_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE_DEFAULT = "20";

    public final static String KOALA_DISDB_FILTERS = "koala.disdb.{}.filters";

    public final static String KOALA_DISDB_FILTERS_DEFAULT = "config,stat,wall,log4j";

    public final static String KOALA_DISDB_MAPCAMEL = "koala.disdb.{}.mapCamel";

    public final static String KOALA_DISDB_MAPCAMEL_DEFAULT = "true";

    public final static String KOALA_DISDB_URL_MYSQL = "jdbc:mysql://{domainName}-{dbNum}.catchu.com:{port}/{database}?serverTimezone=GMT%2B8&useUnicode=true&allowMultiQueries=true&autoReconnect=true&useSSL=true";

    public final static String KOALA_DISDB_PORT_DEFAULT = "3306";

    public final static String KOALA_DISDB_PORT = "koala.disdb.{}.port";

    public static Properties KOALA_DISDB_PROPERTIES = null;

    public static String KOALA_DISDB_MAPPER_SCAN_PACKAGES = "";

    public static String KOALA_DISDB_INIT_ON_START = "koala.disdb.initOnStart";
}
