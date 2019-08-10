package com.catchu.config;

import com.alibaba.fastjson.JSONObject;
import com.catchu.beans.es.ESMappingComponent;
import com.catchu.beans.es.ESRO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;

@Slf4j
public class ESAdminComponent {

    private RestHighLevelClient restHighLevelClient;

    private ESAdminComponent() {
    }

    public void initES() {
        log.info("--------------------------初始化ES客户端START---------------------------");
        try {
            String[] servers = CommentsESConfig.servers.split(",");
            HttpHost[] hosts = new HttpHost[servers.length];
            RestClientBuilder restClientBuilder;
            for (int i = 0; i < servers.length; i++) {
                String server = servers[i];
                String ip = server.split(":")[0];
                Integer port = Integer.valueOf(server.split(":")[1]);
                hosts[i] = new HttpHost(ip, port, "http");
            }
            restHighLevelClient = new RestHighLevelClient(RestClient.builder(hosts));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("--------------------------初始化ES客户端END---------------------------");
    }

    private static class Assistant {
        final static ESAdminComponent commentsESComponent = new ESAdminComponent();
    }

    public static ESAdminComponent getInstance() {
        return Assistant.commentsESComponent;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return this.restHighLevelClient;
    }


    /**
     * 创建索引
     *
     * @param esro
     * @return
     */
    public boolean createIndex(ESRO esro) {
        log.info("uuid:{},desc:{},params:{}", esro.getUuid(), "创建索引", JSONObject.toJSONString(esro));
        Boolean result = false;
        try {
            result = existsIndex(esro);
            if (null != result && !result) {
                CreateIndexRequest request = new CreateIndexRequest(esro.getIndex());
                request.settings(Settings.builder()
                        .put("index.number_of_shards", esro.getShardNum())
                        .put("index.number_of_replicas", esro.getReplicaNum())
                );
                request.mapping(esro.getType(), ESMappingComponent.getInstance().getXContentBuilder());
                CreateIndexResponse response = getRestHighLevelClient().indices().create(request, RequestOptions.DEFAULT);
                result = response.isAcknowledged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("uuid:{},desc:{},result:{}", esro.getUuid(), "创建索引", result);
        return result;
    }

    /**
     * 删除索引
     *
     * @param esro
     * @return
     */
    public boolean deleteIndex(ESRO esro) {
        log.info("uuid:{},desc:{},params:{}", esro.getUuid(), "删除索引", JSONObject.toJSONString(esro));
        Boolean result = false;
        try {
            result = existsIndex(esro);
            if (null != result && result) {
                DeleteIndexRequest request = new DeleteIndexRequest(esro.getIndex());
                request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
                request.masterNodeTimeout("1m");
                request.indicesOptions(IndicesOptions.lenientExpandOpen());
                AcknowledgedResponse deleteIndexResponse = getRestHighLevelClient().indices().delete(request, RequestOptions.DEFAULT);
                result = deleteIndexResponse.isAcknowledged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("uuid:{},desc:{},params:{}", esro.getUuid(), "删除索引", result);
        return result;
    }

    /**
     * 索引是否存在
     *
     * @param esro
     * @return
     */
    public Boolean existsIndex(ESRO esro) {
        log.info("uuid:{},desc:{},params:{}", esro.getUuid(), "检测索引是否存在", JSONObject.toJSONString(esro));
        Boolean result = null;
        try {
            GetIndexRequest request = new GetIndexRequest();
            request.indices(esro.getIndex());
            request.local(false);
            request.humanReadable(true);
            result = getRestHighLevelClient().indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("uuid:{},desc:{},result:{}", esro.getUuid(), "检测索引是否存在", result);
        return result;
    }
}
