package com.charge71.weather.controller.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class handles exceptions thrown by the controllers to return meaningful
 * http responses.
 * 
 * @author Diego Bardari
 *
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles ConstraintViolationException to return code 400.
	 * 
	 * @param ex
	 *            the thrown ConstraintViolationException
	 * @return a 400 http response
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		return ResponseEntity.badRequest().build();
	}
}
