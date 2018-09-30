package com.charge71.weather.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class is a client of the OpenWeatherMap API.
 * 
 * @author Diego Bardari
 *
 */
public interface OpenWeatherService {

	/**
	 * Return the JSON object returned by the forecast OpenWeatherMap API for the
	 * given city id or <code>null</code> if the id does not exist.
	 * 
	 * @param cityId
	 *            the city id to look for
	 * @return the JSON object returned by the forecast OpenWeatherMap API for the
	 *         given city id or <code>null</code> if the id does not exist
	 * @throws Exception
	 */
	public ObjectNode getForecast(int cityId) throws Exception;

}
