package icu.twtool.common.cache

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

/**
 * Redis 缓存类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
class RedisCache(
    private val objectMapper: ObjectMapper,
    private val redisTemplate: RedisTemplate<String, String>
) {

    private fun <T> serialize(value: T): String {
        return objectMapper.writeValueAsString(value)
    }

    private fun <T : Any> deserialize(value: String?, valueType: TypeReference<T>): T? {
        return if (value != null) objectMapper.readValue(value, valueType) else null
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    fun <T : Any> setCacheObject(key: String, value: T) {
        redisTemplate.opsForValue().set(key, serialize(value))
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间单位（默认：秒）
     */
    fun <T : Any> setCacheObject(key: String, value: T, timeout: Long, timeUnit: TimeUnit = TimeUnit.SECONDS) {
        redisTemplate.opsForValue().set(key, serialize(value), timeout, timeUnit)
    }

    /**
     * 设置有效时间
     *
     * @param key 缓存的键值
     * @param timeout 超时时间
     * @param unit 时间单位（默认：秒）
     * @return 返回 true 表示设置成功, 返回 false 表示设置失败
     */
    fun expire(key: String, timeout: Long, unit: TimeUnit = TimeUnit.SECONDS): Boolean {
        return redisTemplate.expire(key, timeout, unit)
    }

    /**
     * 获取有效时间
     *
     * @param key 缓存的键值
     * @return 有效时间
     */
    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key)
    }

    /**
     * 获取缓存值
     *
     * @param key 缓存的键值
     * @param valueType 要转换的类型
     */
    fun <T : Any> getCacheObject(key: String, valueType: TypeReference<T>): T? {
        val value = redisTemplate.opsForValue().get(key)
        return deserialize(value, valueType)
    }

    /**
     * 获取缓存值
     *
     * @param key 缓存的键值
     */
    inline fun <reified T : Any> getCacheObject(key: String): T? {
        return getCacheObject(key, object : TypeReference<T>() {})
    }

    /**
     * 判断缓存键值是否存在
     *
     * @param key 缓存的键值
     * @return 返回 true 表示存在，false 表示不存在
     */
    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    fun delete(key: String): Boolean {
        return redisTemplate.delete(key)
    }

    /**
     * 删除多个对象
     *
     * @param keys 缓存的键值集合
     * @return 删除的数量
     */
    fun deleteAll(keys: Collection<String>): Long {
        return redisTemplate.delete(keys)
    }

    /**
     * 获取缓存的键值列表
     *
     * @param pattern 字符串前缀
     * @return 缓存的键值列表
     */
    fun keys(pattern: String): Collection<String> {
        return redisTemplate.keys(pattern)
    }

}