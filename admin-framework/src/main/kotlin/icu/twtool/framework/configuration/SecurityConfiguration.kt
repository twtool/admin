package icu.twtool.framework.configuration

import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import icu.twtool.framework.configuration.security.jwt.BearerTokenRequestMatcher
import icu.twtool.framework.configuration.security.jwt.BearerTokenResolver
import icu.twtool.framework.configuration.security.jwt.DefaultBearerTokenResolver
import icu.twtool.framework.configuration.security.jwt.JWTAuthenticationFilter
import icu.twtool.common.jwt.JWTDecoder
import icu.twtool.common.jwt.JWTEncoder
import icu.twtool.framework.jwt.auth0.Auth0JwtDecoder
import icu.twtool.framework.jwt.auth0.Auth0JwtEncoder
import icu.twtool.framework.properties.AdminFrameworkProperties
import icu.twtool.system.service.ITokenService
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.web.cors.CorsConfiguration

/**
 * Security 配置类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@EnableMethodSecurity
@SpringBootConfiguration
class SecurityConfiguration(
    properties: AdminFrameworkProperties,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val userDetailsService: UserDetailsService,
    private val objectMapper: ObjectMapper,
    private val tokenService: ITokenService
) {

    private val security = properties.security

    private val jwtAlgorithm = Algorithm.HMAC256(security.jwtSecretKey)

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() } // 禁用 CSRF（跨站请求伪造拦截，使用系统默认使用 JWT + Redis实现认证和授权）
            .headers {
                it.cacheControl { control -> control.disable() } // 禁用 HTTP 缓存标头
            }
            .authorizeHttpRequests {
                it.requestMatchers(*security.permitAllPatterns.toTypedArray()).permitAll()
                    // 除了上述配置的地址，其它资源都需要认证后才能访问
                    .anyRequest().authenticated()
            }
            .httpBasic { it.disable() }
            .cors { // 跨域访问配置
                val corsConfig = security.cors
                if (corsConfig.enabled) it.configurationSource {
                    CorsConfiguration().apply {
                        allowedOrigins = corsConfig.allowedOrigins
                        allowCredentials = corsConfig.allowCredentials
                        allowedMethods = corsConfig.allowedMethods
                        allowedHeaders = corsConfig.allowedHeaders
                    }
                } else {
                    it.disable()
                }
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .anonymous { it.disable() }
            .rememberMe { it.disable() }
            .exceptionHandling {
                it.accessDeniedHandler(accessDeniedHandler)
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            .addFilterAfter(jwtAuthenticationFilter(), ExceptionTranslationFilter::class.java)
            .build()
    }

    @Bean
    @ConditionalOnMissingBean
    fun bearerTokenRequestMatcher(): BearerTokenRequestMatcher {
        return BearerTokenRequestMatcher(bearerTokenResolver())
    }

    @Bean
    @ConditionalOnMissingBean
    fun jwtAuthenticationFilter(): JWTAuthenticationFilter {
        return JWTAuthenticationFilter(bearerTokenRequestMatcher(), userDetailsService, jwtDecoder(), tokenService)
    }

    @Bean
    @ConditionalOnMissingBean
    fun bearerTokenResolver(): BearerTokenResolver {
        return DefaultBearerTokenResolver()
    }

    @Bean
    @ConditionalOnMissingBean
    fun jwtDecoder(): JWTDecoder<String> {
        return Auth0JwtDecoder(jwtAlgorithm, objectMapper, object : TypeReference<String>() {})
    }

    @Bean
    @ConditionalOnMissingBean
    fun jwtEncoder(): JWTEncoder<String> {
        return Auth0JwtEncoder(jwtAlgorithm, objectMapper)
    }
}