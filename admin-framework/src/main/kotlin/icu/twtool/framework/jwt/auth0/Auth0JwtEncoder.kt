package icu.twtool.framework.jwt.auth0

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import icu.twtool.common.jwt.JWTEncoder
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * 使用 Auth0-JWT 实现 JWT 编码器
 *
 * @author wen-flower
 * @since 2024-06-05
 */
class Auth0JwtEncoder<T>(
    private var algorithm: Algorithm,
    private var objectMapper: ObjectMapper
) : JWTEncoder<T> {

    override fun encode(info: T, expiresAt: LocalDateTime): String {
        try {
            val infoStr = objectMapper.writeValueAsString(info)
            return JWT.create()
                .withClaim(INFO_KEY, infoStr)
                .withExpiresAt(expiresAt.atZone(ZoneId.systemDefault()).toInstant())
                .sign(algorithm)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}