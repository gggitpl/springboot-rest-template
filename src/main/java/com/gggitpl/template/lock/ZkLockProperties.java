package com.gggitpl.template.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zookeeper 配置属性
 *
 * @author gggitpl
 */
@Data
@ConfigurationProperties(prefix = "zk.lock")
public class ZkLockProperties {

    private String host = "127.0.0.1";
    private int port = 2181;
    private int baseSleepTimeMs = 6000;
    private int maxRetries = 3;
    private int sessionTimeoutMs = 6000;
    private int connectionTimeoutMs = 6000;

}
