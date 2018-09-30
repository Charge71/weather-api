package com.charge71.weather.controller.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.charge71.weather.WeatherApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WeatherApplication.class)
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	/**
	 * Calling the /data API with city param Moscow should return all 3 cities named
	 * Moscow.
	 */
	@Test
	public void shouldReturnMoscow() {
		try {
			mvc.perform(get("/v1/weather/data").param("CITY", "Moscow").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.items.length()", is(3))).andExpect(jsonPath("$.items[0].name", is("Moscow")))
					.andExpect(jsonPath("$.items[1].name", is("Moscow")))
					.andExpect(jsonPath("$.items[2].name", is("Moscow")));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Calling the /data API with city param Pippo should return http code 400
	 * (invalid name).
	 */
	@Test
	public void shouldReturnBadRequest() {
		try {
			mvc.perform(get("/v1/weather/data").param("CITY", "Pippo").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
