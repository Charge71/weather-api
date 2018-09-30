package com.charge71.weather.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Generic response bean including a list of serializable objects and the
 * response date.
 * 
 * @author Diego Bardari
 *
 * @param <T>
 *            the type of objects in the returned list
 */
public class ResponseItems<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -5899938873056845451L;

	private List<T> items;

	private Date date;

	/**
	 * Return the list of objects in the response.
	 * 
	 * @return the list of objects in the response
	 */
	public List<T> getItems() {
		return items;
	}

	/**
	 * Set the list of objects in the response.
	 * 
	 * @param items
	 *            the list of objects in the response
	 */
	public void setItems(List<T> items) {
		this.items = items;
	}

	/**
	 * Add an object to the response.
	 * 
	 * @param item
	 *            the object to add
	 */
	public void addItem(T item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
	}

	/**
	 * Return the date of the response.
	 * 
	 * @return the date of the response
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the date of the response.
	 * 
	 * @param date
	 *            the date of the response
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
