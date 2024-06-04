package icu.twtool.system.status

import icu.twtool.common.status.IStatus

/**
 * 系统状态枚举
 *
 * @author wen-flower
 * @since 2024-06-05
 */
enum class SystemStatus(override val code: Int, override val msg: String) : IStatus {
    USERNAME_NOT_FOUND(4200, "status.system.username.not.found"),
    USERNAME_OR_PASSWORD_ERROR(4201, "status.system.username.or.password.error"),
    ACCOUNT_DISABLED(4102, "status.system.account.disabled"),
    ;

    override val i18n: Boolean = true
}