package com.velocity.model.request.capture;

import com.velocity.model.request.DifferenceData;

/**
 * This class holds the data for Capture method.
 * 
 * @author Vimal Kumar
 * @date 06-January-2015
 */
public class ChangeTransaction {

    private String type;
    private String ApplicationProfileId;
    private DifferenceData differenceData;

    public DifferenceData getDifferenceData() {
        if(differenceData == null)
        {
            differenceData = new DifferenceData();
        }
        return differenceData;
    }
    public void setDifferenceData(DifferenceData differenceData) {
        this.differenceData = differenceData;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getApplicationProfileId() {
        return ApplicationProfileId;
    }
    public void setApplicationProfileId(String applicationProfileId) {
        ApplicationProfileId = applicationProfileId;
    }
}
