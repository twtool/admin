package icu.twtool.common.status

/**
 * 公共状态枚举
 *
 * @author wen-flower
 * @since 2024-06-04
 */
enum class CommonStatus(
    override val code: Int,
    override val msg: String,
) : IStatus {
    SUCCESS(2000, "status.common.success"),
    USER_DETAILS_NOT_EXISTS(3000, "status.common.user.details.not.exists"),
    ERROR(5000, "status.common.error"),
    ;

    override val i18n: Boolean = true
}