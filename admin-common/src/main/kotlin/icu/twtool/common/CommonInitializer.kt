package icu.twtool.common

import jakarta.annotation.PostConstruct
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.stereotype.Component

/**
 * 公共模块配置类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Component
class CommonInitializer(
    private val messageSource: ReloadableResourceBundleMessageSource
) {

    @PostConstruct
    fun postConstruct() {
        messageSource.addBasenames("classpath:messages/CommonMessages")
    }
}