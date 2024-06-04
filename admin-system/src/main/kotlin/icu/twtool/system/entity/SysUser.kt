package icu.twtool.system.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import icu.twtool.common.entity.BaseEntity

/**
 * 系统用户表
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@TableName("tb_sys_user")
data class SysUser(
    /**
     * 用户 ID
     */
    @TableId(type = IdType.AUTO)
    val id: Long? = null,

    /**
     * 用户名
     */
    val username: String? = null,

    /**
     * 用户手机号
     */
    val phone: String? = null,

    /**
     * 用户昵称
     */
    val nickname: String? = null,

    /**
     * 用户密码
     */
    val password: String? = null,

    /**
     * 是否启用
     */
    val enabled: Boolean? = null
) : BaseEntity() {

    companion object {

        const val ID = "validation.sys.user.id"
        const val USERNAME = "validation.sys.user.username"
        const val NICKNAME = "validation.sys.user.nickname"
        const val PASSWORD = "validation.sys.user.password"
        const val PHONE = "validation.sys.user.phone"
        const val ENABLED = "validation.sys.user.enabled"
    }
}