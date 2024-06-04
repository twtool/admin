package icu.twtool.system.service

import com.baomidou.mybatisplus.extension.service.IService
import icu.twtool.system.entity.SysUser

/**
 * 用户服务接口
 *
 * @author wen-flower
 * @since 2024-06-04
 */
interface ISysUserService : IService<SysUser> {

    /**
     * 通过用户名获取密码
     *
     * @param username 用户名
     * @return 密码
     */
    fun getPasswordByUsername(username: String): String?
}