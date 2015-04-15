package com.velocity.model.request;

/**
 * This class defines the attributes for CaptureAll
 * 
 * @author Vimal Kumar
 * @date 13-04-2015
 */
public class CaptureAll {

    private String applicationProfileId;
    private BatchIds batchIds;
    private DifferenceData differenceData;
    private String merchantProfileId;

    public String getApplicationProfileId() {
        return applicationProfileId;
    }
    public void setApplicationProfileId(String applicationProfileId) {
        this.applicationProfileId = applicationProfileId;
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
    public String getMerchantProfileId() {
        return merchantProfileId;
    }
    public void setMerchantProfileId(String merchantProfileId) {
        this.merchantProfileId = merchantProfileId;
    }
}
