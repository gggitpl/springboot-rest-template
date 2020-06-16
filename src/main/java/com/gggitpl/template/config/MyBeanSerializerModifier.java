package com.gggitpl.template.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 获取数据时, 对返回数据进行格式化输出<br/>
 * null array -> []<br/>
 * null number -> 0<br/>
 * null boolean -> false<br/>
 * null string -> ""<br/>
 *
 * @author gggitpl
 */
public class MyBeanSerializerModifier extends BeanSerializerModifier {
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (Object beanProperty : beanProperties) {
            BeanPropertyWriter writer = (BeanPropertyWriter) beanProperty;
            if (isArrayType(writer)) {
                writer.assignNullSerializer(new NullArrayJsonSerializer());
            } else if (isNumberType(writer)) {
                writer.assignNullSerializer(new NullNumberJsonSerializer());
            } else if (isBooleanType(writer)) {
                writer.assignNullSerializer(new NullBooleanJsonSerializer());
            } else if (isStringType(writer)) {
                writer.assignNullSerializer(new NullStringJsonSerializer());
            }
        }
        return beanProperties;
    }

    private boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    private boolean isStringType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz);
    }


    private boolean isNumberType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return Number.class.isAssignableFrom(clazz);
    }

    private boolean isBooleanType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Boolean.class);
    }

    public static class NullArrayJsonSerializer extends JsonSerializer<Object> {

        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value == null) {
                jgen.writeStartArray();
                jgen.writeEndArray();
            }
        }
    }

    public static class NullStringJsonSerializer extends JsonSerializer<Object> {

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("");
        }
    }

    public static class NullNumberJsonSerializer extends JsonSerializer<Object> {

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(0);
        }
    }

    public static class NullBooleanJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeBoolean(false);
        }
    }
}
