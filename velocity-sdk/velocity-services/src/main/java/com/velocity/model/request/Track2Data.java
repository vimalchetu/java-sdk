/**
 * 
 */
package com.velocity.model.request;


/**
 * @author Vimal Kumar
 *
 * @date Apr 21, 2015
 */
public class Track2Data {
    
    /* Attribute for Track2Data value exists or not. */
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
