package icu.twtool.system.service.impl

import icu.twtool.common.jwt.JWTEncoder
import icu.twtool.common.utils.assertNotNull
import icu.twtool.common.utils.assertTrue
import icu.twtool.system.properties.SystemProperties
import icu.twtool.system.security.JdbcUserDetailsService
import icu.twtool.system.service.ILoginService
import icu.twtool.system.service.ISysUserService
import icu.twtool.system.service.ITokenService
import icu.twtool.system.status.SystemStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * 登录服务实现类
 *
 * @author wen-flower
 * @since 2024-06-05
 */
@Service
class LoginServiceImpl(
    private val jwtEncoder: JWTEncoder<String>,
    private val userService: ISysUserService,
    private val tokenService: ITokenService,
    private val systemProperties: SystemProperties,
    private val userDetailsService: JdbcUserDetailsService
) : ILoginService {

    private val passwordEncoder = DelegatingPasswordEncoder(
        "bcrypt",
        mapOf(
            "bcrypt" to BCryptPasswordEncoder(systemProperties.passwordStrength)
        )
    )

    override fun login(username: String, password: String): String {
        // 验证密码是否正确
        val passwordEncoded =
            assertNotNull(userService.getPasswordByUsername(username)) { SystemStatus.USERNAME_OR_PASSWORD_ERROR }
        assertTrue(passwordEncoder.matches(password, passwordEncoded)) { SystemStatus.USERNAME_OR_PASSWORD_ERROR }

        val userDetails = userDetailsService.loadUserByUsername(username)

        assertTrue(userDetails.enabled) { SystemStatus.ACCOUNT_DISABLED }

        val token = tokenService.create(userDetails)
        return jwtEncoder.encode(token, LocalDateTime.now().plusDays(systemProperties.jwtDurationOfDays))
    }
}