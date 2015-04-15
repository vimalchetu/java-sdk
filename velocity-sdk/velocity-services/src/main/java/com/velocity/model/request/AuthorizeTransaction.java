package com.velocity.model.request;

import com.velocity.model.request.Transaction;

/**
 * This model class holds the data for the authorization of the Transaction.
 * 
 * @author Vimal Kumar
 * @date 30-December-2014
 */
public class AuthorizeTransaction {

	private String type;
	private String applicationProfileId;
	private String merchantProfileId;
	private Transaction transaction;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplicationProfileId() {
		return applicationProfileId;
	}

	public void setApplicationProfileId(String applicationProfileId) {
		this.applicationProfileId = applicationProfileId;
	}

	public String getMerchantProfileId() {
		return merchantProfileId;
	}

	public void setMerchantProfileId(String merchantProfileId) {
		this.merchantProfileId = merchantProfileId;
	}

	public Transaction getTransaction() {
		if (transaction == null) {
			transaction = new Transaction();
		}
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
