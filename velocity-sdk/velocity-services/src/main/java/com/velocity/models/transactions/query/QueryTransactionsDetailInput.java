package com.velocity.models.transactions.query;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for QueryTransactionsDetailInput
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class QueryTransactionsDetailInput {

	@SerializedName("QueryTransactionsDetail")
	private QueryTransactionsDetail queryTransactionsDetail;

	public QueryTransactionsDetail getQueryTransactionsDetail() {

		if (queryTransactionsDetail == null) {
			queryTransactionsDetail = new QueryTransactionsDetail();
		}
		return queryTransactionsDetail;
	}

	public void setQueryTransactionsDetail(
			QueryTransactionsDetail queryTransactionsDetail) {
		this.queryTransactionsDetail = queryTransactionsDetail;
	}

}
