package com.charge71.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.iakovlev.timeshape.TimeZoneEngine;

/**
 * Centralized configuration for the Timeshape library. This library allows the
 * mapping between geocoordinates and time zones.
 * 
 * @author Diego Bardari
 *
 */
@Configuration
public class TimeZoneEngineConfig {

	/**
	 * Return an initialized instance of the TimeZoneEngine.
	 * 
	 * @return an initialized instance of the TimeZoneEngine
	 */
	@Bean
	public TimeZoneEngine getTimeZoneEngine() {
		return TimeZoneEngine.initialize();
	}

}
