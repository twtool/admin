package icu.twtool.framework.configuration.security

import com.fasterxml.jackson.databind.ObjectMapper
import icu.twtool.common.vo.R
import icu.twtool.framework.status.FrameworkStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

/**
 * 访问被拒处理
 *
 * @author wen-flower
 * @since 2024-06-05
 */
@Component
class JsonAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
    private val messageSource: MessageSource
) : AccessDeniedHandler {

    /**
     * 处理访问被拒绝失败。
     */
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"
        objectMapper.writeValue(
            response.writer,
            R.from<Any>(FrameworkStatus.ACCESS_DENIED, messageSource, LocaleContextHolder.getLocale())
        )
    }
}