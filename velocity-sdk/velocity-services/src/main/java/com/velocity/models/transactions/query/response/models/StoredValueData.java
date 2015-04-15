/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.CVResult;
import com.velocity.enums.CardStatus;

/**
 * This class defines the attributes for StoredValueData
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class StoredValueData {

	@SerializedName("CardRestrictionValue")
	private String cardRestrictionValue;

	@SerializedName("CardStatus")
	private CardStatus cardStatus;

	@SerializedName("CVResult")
	private CVResult cVResult;

	@SerializedName("NewBalance")
	private float newBalance;

	@SerializedName("OrderId")
	private String orderId;

	@SerializedName("PreviousBalance")
	private float previousBalance;

	public String getCardRestrictionValue() {
		return cardRestrictionValue;
	}

	public void setCardRestrictionValue(String cardRestrictionValue) {
		this.cardRestrictionValue = cardRestrictionValue;
	}

	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	public CVResult getcVResult() {
		return cVResult;
	}

	public void setcVResult(CVResult cVResult) {
		this.cVResult = cVResult;
	}

	public float getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(float newBalance) {
		this.newBalance = newBalance;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public float getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(float previousBalance) {
		this.previousBalance = previousBalance;
	}

}
