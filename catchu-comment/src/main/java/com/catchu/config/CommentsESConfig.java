package com.catchu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 评论业务ES配置
 *
 */
@Configuration
public class CommentsESConfig {

    public static String servers;

    public static String clusterName;

    public static Integer shardNum;

    public static Integer replicaNum;

    @Value("${comments.es.servers}")
    public void setServers(String servers) {
        CommentsESConfig.servers = servers;
    }
    @Value("${comments.es.clusterName}")
    public void setClusterName(String clusterName){
        CommentsESConfig.clusterName=clusterName;
    }
    @Value("${comments.es.shardNum}")
    public void setShardNum(Integer shardNum){
        CommentsESConfig.shardNum=shardNum;
    }
    @Value("${comments.es.replicaNum}")
    public void setReplicaNum(Integer replicaNum){
        CommentsESConfig.replicaNum=replicaNum;
    }
}
