package com.velocity.model.request;

/**
 * This class defines the attributes for EBTVoucherApprovalCode
 * 
 * @author Vimal Kumar
 * @date 13-04-2015
 */
public class EBTVoucherApprovalCode {

    /* Attribute for EBTVoucherApprovalCode value exists or not. */
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
}
