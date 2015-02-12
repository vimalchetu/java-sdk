/**
 * 
 */
package com.velocity.models.request.verify;

import com.velocity.enums.VelocityEnums;

/**
 * This model class holds the data for Transaction
 * 
 * @author vimalk2
 * @date 13-January-2015
 */
public class Transaction {

	private TenderData tenderData;

	private TransactionData transactionData;

	private VelocityEnums type;

	public TenderData getTenderData() {
		if (tenderData == null) {
			tenderData = new TenderData();
		}
		return tenderData;
	}

	public void setTenderData(TenderData tenderData) {
		this.tenderData = tenderData;
	}

	public TransactionData getTransactionData() {

		if (transactionData == null) {
			transactionData = new TransactionData();
		}

		return transactionData;
	}

	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}

	public VelocityEnums getType() {
		return type;
	}

	public void setType(VelocityEnums type) {
		this.type = type;
	}

}
