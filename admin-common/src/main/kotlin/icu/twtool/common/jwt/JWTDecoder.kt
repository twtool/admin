package icu.twtool.common.jwt

/**
 * JWT 解码器接口
 *
 * @author wen-flower
 * @since 2024-06-05
 */
interface JWTDecoder<T> {

    /**
     * 将 用户信息编码到 JWT 中
     *
     * @param token JWT 字符串
     * @return 保存的载体信息
     */
    fun decode(token: String): T
}