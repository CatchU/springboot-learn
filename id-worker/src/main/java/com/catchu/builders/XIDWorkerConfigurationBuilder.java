package com.catchu.builders;

import com.catchu.beans.XIDWorkerConfigurationBean;
import com.catchu.constants.XIDWorkerConstant;
import com.catchu.utils.IPUtil;
import com.catchu.utils.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.base.Strings;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * DB配置参数创建者
 */
@Data
@Slf4j
public class XIDWorkerConfigurationBuilder extends XBuilder<Environment, XIDWorkerConfigurationBean> {

    private XIDWorkerConfigurationBean xIDWorkerConfigurationBean;

    @Override
    public XIDWorkerConfigurationBean build(Environment environment) {
        String env = environment.getActiveProfiles()[0];
        Properties pro = null;
        Properties proDefault = null;
        Properties proMain = null;
        pro = PropertiesUtil.getProperties(("application-" + env + ".properties"));
        proMain = PropertiesUtil.getProperties(("application.properties"));
        proDefault = PropertiesUtil.getProperties(("application-" + env + "-default.properties"));
        ;
        String clusterName = pro.getProperty(XIDWorkerConstant.KOALA_CLUSTERNAME);
        String servers = pro.getProperty(XIDWorkerConstant.KOALA_IDWORKER_ZK_SERVERS);
        String sessionTimeout = pro.getProperty(XIDWorkerConstant.KOALA_IDWORKER_ZK_SESSIONTIMEOUT);
        String connectionTimeout = pro.getProperty(XIDWorkerConstant.KOALA_IDWORKER_ZK_CONNECTIONTIMEOUT);
        String dataCenterIndex = pro.getProperty(XIDWorkerConstant.KOALA_IDWORKER_DATACENTER);
        String namespace = pro.getProperty(XIDWorkerConstant.KOALA_IDWORKER_ZK_NAMESPACE);
        String port = pro.getProperty(XIDWorkerConstant.KOALA_SERVER_PORT);
        port = Strings.isNullOrEmpty(port) ? proMain.getProperty(XIDWorkerConstant.KOALA_SERVER_PORT) : port;
        String ip = IPUtil.getCurrentServerIp();
        if (Strings.isNullOrEmpty(port)) {
            port = proDefault.getProperty(XIDWorkerConstant.KOALA_SERVER_PORT);
        }
        if (Strings.isNullOrEmpty(sessionTimeout)) {
            sessionTimeout = proDefault.getProperty(XIDWorkerConstant.KOALA_IDWORKER_ZK_SESSIONTIMEOUT);
        }
        if (Strings.isNullOrEmpty(connectionTimeout)) {
            connectionTimeout = proDefault.getProperty(XIDWorkerConstant.KOALA_IDWORKER_ZK_CONNECTIONTIMEOUT);
        }
        try {
            if (Strings.isNullOrEmpty(clusterName)) {
                throw new Exception("cantnot find validate clusterName");
            }
            if (Strings.isNullOrEmpty(servers)) {
                log.info("can't find validate zkservers,use default zkservers configuration");
                servers = proDefault.getProperty(XIDWorkerConstant.KOALA_IDWORKER_ZK_SERVERS);
            }
            if (Strings.isNullOrEmpty(ip)) {
                throw new Exception("cantnot get validate ip info");
            }
            xIDWorkerConfigurationBean = new XIDWorkerConfigurationBean();
            xIDWorkerConfigurationBean.setClusterName(clusterName);
            xIDWorkerConfigurationBean.setServers(servers);
            xIDWorkerConfigurationBean.setSessionTimeoutMs(Strings.isNullOrEmpty(sessionTimeout) ? XIDWorkerConstant.XZK_SESSION_TIMEOUT_MS_DEFAULT : Integer.valueOf(sessionTimeout));
            xIDWorkerConfigurationBean.setConnectionTimeoutMs(Strings.isNullOrEmpty(connectionTimeout) ? XIDWorkerConstant.XZK_CONNECTION_TIMEOUT_MS_DEFAULT : Integer.valueOf(connectionTimeout));
            xIDWorkerConfigurationBean.setDataCenterIndex(Strings.isNullOrEmpty(dataCenterIndex) ? XIDWorkerConstant.DATA_CENTER_DEFAULT : Integer.valueOf(dataCenterIndex));
            xIDWorkerConfigurationBean.setNamespace(Strings.isNullOrEmpty(namespace) ? XIDWorkerConstant.KOALA_IDWORKER_ZK_NAMESPACE_DEFAULT : namespace);
            xIDWorkerConfigurationBean.setPort(Integer.valueOf(port));
            xIDWorkerConfigurationBean.setIp(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xIDWorkerConfigurationBean;
    }

    private XIDWorkerConfigurationBuilder() {
    }

    private static class Assistant {
        final static XIDWorkerConfigurationBuilder builder = new XIDWorkerConfigurationBuilder();
    }

    public static XIDWorkerConfigurationBuilder getInstance() {
        return Assistant.builder;
    }
}
