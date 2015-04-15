/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.CVResult;
import com.velocity.enums.TypeCardType;

/**
 * This class defines the attributes for BankcardData.
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class BankcardData {

	@SerializedName("AVSResult")
	private AVSResult aVSResult;

	@SerializedName("CardTypes")
	private TypeCardType cardTypes;

	@SerializedName("CVResult")
	private CVResult cVResult;

	@SerializedName("MaskedPAN")
	private String maskedPAN;

	@SerializedName("OrderId")
	private String orderId;

	public AVSResult getaVSResult() {
		return aVSResult;
	}

	public void setaVSResult(AVSResult aVSResult) {
		this.aVSResult = aVSResult;
	}

	public TypeCardType getCardTypes() {
		return cardTypes;
	}

	public void setCardTypes(TypeCardType cardTypes) {
		this.cardTypes = cardTypes;
	}

	public CVResult getcVResult() {
		return cVResult;
	}

	public void setcVResult(CVResult cVResult) {
		this.cVResult = cVResult;
	}

	public String getMaskedPAN() {
		return maskedPAN;
	}

	public void setMaskedPAN(String maskedPAN) {
		this.maskedPAN = maskedPAN;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
