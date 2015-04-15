/**
 * 
 */
package com.velocity.models.request.authorizeAndCapture;

/**
 * This model class defines the entities for AuthorizeAndCaptureTransaction
 * 
 * @author vimalk2
 * @date 09-January-2015
 */
public class AuthorizeAndCaptureTransaction {

	private String type;

	private String applicationProfileId;

	private String merchantProfileId;

	private Transcation transaction;

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

	public Transcation getTransaction() {
		if (transaction == null) {
			transaction = new Transcation();
		}

		return transaction;
	}

	public void setTransaction(Transcation transaction) {
		this.transaction = transaction;
	}

}
