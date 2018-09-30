package com.charge71.weather.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.charge71.weather.service.OpenWeatherService;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * OpenWeatherService concrete implementation.
 * 
 * @author Diego Bardari
 * @See {@link OpenWeatherService}
 */
@Service
@CacheConfig(cacheNames = { "forecast" })
public class OpenWeatherServiceImpl implements OpenWeatherService {

	private static final Logger logger = LoggerFactory.getLogger(OpenWeatherServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${weather.service.base.url}")
	private String serviceBaseUrl;

	@Value("${weather.service.app.id}")
	private String serviceAppId;

	/**
	 * This method calls the forecast API of OpenWeatherMap
	 * (https://openweathermap.org/forecast5) to get forecast informations about a
	 * city with the given id. The API is called with units parameter to metric to
	 * have Celsius temperatures. This implementation is cached with a TTL
	 * configured in the application properties.
	 * 
	 */
	@Override
	@Cacheable
	public ObjectNode getForecast(int cityId) throws Exception {
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(serviceBaseUrl).path("/forecast")
				.queryParam("appid", serviceAppId).queryParam("id", cityId).queryParam("units", "metric").build();
		logger.debug("Connecting to weather service:\n" + uriComponents.toUriString());
		try {
			return restTemplate.getForObject(uriComponents.toUriString(), ObjectNode.class);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				logger.warn("Not found city id " + cityId);
				return null;
			}
			logger.error("Weather service returned code " + e.getStatusCode());
			throw new Exception("Weather service returned code " + e.getStatusCode());
		}
	}

}
