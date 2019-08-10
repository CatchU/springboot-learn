package com.catchu.constants;

public class XIDWorkerConstant {

    /**
     * 数据中心编号
     */
    public final static Integer DATA_CENTER_DEFAULT = 0;
    /**
     * 机器编号
     */
    public static Integer SERVER_ID;

    /**
     * ZK SESSION 超时时间
     */
    public final static Integer XZK_SESSION_TIMEOUT_MS_DEFAULT = 60000;

    /**
     * ZK CONNECTION超时时间
     */
    public final static Integer XZK_CONNECTION_TIMEOUT_MS_DEFAULT = 60000;

    public final static String KOALA_CLUSTERNAME = "koala.clustername";

    public final static String KOALA_IDWORKER_ZK_SESSIONTIMEOUT = "koala.idworker.zk.sessiontimeout";

    public final static String KOALA_IDWORKER_ZK_CONNECTIONTIMEOUT = "koala.idworker.zk.connectiontimeout";

    public final static String KOALA_IDWORKER_ZK_SERVERS = "koala.idworker.zk.servers";

    public final static String KOALA_IDWORKER_DATACENTER = "koala.idworker.datacenter";

    public final static String KOALA_IDWORKER_ZK_NAMESPACE = "koala.idworker.zk.namespace";

    public final static String KOALA_IDWORKER_ZK_NAMESPACE_DEFAULT = "rd-server";

    public final static String KOALA_IDWORKER_ZK_BASEPATH = "/idWorker/{clusterName}";

    public final static String KOALA_IDWORKER_ZK_SERVERPATH = KOALA_IDWORKER_ZK_BASEPATH + "/{ip:port}";

    public final static Integer KOALA_SERVER_PORT_DEFAULT = 8080;

    public final static String KOALA_SERVER_PORT = "server.port";
}
