package com.velocity.models.transactions.query;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for TransactionDateRange
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class TransactionDateRange {

	@SerializedName("EndDateTime")
	private String endDateTime;

	@SerializedName("StartDateTime")
	private String startDateTime;

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

}
