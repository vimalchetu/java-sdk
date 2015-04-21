/**
 * 
 */
package com.velocity.model.request;


/**
 * @author Vimal Kumar
 *
 * @date Apr 20, 2015
 */
public class Pan {
    
    /* Attribute for PAN value exists or not. */
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
