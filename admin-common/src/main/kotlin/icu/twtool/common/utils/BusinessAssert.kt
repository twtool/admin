package icu.twtool.common.utils

import icu.twtool.common.exception.BusinessException
import icu.twtool.common.status.CommonStatus
import icu.twtool.common.status.IStatus

/**
 * 断言条件 [condition] 一定为真
 * 如果不为真则抛出业务异常
 *
 * @param condition 要断言的条件
 * @param lazyStatus 如果断言失败返回的状态
 */
fun assertTrue(
    condition: Boolean,
    args: Array<Any>? = null,
    lazyStatus: () -> IStatus = { CommonStatus.ERROR }
) {
    if (!condition) throw BusinessException(lazyStatus(), args)
}

/**
 * 断言条件 [condition] 一定为假
 * 如果不为假则抛出业务异常
 *
 * @param condition 要断言的条件
 * @param lazyStatus 如果断言失败返回的状态
 */
fun assertFalse(
    condition: Boolean,
    args: Array<Any>? = null,
    lazyStatus: () -> IStatus = { CommonStatus.ERROR }
) {
    if (condition) throw BusinessException(lazyStatus(), args)
}

/**
 * 断言条件 [obj] 一定为 null
 * 如果不为 null 则抛出业务异常
 *
 * @param obj 要断言的变量
 * @param lazyStatus 如果断言失败返回的状态
 */
fun <T : Any> assertIsNull(
    obj: T?,
    args: Array<Any>? = null,
    lazyStatus: (T) -> IStatus = { CommonStatus.ERROR }
) {
    if (obj != null) throw BusinessException(lazyStatus(obj), args)
}

/**
 * 断言条件 [obj] 一定为不为 null
 * 如果为 null 则抛出业务异常
 *
 * @param obj 要断言的变量
 * @param lazyStatus 如果断言失败返回的状态
 */
fun <T : Any> assertNotNull(
    obj: T?,
    args: Array<Any>? = null,
    lazyStatus: () -> IStatus = { CommonStatus.ERROR }
): T {
    if (obj == null) throw BusinessException(lazyStatus(), args)
    return obj
}