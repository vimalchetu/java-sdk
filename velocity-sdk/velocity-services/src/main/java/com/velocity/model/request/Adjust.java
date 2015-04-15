package com.velocity.model.request;

import com.velocity.enums.VelocityEnums;
import com.velocity.model.request.BatchIds;
import com.velocity.model.request.DifferenceData;

/**
 * This class holds entities for Adjust
 * 
 * @author Vimal Kumar
 * @date 13-January-2015
 */
public class Adjust {

	private VelocityEnums type;
	private String applicationProfileId;
	private BatchIds batchIds;
	private String merchantProfileId;
	private DifferenceData differenceData;

	public String getApplicationProfileId() {
		return applicationProfileId;
	}

	public void setApplicationProfileId(String applicationProfileId) {
		this.applicationProfileId = applicationProfileId;
	}

	public BatchIds getBatchIds() {
		if (batchIds == null) {
			batchIds = new BatchIds();
		}
		return batchIds;
	}

	public void setBatchIds(BatchIds batchIds) {
		this.batchIds = batchIds;
	}

	public String getMerchantProfileId() {
		return merchantProfileId;
	}

	public void setMerchantProfileId(String merchantProfileId) {
		this.merchantProfileId = merchantProfileId;
	}

	public DifferenceData getDifferenceData() {
		if (differenceData == null) {
			differenceData = new DifferenceData();
		}
		return differenceData;
	}

	public void setDifferenceData(DifferenceData differenceData) {
		this.differenceData = differenceData;
	}

	public VelocityEnums getType() {
		return type;
	}

	public void setType(VelocityEnums type) {
		this.type = type;
	}
}
