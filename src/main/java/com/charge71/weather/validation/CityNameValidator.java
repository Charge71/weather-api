package com.charge71.weather.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.charge71.weather.service.CityIdService;

/**
 * This class implements validation for a city name to be in the list of cities
 * supported by OpenWeatherMap.
 * 
 * @author Diego Bardari
 *
 */
@Component
public class CityNameValidator implements ConstraintValidator<ValidCity, String> {

	private static final Logger logger = LoggerFactory.getLogger(CityNameValidator.class);

	@Autowired
	private CityIdService cityIdService;

	@Override
	public void initialize(ValidCity validCity) {
	}

	/**
	 * Return <code>true</code> if the city name is supported by OpenWeatherMap,
	 * <code>false</code> otherwise.
	 */
	@Override
	public boolean isValid(String cityName, ConstraintValidatorContext context) {
		boolean isValid = cityIdService.isValidCity(cityName);
		if (!isValid) {
			logger.warn("Not valid city name " + cityName);
		}
		return isValid;
	}

}
