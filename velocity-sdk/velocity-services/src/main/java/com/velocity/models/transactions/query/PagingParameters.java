package com.velocity.models.transactions.query;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for PagingParameters
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class PagingParameters {

	@SerializedName("Page")
	private int page;

	@SerializedName("PageSize")
	private int pageSize;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
