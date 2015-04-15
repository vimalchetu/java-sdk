/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attribute for CWSTransaction.
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class CWSTransaction {

	@SerializedName("ApplicationData")
	private ApplicationData applicationData;

	@SerializedName("MerchantProfileMerchantData")
	private MerchantProfileMerchantData merchantProfileMerchantData;

	@SerializedName("MetaData")
	private MetaData metaData;

	@SerializedName("Response")
	private Response response;

	@SerializedName("Transaction")
	private Transaction transaction;

	public ApplicationData getApplicationData() {
		return applicationData;
	}

	public void setApplicationData(ApplicationData applicationData) {
		this.applicationData = applicationData;
	}

	public MerchantProfileMerchantData getMerchantProfileMerchantData() {
		return merchantProfileMerchantData;
	}

	public void setMerchantProfileMerchantData(
			MerchantProfileMerchantData merchantProfileMerchantData) {
		this.merchantProfileMerchantData = merchantProfileMerchantData;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
