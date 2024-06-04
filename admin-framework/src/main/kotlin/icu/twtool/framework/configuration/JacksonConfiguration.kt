package icu.twtool.framework.configuration

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import icu.twtool.common.constants.DATE_FORMAT
import icu.twtool.common.constants.DATE_TIME_FORMAT_WITHOUT_MILLISECOND
import icu.twtool.common.constants.TIME_FORMAT_WITHOUT_MILLISECOND
import icu.twtool.framework.configuration.jackson.SimpleGrantedAuthorityDeserializer
import icu.twtool.framework.configuration.jackson.SimpleGrantedAuthoritySerializer
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Jackson 序列化配置类
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@SpringBootConfiguration
class JacksonConfiguration {

    @Bean
    fun jackson2ObjectMapperBuilderCustomizerConfiguration(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.serializerByType(SimpleGrantedAuthority::class.java, SimpleGrantedAuthoritySerializer())
            builder.deserializerByType(SimpleGrantedAuthority::class.java, SimpleGrantedAuthorityDeserializer())

            val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_WITHOUT_MILLISECOND)
            val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
            val timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT_WITHOUT_MILLISECOND)
            builder.serializerByType(LocalDateTime::class.java, LocalDateTimeSerializer(dateTimeFormatter))
            builder.serializerByType(LocalDate::class.java, LocalDateSerializer(dateFormatter))
            builder.serializerByType(LocalTime::class.java, LocalTimeSerializer(timeFormatter))

            builder.deserializerByType(LocalDateTime::class.java, LocalDateTimeDeserializer(dateTimeFormatter))
            builder.deserializerByType(LocalDate::class.java, LocalDateDeserializer(dateFormatter))
            builder.deserializerByType(LocalTime::class.java, LocalTimeDeserializer(timeFormatter))
        }
    }
}