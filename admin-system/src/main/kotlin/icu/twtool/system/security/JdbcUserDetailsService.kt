package icu.twtool.system.security

import icu.twtool.common.exception.BusinessException
import icu.twtool.common.model.LoginUserDetails
import icu.twtool.common.utils.getLogger
import icu.twtool.system.entity.SysUser
import icu.twtool.system.service.ISysUserService
import icu.twtool.system.status.SystemStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

/**
 * 加载用户特定数据的 JDBC 实现
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Component
class JdbcUserDetailsService(
    private val userService: ISysUserService
) : UserDetailsService {

    private val logger = getLogger()

    override fun loadUserByUsername(username: String): LoginUserDetails {
        return userService.ktQuery()
            .select(SysUser::id, SysUser::username, SysUser::password, SysUser::enabled)
            .oneOpt()
            .map(::buildUserDetails).orElseThrow {
                if (this.logger.isDebugEnabled)
                    this.logger.debug("Query returned no results for user '$username'")
                throw BusinessException(
                    SystemStatus.USERNAME_NOT_FOUND,
                    arrayOf(username)
                )
            }
    }

    /**
     * 通过系统用户对象构建 [UserDetails]
     *
     * @param user 系统用户
     * @return 用户信息
     */
    private fun buildUserDetails(user: SysUser): LoginUserDetails {
        return LoginUserDetails(
            user.id!!,
            user.username!!,
            loadAuthoritiesByUserId(user.id),
            user.enabled!!
        )
    }

    /**
     * 通过用户 ID 查询用户角色字符串列表
     *
     * @param userId 用户 ID
     * @return 用户的关联权限
     */
    private fun loadAuthoritiesByUserId(userId: Long?): List<SimpleGrantedAuthority> {
        val authorities = mutableListOf<SimpleGrantedAuthority>()
        // 添加角色
//        val roleIds = userRoleService.listRoleByUserId(userId ?: return authorities)
//            .filter { it.enabled == true } // 过滤未启用权限
//            .mapNotNull { role ->
//                if (role.name != null) {
//                    authorities.add(SimpleGrantedAuthority(SysRole.PREFIX + role.name))
//                }
//                role.id
//            }
//         添加菜单权限
//        menuService.listByRoleIds(roleIds).forEach { menu ->
//            if (menu.name != null) {
//                authorities.add(SimpleGrantedAuthority(menu.name))
//            }
//        }
        return authorities
    }
}