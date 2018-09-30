package com.charge71.weather.service.impl;

import java.io.InputStream;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.charge71.weather.bean.DataResponseItem;
import com.charge71.weather.service.CityWeatherService;
import com.charge71.weather.service.OpenWeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityWeatherServiceImplTest {

	@Autowired
	private ObjectMapper objectMapper;

	@TestConfiguration
	static class CityWeatherServiceImplTestConfiguration {

		@Bean
		public CityWeatherService cityWeatherService() {
			return new CityWeatherServiceImpl();
		}
	}

	private int cityId = 524901;
	private int undefinedCityId = 999999999;

	/**
	 * Initializes the mocked OpenWeatherService to return a given response for
	 * 524901 or null for 999999999.
	 */
	@Before
	public void setUp() {
		try (InputStream is = CityWeatherServiceImplTest.class.getResourceAsStream("/response/524901.json")) {
			ObjectNode objectNode = objectMapper.readValue(is, ObjectNode.class);
			Mockito.when(openWeatherService.getForecast(cityId)).thenReturn(objectNode);
			Mockito.when(openWeatherService.getForecast(undefinedCityId)).thenReturn(null);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Autowired
	private CityWeatherService cityWeatherService;

	@MockBean
	private OpenWeatherService openWeatherService;

	/**
	 * Should return not null averages for September 29th, 2018 and 524901 city id.
	 */
	@Test
	public void getWeatherAveragesTest() {
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 8, 29); // September 29th
		try {
			DataResponseItem responseItem = cityWeatherService.getWeatherAverages(cal, cityId);
			Assert.assertNotNull(responseItem);
			Assert.assertEquals(cityId, responseItem.getId());
			Assert.assertNotNull(responseItem.getTempAvgDaily());
			Assert.assertNotNull(responseItem.getTempAvgNightly());
			Assert.assertNotNull(responseItem.getPressureAvg());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Should return null averages for September 29th, 2017 and 524901 city id
	 * (wrong date).
	 */
	@Test
	public void getWeatherAveragesTestWithNullValues() {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(2017, 9, 29);
		try {
			DataResponseItem responseItem = cityWeatherService.getWeatherAverages(cal, cityId);
			Assert.assertNotNull(responseItem);
			Assert.assertEquals(cityId, responseItem.getId());
			Assert.assertNull(responseItem.getTempAvgDaily());
			Assert.assertNull(responseItem.getTempAvgNightly());
			Assert.assertNull(responseItem.getPressureAvg());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Should return null averages for September 29th, 2018 and 999999999 city id
	 * (wrong city id).
	 */
	@Test
	public void shouldReturnNull() {
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 9, 29);
		try {
			DataResponseItem responseItem = cityWeatherService.getWeatherAverages(cal, undefinedCityId);
			Assert.assertNull(responseItem);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
