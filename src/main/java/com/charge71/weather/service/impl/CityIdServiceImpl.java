package com.charge71.weather.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charge71.weather.bean.DataResponseItem;
import com.charge71.weather.service.CityIdService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * CityIdService concrete implementation.
 * 
 * @author Diego Bardari
 * @see {@link CityIdService}
 */
@Service
public class CityIdServiceImpl implements CityIdService {

	private static final Logger logger = LoggerFactory.getLogger(CityIdServiceImpl.class);

	@Autowired
	private ObjectMapper objectMapper;

	private Map<String, Set<Integer>> cities = new HashMap<>();

	/**
	 * This method initialize the service by loading the JSON provided by
	 * OpenWeatherMap and creating a map of names and ids.
	 * 
	 */
	@PostConstruct
	public void init() {
		// load the json as a result
		try (InputStream is = CityIdServiceImpl.class.getResourceAsStream("/city.list.json")) {
			List<DataResponseItem> list = objectMapper.readValue(is, new TypeReference<List<DataResponseItem>>() {
			});
			list.forEach(item -> {
				// add each id to the set for the name
				Set<Integer> cset = cities.get(item.getName());
				if (cset == null) {
					cset = new HashSet<>();
					cities.put(item.getName(), cset);
				}
				cset.add(item.getId());
			});
			logger.info("CityIdServiceImpl initialized");
		} catch (IOException e) {
			logger.error("CityIdServiceImpl init error", e);
			throw new IllegalStateException();
		}
	}

	@Override
	public Set<Integer> getCities(String cityName) {
		logger.debug("Requested city name " + cityName);
		Set<Integer> result = cities.get(cityName);
		if (result == null) {
			logger.warn("No ids found for " + cityName);
		} else {
			logger.info(result.size() + " ids found for " + cityName);
		}
		return result;
	}

	@Override
	public boolean isValidCity(String cityName) {
		return cities.containsKey(cityName);
	}

}
