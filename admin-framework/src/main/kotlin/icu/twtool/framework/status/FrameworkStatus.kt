package icu.twtool.framework.status

import icu.twtool.common.status.IStatus

enum class FrameworkStatus(override val code: Int, override val msg: String) : IStatus {
    NOT_LOGIN(4100, "status.framework.not.login"),
    ACCESS_DENIED(4101, "status.framework.access.denied"),
    LOGIN_EXPIRES(4103, "status.framework.login.expires"),
    NOT_OPERATED_FOR_TIMEOUT(4104, "status.framework.not.operated.for.timeout"),
    BEARER_ERROR(5101, "status.framework.bearer.error"),
    ;

    override val i18n: Boolean = true
}