package icu.twtool.system.service.impl

import icu.twtool.common.cache.RedisCache
import icu.twtool.common.model.LoginUserDetails
import icu.twtool.system.properties.SystemProperties
import icu.twtool.system.service.ITokenService
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.TimeUnit

private const val PREFIX = "user:details"

/**
 * Token 服务实现类
 */
@Service
class TokenServiceImpl(
    private val redisCache: RedisCache,
    private val systemProperties: SystemProperties,
) : ITokenService {

    override fun create(userDetails: LoginUserDetails): String {
        val token = PREFIX + ":" + userDetails.username + ":" + UUID.randomUUID()
        redisCache.setCacheObject(token, userDetails, systemProperties.tokenDurationOfMinute, TimeUnit.MINUTES)
        return token
    }

    override fun getUserDetails(token: String): LoginUserDetails? {
        return redisCache.getCacheObject(token)
    }

    override fun refresh(token: String) {
        if (redisCache.hasKey(token)) {
            redisCache.expire(token, systemProperties.tokenDurationOfMinute, TimeUnit.MINUTES)
        }
    }

    override fun delete(token: String) {
        redisCache.delete(token)
    }

}