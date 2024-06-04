package icu.twtool.common.status

/**
 * 状态接口（支持国际化）
 *
 * @author wen-flower
 * @since 2024-06-04
 */
interface IStatus {
    /**
     * 状态码
     */
    val code: Int

    /**
     * 状态信息，如果 [i18n] 为 true，则是 message key
     */
    val msg: String

    /**
     * 是否是 message 字符串，如果为 true，则应从 MessageSource 中读取国际化字符串
     */
    val i18n: Boolean
}