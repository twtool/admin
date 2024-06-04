package icu.twtool.common.exception

import icu.twtool.common.status.CommonStatus
import icu.twtool.common.status.IStatus

/**
 * 业务异常
 *
 * @author wen-flower
 * @since 2024-06-04
 */
class BusinessException(
    val status: IStatus = CommonStatus.ERROR,
    val args: Array<Any>? = null
) : RuntimeException(status.msg)