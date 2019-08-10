package com.catchu.builders;

import com.catchu.constants.XIDWorkerConstant;
import lombok.Data;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * XCurator创建者
 */
@Data
public class XCuratorBuilder extends XBuilder<String, CuratorFramework> {

    private CuratorFramework curatorFramework;

    @Override
    public CuratorFramework build(String params) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        curatorFramework =
                CuratorFrameworkFactory.builder()
                        .connectString(XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getServers())
                        .sessionTimeoutMs(XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getSessionTimeoutMs())
                        .connectionTimeoutMs(XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getConnectionTimeoutMs())
                        .retryPolicy(retryPolicy)
                        .namespace(XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getNamespace())
                        .build();
        curatorFramework.start();
        /**
         * 初始化
         */
        init();
        return curatorFramework;
    }

    private XCuratorBuilder() {
    }

    private static class Assistant {
        final static XCuratorBuilder builder = new XCuratorBuilder();
    }

    public static XCuratorBuilder getInstance() {
        return Assistant.builder;
    }

    private void init() {
        /**
         * 初始化基础路径
         */
        initBasePath();
        /**
         * 初始化服务路径
         */
        initServerPath();
        /**
         * 初始化服务ID
         */
        initServerId();
    }

    /**
     * 初始化基础路径
     */
    private void initBasePath() {
        String basePath = XIDWorkerConstant.KOALA_IDWORKER_ZK_BASEPATH.replace("{nameSpace}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getNamespace()).replace("{clusterName}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getClusterName());
        try {
            Stat stat = getCuratorFramework().checkExists().forPath(basePath);
            if (null == stat) {
                getCuratorFramework().create().creatingParentsIfNeeded().forPath(basePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化服务路径
     */
    private void initServerPath() {
        try {
            String ip = XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getIp();
            Integer port = XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getPort();
            String ipPort = ip + ":" + port;
            String basePath = XIDWorkerConstant.KOALA_IDWORKER_ZK_BASEPATH.replace("{nameSpace}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getNamespace()).replace("{clusterName}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getClusterName());
            List<String> list = getCuratorFramework().getChildren().forPath(basePath);
            if (list.stream().filter(data -> data.startsWith(ipPort)).count() <= 0) {
                String serverPath = XIDWorkerConstant.KOALA_IDWORKER_ZK_SERVERPATH.replace("{nameSpace}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getNamespace()).replace("{clusterName}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getClusterName()).replace("{ip:port}", ipPort);
                getCuratorFramework().create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(serverPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化服务ID
     */
    private void initServerId() {
        try {
            String ip = XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getIp();
            Integer port = XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getPort();
            String ipPort = ip + ":" + port;
            String basePath = XIDWorkerConstant.KOALA_IDWORKER_ZK_BASEPATH.replace("{nameSpace}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getNamespace()).replace("{clusterName}", XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().getClusterName());
            List<String> list = getCuratorFramework().getChildren().forPath(basePath);
            if (list.stream().filter(data -> data.startsWith(ipPort)).count() <= 0) {
                throw new Exception("init serverPath fail");
            }
            if (list.stream().filter(data -> data.startsWith(ipPort)).count() > 1) {
                throw new Exception("mutil serverPath info");
            }
            String serverIdString = list.stream().filter(data -> data.startsWith(ipPort)).findFirst().get();
            String serverId = serverIdString.substring(ipPort.length());
            XIDWorkerConfigurationBuilder.getInstance().getXIDWorkerConfigurationBean().setServerId(Integer.valueOf(serverId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
