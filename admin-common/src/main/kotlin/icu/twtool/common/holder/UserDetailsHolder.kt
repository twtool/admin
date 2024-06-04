package icu.twtool.common.holder

import icu.twtool.common.model.LoginUserDetails
import icu.twtool.common.status.CommonStatus
import icu.twtool.common.utils.assertNotNull
import org.springframework.security.core.context.SecurityContextHolder

/**
 * 登录用户信息持有类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
object UserDetailsHolder {

    fun get(): LoginUserDetails {
        val userDetails = SecurityContextHolder.getContext().authentication.details as? LoginUserDetails

        return assertNotNull(userDetails) { CommonStatus.USER_DETAILS_NOT_EXISTS }
    }
}