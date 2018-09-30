package com.charge71.weather.service;

import java.util.Set;

/**
 * This service uses a map of cities and their ids to return all the ids for the
 * given city name.
 * 
 * @author Diego Bardari
 *
 */
public interface CityIdService {

	/**
	 * Return a list of city ids with the given cityName or <code>null</code> if
	 * there's none.
	 * 
	 * @param cityName
	 *            the name of the city to search
	 * @return a list of <code>CityBean</code> with the given cityName or
	 *         <code>null</code> if there's none.
	 */
	public Set<Integer> getCities(String cityName);

	/**
	 * Return <code>true</code> if a key exist in the cities map, I.E. if at least a
	 * city exist with the given name, <code>false</code> otherwise.
	 * 
	 * @param cityName
	 * @return <code>true</code> if a key exist in the cities map,
	 *         <code>false</code> otherwise
	 */
	public boolean isValidCity(String cityName);

}
