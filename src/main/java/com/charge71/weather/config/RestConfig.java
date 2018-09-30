package com.charge71.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Centralized configuration for RestTemplate.
 * 
 * @author Diego Bardari
 *
 */
@Configuration
public class RestConfig {

	/**
	 * Return an instance of RestTemplate.
	 * 
	 * @return an instance of RestTemplate
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate(getClientHttpRequestFactory());
	}

	/**
	 * Return a ClientHttpRequestFactory instance initialized with one minute
	 * timeouts.
	 * 
	 * @return a ClientHttpRequestFactory instance initialized with one minute
	 *         timeouts
	 */
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 60000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		clientHttpRequestFactory.setConnectionRequestTimeout(timeout);
		return clientHttpRequestFactory;
	}

}
