package icu.twtool.framework.configuration.security.jwt

import icu.twtool.framework.status.FrameworkStatus
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.util.StringUtils

/**
 * 默认 Bearer 令牌解析器，将会从请求头中读取
 *
 * @author wen-flower
 * @since 2024-06-05
 */
class DefaultBearerTokenResolver(
    private val headerName: String = DEFAULT_BEARER_TOKEN_HEADER_NAME
) : BearerTokenResolver {

    override fun resolve(request: HttpServletRequest): String? {
        return resolveFromAuthorizationHeader(request)
    }

    private fun resolveFromAuthorizationHeader(request: HttpServletRequest): String? {
        val authorization = request.getHeader(this.headerName)
        return if (!StringUtils.startsWithIgnoreCase(authorization, BEARER_TOKEN_PREFIX)) {
            null
        } else {
            val matchResult = AUTHORIZATION_REGEX.matchEntire(authorization)
            if (matchResult == null) {
                throw JWTAuthenticationException(FrameworkStatus.BEARER_ERROR)
            } else {
                matchResult.groups["token"]?.value
            }
        }
    }

    companion object {
        private const val BEARER_TOKEN_PREFIX = "Bearer"
        private const val DEFAULT_BEARER_TOKEN_HEADER_NAME = HttpHeaders.AUTHORIZATION

        private val AUTHORIZATION_REGEX: Regex =
            Regex("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", RegexOption.IGNORE_CASE)
    }
}