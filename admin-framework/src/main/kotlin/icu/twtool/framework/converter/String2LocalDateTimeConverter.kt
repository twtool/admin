package icu.twtool.framework.converter

import icu.twtool.common.constants.DATE_TIME_FORMAT_WITHOUT_MILLISECOND
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 配置字符串到 LocalDateTime 的转换器（Get 请求时使用）
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Component
class String2LocalDateTimeConverter : Converter<String, LocalDateTime> {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_WITHOUT_MILLISECOND)

    override fun convert(source: String): LocalDateTime? {
        return LocalDateTime.parse(source, dateTimeFormatter)
    }
}