/**
 * 
 */
package com.velocity.models.request.verify;

/**
 * This class holds the data for AuthorizeTransaction
 * 
 * @author vimalk2
 * @date 13-January-2015
 */
public class AuthorizeTransaction {

	private Transaction transaction;

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
