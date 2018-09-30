package com.charge71.weather.controller.v1;

import java.util.Calendar;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charge71.weather.bean.DataResponseItem;
import com.charge71.weather.bean.ResponseItems;
import com.charge71.weather.service.CityIdService;
import com.charge71.weather.service.CityWeatherService;
import com.charge71.weather.validation.ValidCity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller for APIs related to weather forecast. API version 1.
 * 
 * @author Diego Bardari
 *
 */
@RequestMapping("/v1/weather")
@RestController
@Validated
@Api(tags = "weather")
public class WeatherController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@Autowired
	private CityIdService cityIdService;

	@Autowired
	private CityWeatherService cityWeatherService;

	/**
	 * Return a list of DataResponseItem for the requested city, or a meaningful
	 * error. The name of the city is validated to be included in the ones supported
	 * by OpenWeatherMap.
	 * 
	 * @param cityName
	 *            the name of the city
	 * @returna list of DataResponseItem for the requested city
	 */
	@GetMapping(path = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Return a list of forecast for the cities with the given CITY name")
	public ResponseEntity<ResponseItems<DataResponseItem>> data(
			@ApiParam(value = "The name of the city", required = true) @RequestParam("CITY") @ValidCity String cityName) {
		logger.info("Requested data for city name: " + cityName);
		// get the ids of the given city
		Set<Integer> cities = cityIdService.getCities(cityName);
		if (cities == null) {
			// because of validation it should never enter here
			logger.warn("No results for city name: " + cityName);
			return ResponseEntity.notFound().build();
		}
		ResponseItems<DataResponseItem> response = new ResponseItems<>();
		Calendar today = Calendar.getInstance();
		cities.forEach(cityId -> {
			// for each city get the response or log an error
			try {
				DataResponseItem item = cityWeatherService.getWeatherAverages(today, cityId);
				response.addItem(item);
			} catch (Exception e) {
				logger.error("Error getting weather for city id " + cityId, e);
			}
		});
		response.setDate(today.getTime());
		return ResponseEntity.ok(response);
	}

}
