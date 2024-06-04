package icu.twtool.system.service

import icu.twtool.common.model.LoginUserDetails

/**
 * JWT 服务
 *
 * @author wen-flower
 * @since 2024-06-05
 */
interface ITokenService {

    /**
     * 创建 Token
     *
     * @return 返回唯一 Token
     */
    fun create(userDetails: LoginUserDetails): String

    /**
     * 获取 [LoginUserDetails]
     */
    fun getUserDetails(token: String): LoginUserDetails?

    /**
     * 刷新 Token 有效期
     *
     */
    fun refresh(token: String)

    /**
     * 删除 Token
     *
     * @param token 唯一 Token
     */
    fun delete(token: String)

}