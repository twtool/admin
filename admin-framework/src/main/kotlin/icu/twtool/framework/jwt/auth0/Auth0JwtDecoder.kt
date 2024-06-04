package icu.twtool.framework.jwt.auth0

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import icu.twtool.common.jwt.JWTDecoder

/**
 * 使用 Auth0-Jwt 实现 JWT解码器
 *
 * @author wen-flower
 * @since 2024-06-05
 */
class Auth0JwtDecoder<T>(
    private val algorithm: Algorithm,
    private val objectMapper: ObjectMapper,
    private val typeReference: TypeReference<T>,
) : JWTDecoder<T> {

    override fun decode(token: String): T {
        val decodedJWT = JWT.require(algorithm).build().verify(token)
        try {
            val userDetailsStr = decodedJWT.getClaim(INFO_KEY).asString()
            return objectMapper.readValue(
                userDetailsStr,
                typeReference
            )
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }
}