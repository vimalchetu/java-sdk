/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import java.util.List;

/**
 * This class defines the attribute for QueryTransactionResponse.
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class QueryTransactionResponse {

	private List<TransactionInformation> transactionInformationList;

	public List<TransactionInformation> getTransactionInformationList() {
		return transactionInformationList;
	}

	public void setTransactionInformationList(
			List<TransactionInformation> transactionInformationList) {
		this.transactionInformationList = transactionInformationList;
	}

}
