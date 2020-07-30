package dev.alnat.moneykeeper.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by @author AlNat on 27.07.2020.
 * Licensed by Apache License, Version 2.0
 */
@Configuration
public class MessageConverterConfiguration {


    // Указываем список конверторов для строки конвертации через строку
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);

        List<MediaType> supportedMediaTypesList = List.of(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_HTML,
                MediaType.MULTIPART_FORM_DATA
        );
        converter.setSupportedMediaTypes(supportedMediaTypesList);

        return converter;
    }


    // Переопределяем стандартные конверторы XML и JSON у Jackson для поддержки полей c Lazy

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new HibernateAwareObjectMapper());
        return converter;
    }


    @Bean
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter() {
        MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();
        converter.setObjectMapper(new HibernateAwareXmlMapper());
        return converter;
    }


    /**
     * Данный класс реализует корректную обработку ошибки LazyInitializationException при преобразовании в JSON
     */
    public static class HibernateAwareObjectMapper extends ObjectMapper {

        public HibernateAwareObjectMapper() {
            registerModule(new Hibernate5Module());
        }

    }

    /**
     * Данный класс реализует корректную обработку ошибки LazyInitializationException при преобразовании в XML
     */
    public static class HibernateAwareXmlMapper extends XmlMapper {

        public HibernateAwareXmlMapper() {
            registerModule(new Hibernate5Module());
        }

    }

}
