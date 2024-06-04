package icu.twtool.system

import jakarta.annotation.PostConstruct
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.stereotype.Component

/**
 * 系统模块初始化
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Component
class SystemInitializer(
    private val messageSource: ReloadableResourceBundleMessageSource
) {

    @PostConstruct
    fun postConstruct() {
        messageSource.addBasenames("classpath:messages/SystemMessages")
    }
}