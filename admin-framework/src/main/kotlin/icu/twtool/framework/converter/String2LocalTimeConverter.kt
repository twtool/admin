package icu.twtool.framework.converter

import icu.twtool.common.constants.TIME_FORMAT_WITHOUT_MILLISECOND
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * 配置字符串到 LocalTime 的转换器（Get 请求时使用）
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Component
class String2LocalTimeConverter : Converter<String, LocalTime> {

    private val formatter = DateTimeFormatter.ofPattern(TIME_FORMAT_WITHOUT_MILLISECOND)

    override fun convert(source: String): LocalTime? {
        return LocalTime.parse(source, formatter)
    }
}