/**
 * 
 */
package com.velocity.models.request.adjust;

/**
 * This class holds the data for DifferenceData for Adjust method
 * @author vimalk2
 * @date 13-January-2015
 */
public class DifferenceData {

	private String amount;

	private String transactionId;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
