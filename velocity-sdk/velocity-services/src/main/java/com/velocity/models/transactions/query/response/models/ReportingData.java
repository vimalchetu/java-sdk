package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for ReportingData
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class ReportingData {

	@SerializedName("Comment")
	private String comment;

	@SerializedName("Description")
	private String description;

	@SerializedName("Reference")
	private String reference;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
