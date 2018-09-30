package com.charge71.weather.service.impl;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.charge71.weather.service.CityIdService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityIdServiceImplTest {

	@TestConfiguration
	static class CityIdServiceImplTestConfiguration {

		@Bean
		public CityIdService cityIdService() {
			return new CityIdServiceImpl();
		}
	}

	@Autowired
	private CityIdService cityIdService;

	/**
	 * Should return a single city named Milano.
	 */
	@Test
	public void shouldReturnSingleResult() {
		Set<Integer> cities = cityIdService.getCities("Milano");
		Assert.assertNotNull(cities);
		Assert.assertEquals(1, cities.size());
		Assert.assertEquals(3173435, cities.iterator().next().intValue());
	}

	/**
	 * Should return 7 cities called London.
	 */
	@Test
	public void shouldReturnMultipleResults() {
		Set<Integer> cities = cityIdService.getCities("London");
		Assert.assertNotNull(cities);
		Assert.assertEquals(7, cities.size());
	}

	/**
	 * Should return null as there's no city called Pippo.
	 */
	@Test
	public void shouldReturnNull() {
		Set<Integer> cities = cityIdService.getCities("Pippo");
		Assert.assertNull(cities);
	}
}
