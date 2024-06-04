package icu.twtool.framework.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import icu.twtool.common.cache.RedisCache
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 * Redis 配置类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@SpringBootConfiguration
class RedisConfiguration(
    private val objectMapperBuilder: Jackson2ObjectMapperBuilder,
) {

    @Bean
    fun redisCache(redisConnectionFactory: RedisConnectionFactory): RedisCache {
        val objectMapper = ObjectMapper()
        objectMapperBuilder.configure(objectMapper)

        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.setDefaultSerializer(RedisSerializer.string())

        redisTemplate.connectionFactory = redisConnectionFactory

        redisTemplate.afterPropertiesSet()

        return RedisCache(objectMapper, redisTemplate)
    }
}