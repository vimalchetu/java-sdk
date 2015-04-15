package com.velocity.model.request;

import com.velocity.model.request.Transaction;

/**
 * This model class defines the entities for AuthorizeAndCaptureTransaction
 * 
 * @author Vimal Kumar
 * @date 09-January-2015
 */
public class AuthorizeAndCaptureTransaction {

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

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Transaction getTransaction() {
		if (transaction == null) {
			transaction = new Transaction();
		}
		return transaction;
	}
}
