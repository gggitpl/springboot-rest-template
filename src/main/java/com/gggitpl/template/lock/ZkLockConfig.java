package com.gggitpl.template.lock;

import com.google.common.base.Joiner;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * zookeeper 配置
 *
 * @author gggitpl
 */
@Configuration
@EnableConfigurationProperties(ZkLockProperties.class)
@ConditionalOnProperty(prefix = "zk.lock", name = "host")
public class ZkLockConfig {
    @Resource
    private ZkLockProperties zkLockProperties;

    @Bean
    public CuratorFramework zkClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkLockProperties.getBaseSleepTimeMs(), zkLockProperties.getMaxRetries());
        CuratorFramework client = CuratorFrameworkFactory
                .newClient(Joiner.on(":").join(zkLockProperties.getHost(), zkLockProperties.getPort()),
                        zkLockProperties.getSessionTimeoutMs(),
                        zkLockProperties.getConnectionTimeoutMs(),
                        retryPolicy);
        client.start();
        return client;
    }

    @Bean
    public AsyncCuratorFramework zkAsyncClient() {
        return AsyncCuratorFramework.wrap(zkClient());
    }
}
