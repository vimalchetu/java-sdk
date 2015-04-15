package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for TransactionDetail
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class TransactionDetail {

	@SerializedName("TransactionInformation")
	private TransactionInformation transactionInformation;

	@SerializedName("FamilyInformation")
	private FamilyInformation familyInformation;

	@SerializedName("CompleteTransaction")
	private CompleteTransaction completeTransaction;

	public CompleteTransaction getCompleteTransaction() {
		return completeTransaction;
	}

	public void setCompleteTransaction(CompleteTransaction completeTransaction) {
		this.completeTransaction = completeTransaction;
	}

	public TransactionInformation getTransactionInformation() {
		return transactionInformation;
	}

	public void setTransactionInformation(
			TransactionInformation transactionInformation) {
		this.transactionInformation = transactionInformation;
	}

	public FamilyInformation getFamilyInformation() {
		return familyInformation;
	}

	public void setFamilyInformation(FamilyInformation familyInformation) {
		this.familyInformation = familyInformation;
	}

}
