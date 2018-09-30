package com.charge71.weather.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.charge71.weather.service.OpenWeatherService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherServiceImplTest {

	@TestConfiguration
	static class OpenWeatherServiceImplTestConfiguration {

		@Bean
		public OpenWeatherService openWeatherService() {
			return new OpenWeatherServiceImpl();
		}
	}

	@Autowired
	private OpenWeatherService openWeatherService;

	/**
	 * Should return Moscow for the given id.
	 */
	@Test
	public void shouldReturnMoscow() {
		try {
			ObjectNode node = openWeatherService.getForecast(524901);
			Assert.assertNotNull(node);
			Assert.assertNotNull(node.get("city"));
			Assert.assertNotNull(node.get("city").get("name"));
			Assert.assertEquals("Moscow", node.get("city").get("name").asText());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	/**
	 * Should return null for the given fake id.
	 */
	@Test
	public void shouldReturnNull() {
		try {
			ObjectNode node = openWeatherService.getForecast(9999999);
			Assert.assertNull(node);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}
}
