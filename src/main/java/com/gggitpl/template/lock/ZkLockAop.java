package com.gggitpl.template.lock;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;

/**
 * ZkLock切面
 *
 * @author gggitpl
 */
@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "zk", name = "host")
public class ZkLockAop {

    @Value("${spring.application.name}")
    private String appName;
    @Resource
    private CuratorFramework zkClient;

    @Around("@annotation(com.gggitpl.template.lock.ZkLock)")
    public Object post(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        ZkLock zkLock = methodSignature.getMethod().getAnnotation(ZkLock.class);
        String simpleName = point.getTarget().getClass().getSimpleName();
        String path = Joiner.on("_").join(appName, "lock", simpleName, point.getSignature().getName());
        InterProcessSemaphoreMutex mutex = new InterProcessSemaphoreMutex(zkClient, "/" + path);
        if (mutex.acquire(zkLock.time(), zkLock.timeunit())) {
            try {
                log.debug("Acquired the lock: {}", path);
                return point.proceed();
            } finally {
                mutex.release();
                log.debug("Released the lock: {}", path);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Get zkLock timeout");
        }
    }

}
