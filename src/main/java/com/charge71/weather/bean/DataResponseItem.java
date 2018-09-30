package com.charge71.weather.bean;

import java.io.Serializable;

/**
 * This bean is the result of the forecast for a single city. It includes the
 * name, id, country, geocoordinates and the forecast in the form of the next
 * three days daily and nighly temperature average and pressure average.
 * 
 * @author Diego Bardari
 *
 */
public class DataResponseItem implements Serializable {

	private static final long serialVersionUID = 8254248218434989191L;

	public static class Coords {
		private double lon;
		private double lat;

		/**
		 * Return the longitude of the city.
		 * 
		 * @return the longitude of the city
		 */
		public double getLon() {
			return lon;
		}

		/**
		 * Set the longitude of the city.
		 * 
		 * @param lon
		 *            the longitude of the city
		 */
		public void setLon(double lon) {
			this.lon = lon;
		}

		/**
		 * Return the latitude of the city.
		 * 
		 * @return the latitude of the city
		 */
		public double getLat() {
			return lat;
		}

		/**
		 * Set the latitude of the city.
		 * 
		 * @param lat
		 *            the latitude of the city
		 */
		public void setLat(double lat) {
			this.lat = lat;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Coords [lon=").append(lon).append(", lat=").append(lat).append("]");
			return builder.toString();
		}
	}

	private int id;
	private String name;
	private String country;
	private Coords coord;
	private Double tempAvgDaily;
	private Double tempAvgNightly;
	private Double pressureAvg;

	/**
	 * Return the OpenWeatherMap of the city.
	 * 
	 * @return the OpenWeatherMap of the city
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the OpenWeatherMap of the city.
	 * 
	 * @param id
	 *            the OpenWeatherMap of the city
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return the name of the city.
	 * 
	 * @return the name of the city
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the city.
	 * 
	 * @param name
	 *            the name of the city
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the country of the city.
	 * 
	 * @return the country of the city
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Set the country of the city.
	 * 
	 * @param country
	 *            the country of the city
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Return the geocoordinates of the city.
	 * 
	 * @return the geocoordinates of the city
	 */
	public Coords getCoord() {
		return coord;
	}

	/**
	 * Set the geocoordinates of the city.
	 * 
	 * @param coord
	 *            the geocoordinates of the city
	 */
	public void setCoord(Coords coord) {
		this.coord = coord;
	}

	/**
	 * Return the average daily temperature for the next three days. Temperature is
	 * daily when the local time is between 6 and 18 o'clock included.
	 * 
	 * @return the average daily temperature for the next three days
	 */
	public Double getTempAvgDaily() {
		return tempAvgDaily;
	}

	/**
	 * Set the average daily temperature for the next three days.
	 * 
	 * @param tempAvgDaily
	 *            the average daily temperature for the next three days
	 */
	public void setTempAvgDaily(Double tempAvgDaily) {
		this.tempAvgDaily = tempAvgDaily;
	}

	/**
	 * Return the average nightly temperature for the next three days. Temperature
	 * is nightly when the local time is between 18 and 6 o'clock included.
	 * 
	 * @return the average nightly temperature for the next three days
	 */
	public Double getTempAvgNightly() {
		return tempAvgNightly;
	}

	/**
	 * Set the average nightly temperature for the next three days.
	 * 
	 * @param tempAvgNightly
	 *            the average nightly temperature for the next three days
	 */
	public void setTempAvgNightly(Double tempAvgNightly) {
		this.tempAvgNightly = tempAvgNightly;
	}

	/**
	 * Return the average pressure for the next three days.
	 * 
	 * @return the average pressure for the next three days
	 */
	public Double getPressureAvg() {
		return pressureAvg;
	}

	/**
	 * Set the average pressure for the next three days.
	 * 
	 * @param pressureAvg
	 *            the average pressure for the next three days
	 */
	public void setPressureAvg(Double pressureAvg) {
		this.pressureAvg = pressureAvg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataResponseItem [id=").append(id).append(", name=").append(name).append(", country=")
				.append(country).append(", coord=").append(coord).append(", tempAvgDaily=").append(tempAvgDaily)
				.append(", tempAvgNightly=").append(tempAvgNightly).append(", pressureAvg=").append(pressureAvg)
				.append("]");
		return builder.toString();
	}

}
