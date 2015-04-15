package com.velocity.model.request;

/**
 * This class defines the attributes for Name
 * 
 * @author Vimal Kumar
 * @date 09-January-2015
 */
public class Name {

    /* Attribute for Name value exists or not. */
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
