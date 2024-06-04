package icu.twtool.framework.configuration.security.jwt

import icu.twtool.common.model.LoginUserDetails
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.util.Assert

/**
 * JWT 认证 Token（一定是 authenticated 的）
 *
 * @author wen-flower
 * @since 2024-06-05
 */
class JWTAuthenticationToken(
    private val token: String,
    private val jwtUserDetails: LoginUserDetails
) : AbstractAuthenticationToken(jwtUserDetails.authorities) {

    init {
        isAuthenticated = true
    }

    override fun getDetails(): LoginUserDetails = jwtUserDetails

    override fun getCredentials(): String = token

    override fun getPrincipal(): String = token

    override fun setAuthenticated(authenticated: Boolean) {
        Assert.isTrue(authenticated, "JWT AuthenticationToken it cannot be set to unauthenticated.")
        super.setAuthenticated(true)
    }

}