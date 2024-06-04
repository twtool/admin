package icu.twtool.system.service

/**
 * 登录服务
 *
 * @author wen-flower
 * @since 2024-06-05
 */
interface ILoginService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return JWT 字符串
     */
    fun login(username: String, password: String): String
}