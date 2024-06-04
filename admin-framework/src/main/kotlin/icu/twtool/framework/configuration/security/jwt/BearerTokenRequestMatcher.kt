package icu.twtool.framework.configuration.security.jwt

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.util.matcher.RequestMatcher

/**
 * BearerToken 认证请求匹配器
 *
 * @author wen-flower
 * @since 2024-06-05
 */
data class BearerTokenRequestMatcher(val bearerTokenResolver: BearerTokenResolver) : RequestMatcher {
    override fun matches(request: HttpServletRequest): Boolean {
        return bearerTokenResolver.resolve(request) != null
    }
}
