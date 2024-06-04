package icu.twtool.framework.configuration

import icu.twtool.framework.properties.AdminFrameworkProperties
import org.apache.commons.lang3.concurrent.BasicThreadFactory
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor

/**
 * 异步配置类，开启 @Async 异步注解支持
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@EnableAsync
@SpringBootConfiguration
class AsyncConfiguration(
    properties: AdminFrameworkProperties
) {

    companion object {

        const val SCHEDULED_EXECUTOR_SERVICE_ID = "scheduledExecutorService"

        const val THREAD_POOL_TASK_EXECUTOR_ID = "threadPoolTaskExecutor"
    }

    private val asyncProperties = properties.async

    @Bean(THREAD_POOL_TASK_EXECUTOR_ID)
    fun threadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.maxPoolSize = asyncProperties.maxPoolSize
        executor.corePoolSize = asyncProperties.corePoolSize
        executor.queueCapacity = asyncProperties.maxQueueCapacity
        executor.keepAliveSeconds = asyncProperties.keepAliveSeconds
        // 一个被拒绝任务的处理程序，它直接在方法的调用线程 execute 中运行被拒绝的任务，除非执行程序已关闭，在这种情况下，任务将被丢弃。
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        return executor
    }

    @Bean(SCHEDULED_EXECUTOR_SERVICE_ID)
    fun scheduledExecutorService(): ScheduledExecutorService {
        return ScheduledThreadPoolExecutor(
            asyncProperties.corePoolSize,
            BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").build(),
            // 一个被拒绝任务的处理程序，它直接在方法的调用线程 execute 中运行被拒绝的任务，除非执行程序已关闭，在这种情况下，任务将被丢弃。
            ThreadPoolExecutor.CallerRunsPolicy()
        )
    }
}