package icu.twtool.common.model

import com.fasterxml.jackson.annotation.JsonIgnore
import icu.twtool.common.constants.ROLE_ADMIN
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * 登录用户详情
 *
 * @author wen-flower
 * @since 2024-06-04
 */
data class LoginUserDetails(
    /**
     * 用户 ID
     */
    val userId: Long,

    /**
     * 用户名
     */
    @JvmField
    val username: String,

    /**
     * 用户权限
     */
    @JvmField
    val authorities: Collection<SimpleGrantedAuthority>,

    /**
     * 是否启用
     */
    val enabled: Boolean,
) : UserDetails {

    /**
     * 是否是管理员
     */
    val admin: Boolean = authorities.any { it.authority == ROLE_ADMIN }

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    @JsonIgnore
    override fun getPassword(): String? = null

    override fun getUsername(): String = username

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true

    @JsonIgnore
    override fun isEnabled(): Boolean = enabled
}