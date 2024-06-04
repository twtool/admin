package icu.twtool.framework.configuration

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean

/**
 * MyBatis Plus 配置类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@MapperScan("icu.twtool.**.mapper")
@SpringBootConfiguration
class MyBatisPlusConfiguration(
    private val objectMapper: ObjectMapper
) {

    @PostConstruct
    fun postConstruct() {
        JacksonTypeHandler.setObjectMapper(objectMapper)
    }

    /**
     * 添加分页插件
     */
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        // 如果配置多个插件,需要最后添加分页插件
        // 如果有多数据源可以不配具体类型
        interceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL))
        return interceptor
    }
}