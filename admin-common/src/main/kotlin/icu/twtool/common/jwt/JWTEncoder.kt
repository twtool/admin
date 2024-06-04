package icu.twtool.common.jwt

import java.time.LocalDateTime

/**
 * JWT 编码器接口
 *
 * @author wen-flower
 * @since 2024-06-05
 */
interface JWTEncoder<T> {

    /**
     * 将用户信息编码为 JWT
     *
     * @param info 载体信息
     * @param expiresAt 到期时间
     * @return JWT 字符串
     */
    fun encode(info: T, expiresAt: LocalDateTime): String
}