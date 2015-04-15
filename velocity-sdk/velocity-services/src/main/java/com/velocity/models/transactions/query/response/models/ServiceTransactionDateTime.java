package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for ServiceTransactionDateTime
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class ServiceTransactionDateTime {

	@SerializedName("Date")
	private String date;

	@SerializedName("Time")
	private String time;

	@SerializedName("TimeZone")
	private String timeZone;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
