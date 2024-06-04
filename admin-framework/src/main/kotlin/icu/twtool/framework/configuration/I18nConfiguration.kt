package icu.twtool.framework.configuration

import icu.twtool.framework.properties.AdminFrameworkProperties
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*

/**
 * 国际化配置类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@SpringBootConfiguration
class I18nConfiguration(
    private val properties: AdminFrameworkProperties
) : WebMvcConfigurer {

    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        // 默认语言为简体中文
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE)
        return resolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val interceptor = LocaleChangeInterceptor()
        interceptor.paramName = properties.i18n.paramName
        return interceptor
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }
}