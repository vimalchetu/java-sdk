/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.TransactionState;

/**
 * This class defines the attributes for FamilyInformation .
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class FamilyInformation {

	@SerializedName("FamilyId")
	private String familyId;

	@SerializedName("FamilySequenceCount")
	private Integer familySequenceCount;

	@SerializedName("FamilySequenceNumber")
	private Integer familySequenceNumber;

	@SerializedName("FamilyState")
	private TransactionState familyState;

	@SerializedName("NetAmount")
	private float netAmount;

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public Integer getFamilySequenceCount() {
		return familySequenceCount;
	}

	public void setFamilySequenceCount(Integer familySequenceCount) {
		this.familySequenceCount = familySequenceCount;
	}

	public TransactionState getFamilyState() {
		return familyState;
	}

	public void setFamilyState(TransactionState familyState) {
		this.familyState = familyState;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	public Integer getFamilySequenceNumber() {
		return familySequenceNumber;
	}

	public void setFamilySequenceNumber(Integer familySequenceNumber) {
		this.familySequenceNumber = familySequenceNumber;
	}

}
