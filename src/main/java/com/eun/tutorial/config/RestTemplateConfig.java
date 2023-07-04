package com.eun.tutorial.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.eun.tutorial.dto.main.RealEstatePriceItem.ItemsDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;

@Configuration
public class RestTemplateConfig {

    // RestTemplate bean creation and other configurations...

    @Bean
    public RestTemplate customRestTemplate(ObjectMapper objectMapper) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set custom ObjectMapper
        restTemplate.getMessageConverters().stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .map(converter -> (MappingJackson2HttpMessageConverter) converter)
                .forEach(converter -> converter.setObjectMapper(objectMapper));

        return restTemplate;
    }

    @Configuration
    public static class ObjectMapperConfig {

    	@Bean
    	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    	    return builder -> builder
    	            .postConfigurer(objectMapper -> {
    	                objectMapper.coercionConfigFor(ItemsDTO.class)
    	                        .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
    	                objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    	            });
    	}
    }
}