package icu.twtool.framework.manager

import icu.twtool.framework.properties.AdminFrameworkProperties
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * 异步任务管理器
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Component
class AsyncManager(
    properties: AdminFrameworkProperties,
    private val executor: ScheduledExecutorService
) {

    private val delay: Long = properties.async.delay

    /**
     * 执行任务
     */
    fun execute(task: TimerTask) {
        executor.schedule(task, delay, TimeUnit.MILLISECONDS)
    }

    @PreDestroy
    fun preDestroy() {
        executor.shutdown()
    }
}