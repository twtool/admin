package icu.twtool.framework.converter

import icu.twtool.common.constants.DATE_FORMAT
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 配置字符串到 LocalDate 的转换器（Get 请求时使用）
 *
 * @author wen-flower
 * @since 2024-06-04
 */
@Component
class String2LocalDateConverter : Converter<String, LocalDate> {

    private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    override fun convert(source: String): LocalDate? {
        return LocalDate.parse(source, formatter)
    }
}