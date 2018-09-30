package com.charge71.weather.service;

import java.util.Calendar;

import com.charge71.weather.bean.DataResponseItem;

/**
 * This service return a DataResponseItem with the forecast for a city.
 * 
 * @author Diego Bardari
 *
 */
public interface CityWeatherService {

	/**
	 * Return a DataResponseItem with the forecast for the given city id and date.
	 * 
	 * @param date
	 *            the date to get the forecast
	 * @param cityId
	 *            the city id
	 * @return a DataResponseItem with the forecast for the given city id and date
	 * @throws Exception
	 *             if an error occurs while getting the data
	 */
	public DataResponseItem getWeatherAverages(Calendar date, int cityId) throws Exception;

}
