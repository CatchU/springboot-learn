package com.catchu.beans;

import lombok.Data;

@Data
public class XIDWorkerConfigurationBean {

    private String servers;

    private Integer sessionTimeoutMs;

    private Integer connectionTimeoutMs;

    private String clusterName;

    private Integer dataCenterIndex;

    private Integer serverId;

    private String namespace;

    private Integer port;

    private String ip;
}
