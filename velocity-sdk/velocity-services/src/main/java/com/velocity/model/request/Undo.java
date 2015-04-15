package com.velocity.model.request;

import com.velocity.enums.VelocityEnums;
import com.velocity.model.request.BatchIds;
import com.velocity.model.request.DifferenceData;

/**
 * This class defines the attributes for Undo method.
 * 
 * @author Vimal Kumar
 * @date 08-January-2015
 */
public class Undo {

    private VelocityEnums type;
    private String applicationProfileId;
    private String transactionId;
    private String merchantProfileId;
    private BatchIds batchIds;
    private DifferenceData differenceData;

    public VelocityEnums getType() {
        return type;
    }
    public void setType(VelocityEnums type) {
        this.type = type;
    }
    public String getApplicationProfileId() {
        return applicationProfileId;
    }
    public void setApplicationProfileId(String applicationProfileId) {
        this.applicationProfileId = applicationProfileId;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getMerchantProfileId() {
        return merchantProfileId;
    }
    public void setMerchantProfileId(String merchantProfileId) {
        this.merchantProfileId = merchantProfileId;
    }
    public BatchIds getBatchIds() {
        if(batchIds == null){
            batchIds = new BatchIds();
        }
        return batchIds;
    }
    public void setBatchIds(BatchIds batchIds) {
        this.batchIds = batchIds;
    }
    public DifferenceData getDifferenceData() {
        if(differenceData == null){
            differenceData = new DifferenceData();
        }
        return differenceData;
    }
    public void setDifferenceData(DifferenceData differenceData) {
        this.differenceData = differenceData;
    }
}
