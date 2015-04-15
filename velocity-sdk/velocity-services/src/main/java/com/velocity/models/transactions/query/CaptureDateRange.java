package com.velocity.models.transactions.query;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for CaptureDateRange
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class CaptureDateRange {

	@SerializedName("StartDateTime")
	private String startDateTime;

	@SerializedName("EndDateTime")
	private String endDateTime;

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

}
