package com.example.async.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    @Value("${threadpool.corePoolSize}")
    private int corePoolSize;

    @Value("${threadpool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${threadpool.queueCapacity}")
    private int queueCapacity;

    @Value("${threadpool.keepAliveSeconds}")
    private int keepAliveSeconds;

    public static ThreadPoolTaskExecutor executor;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    static class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            logger.error("任务处理异常:{}, 抛出方法:{}, 入参:{}", throwable, method, JSON.toJSONString(obj));
        }
    }

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("testThread-");
        executor.setRejectedExecutionHandler(new LogAbortPolicy());
        executor.initialize();
        return executor;
    }

    static class LogAbortPolicy implements RejectedExecutionHandler{
        /**
         * Always print RejectedExecutionException but not throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            logger.error("RejectedExecutionException;corePoolSize:{},poolSize:{},activeCount:{},taskCount:{}",e.getCorePoolSize(),e.getPoolSize(),e.getActiveCount(),e.getTaskCount());
            // TODO
//            if (!e.isShutdown()) {
//                r.run();
//            }
        }
    }
}
