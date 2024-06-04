package icu.twtool.system.controller

import icu.twtool.common.controller.BaseController
import icu.twtool.common.holder.UserDetailsHolder
import icu.twtool.common.model.LoginUserDetails
import icu.twtool.common.vo.R
import icu.twtool.system.param.login.LoginBody
import icu.twtool.system.service.ILoginService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 登录控制器
 *
 * @author wen-flower
 * @since 2024-06-05
 */
@RestController
class SysLoginController(
    private val loginService: ILoginService
) : BaseController() {

    /**
     * 登录接口
     */
    @PostMapping("/login")
    fun login(@RequestBody body: LoginBody): R<String> {
        return success(loginService.login(body.username, body.password))
    }

    /**
     * 登录用户信息
     */
    @GetMapping("/profile")
    fun profile(): R<LoginUserDetails> {
        return success(UserDetailsHolder.get())
    }
}