package com.charge71.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

/**
 * Centralized configuration for Jackson's ObjectMapper.
 * 
 * @author Diego Bardari
 *
 */
@Configuration
public class JacksonConfig {

	/**
	 * Return an instance of ObjectMapper.
	 * 
	 * @return an instance of ObjectMapper
	 */
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(new ISO8601DateFormat());
		return mapper;
	}
}
