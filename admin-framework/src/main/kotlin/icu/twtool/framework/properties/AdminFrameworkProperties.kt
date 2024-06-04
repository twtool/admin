package icu.twtool.framework.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

/**
 * 框架可配置属性
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@ConfigurationProperties(prefix = "admin.framework")
data class AdminFrameworkProperties(
    /**
     * 异步任务配置
     */
    @DefaultValue
    val async: Async,

    /**
     * 国际化配置
     */
    @DefaultValue
    val i18n: I18n,

    /**
     * Security 配置
     */
    @DefaultValue
    val security: Security
) {

    data class Async(
        /**
         * 异步任务执行延时时间（默认：10ms）
         */
        val delay: Long = 10,

        /**
         * 核心线程池大小（默认：10）
         */
        val corePoolSize: Int = 10,

        /**
         * 最大可创建线程数（默认：50）
         */
        val maxPoolSize: Int = 50,

        /**
         * 最大队列长度（默认：2000）
         */
        val maxQueueCapacity: Int = 2000,

        /**
         * 线程允许存在的空闲时间（默认：1000ms）
         */
        val keepAliveSeconds: Int = 1000,
    )

    /**
     * 国际化配置
     */
    data class I18n(
        /**
         * 修改语言参数（默认：locale）
         */
        val paramName: String = LocaleChangeInterceptor.DEFAULT_PARAM_NAME
    )

    /**
     * Security 配置
     */
    data class Security(
        /**
         * 任何人可以访问的地址列表
         */
        val permitAllPatterns: List<String> = emptyList(),

        /**
         * 跨域配置
         */
        @DefaultValue
        val cors: CorsConfig,

        /**
         * JWT 密钥
         */
        val jwtSecretKey: String,
    ) {
        /**
         * 跨域配置
         */
        data class CorsConfig(

            /**
             * 是否启用
             */
            val enabled: Boolean = true,

            /**
             * 允许来源（默认：http://localhost:5173）
             */
            val allowedOrigins: List<String> = listOf("http://localhost:5173"),

            /**
             * 允许携带 Cookies
             */
            val allowCredentials: Boolean = true,

            /**
             * 允许使用请求方法
             */
            val allowedMethods: List<String> = listOf("GET", "POST", "PUT", "DELETE", "PATCH"),

            /**
             * 允许使用请求头
             */
            val allowedHeaders: List<String> = listOf("*")
        )
    }
}