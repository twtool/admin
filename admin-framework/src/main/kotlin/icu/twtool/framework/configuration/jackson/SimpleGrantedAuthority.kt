package icu.twtool.framework.configuration.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * 自定义 [SimpleGrantedAuthority] 序列化方式
 *
 * @author wen-flower
 * @since 2024-06-04
 */
class SimpleGrantedAuthoritySerializer : JsonSerializer<SimpleGrantedAuthority>() {

    override fun serialize(value: SimpleGrantedAuthority, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(value.authority)
    }

    override fun serializeWithType(
        value: SimpleGrantedAuthority,
        gen: JsonGenerator,
        serializers: SerializerProvider,
        typeSer: TypeSerializer
    ) {
        val typeIdDef = typeSer.writeTypePrefix(
            gen,
            typeSer.typeId(value, JsonToken.VALUE_STRING)
        )
        serialize(value, gen, serializers)
        typeSer.writeTypeSuffix(gen, typeIdDef)
    }

}

/**
 * 自定义 [SimpleGrantedAuthority] 反序列化方式
 *
 * @author wen-flower
 * @since 2024-06-04
 */
class SimpleGrantedAuthorityDeserializer : JsonDeserializer<SimpleGrantedAuthority>() {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): SimpleGrantedAuthority? {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            return SimpleGrantedAuthority(parser.text)
        }
        return null
    }
}