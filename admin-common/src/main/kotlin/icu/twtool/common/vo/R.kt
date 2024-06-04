package icu.twtool.common.vo

import icu.twtool.common.status.IStatus
import org.springframework.context.MessageSource
import java.util.Locale

/**
 * 统一数据返回包装
 *
 * @author wen-flower
 * @since 2024-06-04
 */
data class R<D : Any>(
    val code: Int,
    val msg: String,
    val data: D? = null
) {

    companion object {

        fun <D : Any> from(
            status: IStatus,
            messageSource: MessageSource,
            locale: Locale,
            args: Array<Any>? = null,
            data: D? = null
        ): R<D> {
            val msg = if (status.i18n) messageSource.getMessage(status.msg, args, locale) else status.msg
            return R(status.code, msg, data)
        }
    }
}
