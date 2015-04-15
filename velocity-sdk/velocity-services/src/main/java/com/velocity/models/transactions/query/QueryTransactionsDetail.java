package com.velocity.models.transactions.query;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.TransactionDetailFormat;

/**
 * This class defines the attributes for QueryTransactionsDetail
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class QueryTransactionsDetail {

	@SerializedName("PagingParameters")
	private PagingParameters pagingParameters;

	@SerializedName("QueryTransactionsParameters")
	private QueryTransactionsParameters queryTransactionsParameters;

	@SerializedName("TransactionDetailFormat")
	private TransactionDetailFormat transactionDetailFormat;

	@SerializedName("IncludeRelated")
	private boolean includeRelated;

	public PagingParameters getPagingParameters() {
		if (pagingParameters == null) {
			pagingParameters = new PagingParameters();
		}
		return pagingParameters;
	}

	public void setPagingParameters(PagingParameters pagingParameters) {
		this.pagingParameters = pagingParameters;
	}

	public QueryTransactionsParameters getQueryTransactionsParameters() {
		if (queryTransactionsParameters == null) {
			queryTransactionsParameters = new QueryTransactionsParameters();
		}
		return queryTransactionsParameters;
	}

	public void setQueryTransactionsParameters(
			QueryTransactionsParameters queryTransactionsParameters) {
		this.queryTransactionsParameters = queryTransactionsParameters;
	}

	public TransactionDetailFormat getTransactionDetailFormat() {
		return transactionDetailFormat;
	}

	public void setTransactionDetailFormat(
			TransactionDetailFormat transactionDetailFormat) {
		this.transactionDetailFormat = transactionDetailFormat;
	}

	public boolean isIncludeRelated() {
		return includeRelated;
	}

	public void setIncludeRelated(boolean includeRelated) {
		this.includeRelated = includeRelated;
	}

}
