package com.charge71.weather.service.impl;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charge71.weather.bean.DataResponseItem;
import com.charge71.weather.service.CityWeatherService;
import com.charge71.weather.service.OpenWeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.iakovlev.timeshape.TimeZoneEngine;

/**
 * CityWeatherService concrete implementation.
 * 
 * @author Diego Bardari
 * @see {@link CityWeatherService}
 */
@Service
public class CityWeatherServiceImpl implements CityWeatherService {

	private static final Logger logger = LoggerFactory.getLogger(CityWeatherServiceImpl.class);

	@Autowired
	private OpenWeatherService openWeatherService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TimeZoneEngine timeZoneEngine;

	@Override
	public DataResponseItem getWeatherAverages(final Calendar current, int cityId) throws Exception {
		// get forecast json from openWeatherService
		ObjectNode forecast = openWeatherService.getForecast(cityId);
		if (forecast == null) {
			// no city with id
			return null;
		}
		// initialize the response with the city data
		DataResponseItem response = objectMapper.treeToValue(forecast.get("city"), DataResponseItem.class);
		// get the time zone to be used from the given city
		Optional<ZoneId> optZone = timeZoneEngine.query(response.getCoord().getLat(), response.getCoord().getLon());
		if (!optZone.isPresent()) {
			// unable to get the time zone
			throw new Exception("Unable to get city time zone");
		}
		// get the time zone
		ZoneId zoneId = optZone.get();
		TimeZone timeZone = TimeZone.getTimeZone(zoneId);
		// lists to be used for averages
		List<Double> daily = new ArrayList<>();
		List<Double> nightly = new ArrayList<>();
		List<Double> pressure = new ArrayList<>();

		// cycle the list of forecasts
		forecast.get("list").forEach(item -> {
			// check if the forecast is for one of the next three days
			long dt = item.get("dt").asLong() * 1000; // milliseconds UTC
			Calendar cal = Calendar.getInstance(timeZone);
			cal.setTimeInMillis(dt);
			if (nextThreeDays(current, cal)) {
				// do process the forecast
				logger.debug("Processing day " + cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1));
				JsonNode mainNode = item.get("main");
				// is it daily or nightly or both?
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				if (daily(hour)) {
					double temp = mainNode.get("temp").asDouble();
					daily.add(temp);
					logger.debug("Hour " + hour + " added daily temp " + temp);
				}
				if (nightly(hour)) {
					double temp = mainNode.get("temp").asDouble();
					nightly.add(temp);
					logger.debug("Hour " + hour + " added nightly temp " + temp);
				}
				// always add pressure
				double press = mainNode.get("pressure").asDouble();
				pressure.add(press);
				logger.debug("Hour " + hour + " added pressure " + press);
			}
		});
		// using streams to get averages
		try {
			Double dailyAvg = daily.stream().mapToDouble(a -> a).average().getAsDouble();
			response.setTempAvgDaily(dailyAvg);
		} catch (NoSuchElementException e) {
			logger.warn("No values for daily temperature.");
		}
		try {
			Double nightyAvg = nightly.stream().mapToDouble(a -> a).average().getAsDouble();
			response.setTempAvgNightly(nightyAvg);
		} catch (NoSuchElementException e) {
			logger.warn("No values for nightly temperature.");
		}
		try {
			Double pressureAvg = pressure.stream().mapToDouble(a -> a).average().getAsDouble();
			response.setPressureAvg(pressureAvg);
		} catch (NoSuchElementException e) {
			logger.warn("No values for pressure.");
		}
		return response;
	}

	//

	/**
	 * Checks if two calendars are the same day, I.E. the same day of year and the
	 * same year.
	 * 
	 * @param cal1
	 * @param cal2
	 * @return <code>true</code> if the calendars are the same day,
	 *         <code>false</code> otherwise
	 */
	private static boolean sameDay(Calendar cal1, Calendar cal2) {
		return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	/**
	 * Checks if a calendar is one, two or three days after another calendar. It
	 * uses calendars to better handle edge cases like last days of the year.
	 * 
	 * @param current
	 * @param calendar
	 * @return <code>true</code> if calendar is one, two or three days after
	 *         current, <code>false</code> otherwise
	 */
	private static boolean nextThreeDays(Calendar current, Calendar calendar) {
		// clone current calendar to not modify it
		Calendar clone = Calendar.getInstance();
		clone.setTime(current.getTime());
		// check if is one day after
		clone.add(Calendar.DATE, 1);
		if (sameDay(clone, calendar)) {
			return true;
		}
		// check if is two days after
		clone.add(Calendar.DATE, 1);
		if (sameDay(clone, calendar)) {
			return true;
		}
		// check if is three days after
		clone.add(Calendar.DATE, 1);
		if (sameDay(clone, calendar)) {
			return true;
		}
		return false;
	}

	/**
	 * Return <code>true</code> if the hour is between 6 and 18 included,
	 * <code>false</code> otherwise.
	 * 
	 * @param hour
	 *            the hour to check
	 * @return <code>true</code> if the hour is between 6 and 18 included,
	 *         <code>false</code> otherwise
	 */
	private static boolean daily(int hour) {
		return hour >= 6 && hour <= 18;
	}

	/**
	 * Return <code>true</code> if the hour is between 18 and 6 included,
	 * <code>false</code> otherwise.
	 * 
	 * @param hour
	 *            the hour to check
	 * @return <code>true</code> if the hour is between 18 and 6 included,
	 *         <code>false</code> otherwise
	 */
	private static boolean nightly(int hour) {
		return hour <= 6 || hour >= 18;
	}
}
