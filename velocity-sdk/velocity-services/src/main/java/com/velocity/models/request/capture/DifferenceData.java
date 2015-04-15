/**
 * 
 */
package com.velocity.models.request.capture;

import com.velocity.enums.VelocityEnums;

/**
 * This class defines the entities for DifferenceData for Capture method.
 * 
 * @author vimalk2
 * @date 06-January-2015
 */
public class DifferenceData {

	private VelocityEnums type;

	private String transactionId;

	private String amount;

	private String tipAmount;

	public VelocityEnums getType() {
		return type;
	}

	public void setType(VelocityEnums type) {
		this.type = type;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(String tipAmount) {
		this.tipAmount = tipAmount;
	}

}
