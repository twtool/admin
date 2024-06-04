package icu.twtool.system.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "admin.system")
data class SystemProperties(
    /**
     * JWT 有效时间，在 JWT 内有效时间内访问服务，会刷新 Token 有效时间，默认：7 天，单位：天）
     */
    val jwtDurationOfDays: Long = 7,

    /**
     * Token 有效时间，多长时间不访问服务登录失效（默认：30分钟，单位：分钟）
     */
    val tokenDurationOfMinute: Long = 30,

    /**
     * 密码编码强度（默认：10）
     */
    val passwordStrength: Int = 10
)