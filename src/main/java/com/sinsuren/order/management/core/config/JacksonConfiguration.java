package com.sinsuren.order.management.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by surender.s on 05/10/17.
 */

@Configuration
@PropertySources({
        @PropertySource("jackson.yml")
        })
public class JacksonConfiguration {

    @Value("${order-management.core.jackson.date-format}")
    String dateFormat;

    @Primary
    @Bean
    public ObjectMapper httpClientObjectMapper() {

        //return getBasicObjectMappedBuilder().dateFormat(new SimpleDateFormat("yyyy-MM-dd")).build();
        return Jackson2ObjectMapperBuilder.
                json()
                .dateFormat(new SimpleDateFormat(dateFormat))
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .timeZone(TimeZone.getTimeZone("Asia/Calcutta"))
                .modules(new JodaModule())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(JsonGenerator.Feature.ESCAPE_NON_ASCII)
                .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .featuresToEnable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .build();
    }


    @Bean(name = "camelCase")
    public ObjectMapper objectMapperCamelCase() {

        //return getBasicObjectMappedBuilder().dateFormat(new SimpleDateFormat("yyyy-MM-dd")).build();
        return Jackson2ObjectMapperBuilder.
                json()
                .dateFormat(new SimpleDateFormat(dateFormat))
//                .modules(new JodaModule())
                .timeZone(TimeZone.getTimeZone("Asia/Calcutta"))
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(JsonGenerator.Feature.ESCAPE_NON_ASCII)
                .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .featuresToEnable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

    @Bean(name = "redis")
    public ObjectMapper objectMapperForRedis() {


        //return getBasicObjectMappedBuilder().dateFormat(new SimpleDateFormat("yyyy-MM-dd")).build();
        return Jackson2ObjectMapperBuilder.
                json()
                .dateFormat(new SimpleDateFormat(dateFormat))
                .modules(new JodaModule())
                .timeZone(TimeZone.getTimeZone("Asia/Calcutta"))
                .defaultTyping(getDefaultTyping())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(JsonGenerator.Feature.ESCAPE_NON_ASCII)
                .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .featuresToEnable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }


    private TypeResolverBuilder<?> getDefaultTyping() {
        ObjectMapper.DefaultTyping applicability = ObjectMapper.DefaultTyping.NON_FINAL;
        JsonTypeInfo.As includeAs = JsonTypeInfo.As.PROPERTY;

        TypeResolverBuilder<?> typer = new ObjectMapper.DefaultTypeResolverBuilder(applicability);
        typer = typer.init(JsonTypeInfo.Id.CLASS, null);
        typer = typer.inclusion(includeAs);
        return typer;
    }

}

