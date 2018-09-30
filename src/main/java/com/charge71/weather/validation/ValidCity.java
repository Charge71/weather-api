package com.charge71.weather.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation that marks a parameter to be a city name supported by
 * OpenWeatherMap.
 * 
 * @author Diego Bardari
 *
 */
@Documented
@Constraint(validatedBy = CityNameValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCity {
	String message() default "Invalid city name";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
