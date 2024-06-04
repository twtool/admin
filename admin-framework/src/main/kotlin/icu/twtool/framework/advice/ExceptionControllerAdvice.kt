package icu.twtool.framework.advice

import icu.twtool.common.controller.BaseController
import icu.twtool.common.exception.BusinessException
import icu.twtool.common.vo.R
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 处理异常
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@RestControllerAdvice
class ExceptionControllerAdvice : BaseController() {

    /**
     * 业务异常直接抛出读取状态
     */
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): R<Any> {
        return error(e.status, e.args)
    }

    /**
     * 处理所有异常，并打印日志
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): R<Any> {
        log.error(e.message, e)
        return error()
    }
}