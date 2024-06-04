package icu.twtool.framework.configuration.security.jwt

import com.auth0.jwt.exceptions.TokenExpiredException
import icu.twtool.common.jwt.JWTDecoder
import icu.twtool.framework.status.FrameworkStatus
import icu.twtool.system.service.ITokenService
import icu.twtool.system.status.SystemStatus
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.filter.OncePerRequestFilter

/**
 * JWT 请求认证过滤器
 *
 * @author wen-flower
 * @since 2024-06-05
 */
class JWTAuthenticationFilter(
    private val bearerTokenRequestMatcher: BearerTokenRequestMatcher,
    private val jwtDecoder: JWTDecoder<String>,
    private val tokenService: ITokenService,
) : OncePerRequestFilter() {

    private val securityContextHolderStrategy: SecurityContextHolderStrategy = SecurityContextHolder
        .getContextHolderStrategy()

    private val securityContextRepository: SecurityContextRepository = RequestAttributeSecurityContextRepository()

    override fun shouldNotFilter(@NonNull request: HttpServletRequest): Boolean {
        return !bearerTokenRequestMatcher.matches(request)
    }

    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        val authentication = attemptAuthentication(request)
        val context = securityContextHolderStrategy.createEmptyContext()
        context.authentication = authentication
        securityContextHolderStrategy.context = context
        securityContextRepository.saveContext(context, request, response)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }

    private fun attemptAuthentication(request: HttpServletRequest): Authentication {
        try {
            val jwt: String = bearerTokenRequestMatcher.bearerTokenResolver.resolve(request)!!
            val token = jwtDecoder.decode(jwt)
            val userDetails = tokenService.getUserDetails(token)
                ?: throw JWTAuthenticationException(FrameworkStatus.NOT_OPERATED_FOR_TIMEOUT)
            if (!userDetails.enabled) throw JWTAuthenticationException(SystemStatus.ACCOUNT_DISABLED)
            tokenService.refresh(token)
            return JWTAuthenticationToken(token, userDetails)
        } catch (e: RuntimeException) {
            if (e is TokenExpiredException) {
                throw JWTAuthenticationException(FrameworkStatus.BEARER_ERROR)
            }
            if (e is JWTAuthenticationException) throw e
            logger.error(e.message, e)
            throw JWTAuthenticationException(FrameworkStatus.BEARER_ERROR)
        }
    }
}
