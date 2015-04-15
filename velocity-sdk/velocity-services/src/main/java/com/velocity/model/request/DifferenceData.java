package com.velocity.model.request;

import com.velocity.enums.VelocityEnums;

/**
 * This class defines the entities for DifferenceData for Capture method.
 * 
 * @author Vimal Kumar
 * @date 06-January-2015
 */
public class DifferenceData {

    private VelocityEnums type;
    private String transactionId;
    private String amount;
    private String tipAmount;
    /* Attribute for DifferenceData value exists or not. */
    private boolean nillable = true;
    private String value = "";

    public boolean isNillable() {
        return nillable;
    }
    public void setNillable(boolean nillable) {
        this.nillable = nillable;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public VelocityEnums getType() {
        return type;
    }
    public void setType(VelocityEnums type) {
        this.type = type;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getTipAmount() {
        return tipAmount;
    }
    public void setTipAmount(String tipAmount) {
        this.tipAmount = tipAmount;
    }
}
