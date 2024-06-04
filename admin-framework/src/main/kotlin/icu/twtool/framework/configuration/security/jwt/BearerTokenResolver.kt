package icu.twtool.framework.configuration.security.jwt

import jakarta.servlet.http.HttpServletRequest

/**
 * Bearer 令牌解析器
 *
 * @author wen-flower
 * @since 2024-06-05
 */
interface BearerTokenResolver {

    /**
     * 从请求中解析 Bearer 令牌
     *
     * @param request HTTP 请求
     * @return 令牌（可为空）
     */
    fun resolve(request: HttpServletRequest): String?
}