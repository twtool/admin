package icu.twtool.framework.configuration.security

import com.fasterxml.jackson.databind.ObjectMapper
import icu.twtool.common.utils.getLogger
import icu.twtool.common.vo.R
import icu.twtool.framework.configuration.security.jwt.JWTAuthenticationException
import icu.twtool.framework.status.FrameworkStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

/**
 * 处理认证异常
 *
 * @author wen-flower
 * @since 2024-06-05
 */
@Component
class JsonAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
    private val messageSource: MessageSource
) : AuthenticationEntryPoint {

    private val logger = getLogger()

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        if (logger.isDebugEnabled) logger.debug("handle authentication exception: {}", authException.message)
        response.contentType = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"

        val status =
            if (authException is JWTAuthenticationException) authException.status
            else FrameworkStatus.NOT_LOGIN

        objectMapper.writeValue(
            response.writer,
            R.from<Any>(status, messageSource, LocaleContextHolder.getLocale())
        )
    }
}