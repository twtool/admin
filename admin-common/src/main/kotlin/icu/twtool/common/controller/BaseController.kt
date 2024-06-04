package icu.twtool.common.controller

import icu.twtool.common.status.CommonStatus
import icu.twtool.common.status.IStatus
import icu.twtool.common.vo.R
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

/**
 * 前端控制器基础实现
 *
 * @author wen-flower
 * @since 2024-06-04
 */
abstract class BaseController : ApplicationContextAware {

    protected val log: Logger = LoggerFactory.getLogger(this::class.java)

    private lateinit var messageSource: MessageSource

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        messageSource = applicationContext.getBean<MessageSource>()
    }

    private fun <D : Any> result(status: IStatus, args: Array<Any>? = null, data: D? = null): R<D> {
        return R.from(status, messageSource, LocaleContextHolder.getLocale(), args, data)
    }

    protected fun <D : Any> success(data: D? = null): R<D> {
        return result(CommonStatus.SUCCESS, data = data)
    }

    protected fun <D : Any> error(status: IStatus = CommonStatus.ERROR, args: Array<Any>? = null): R<D> {
        return result(status, args)
    }

    protected fun <D : Any> Boolean.result(
        status: IStatus = CommonStatus.ERROR,
        args: Array<Any>? = null,
        data: D? = null
    ): R<D> {
        return if (this) success(data) else error(status, args)
    }
}