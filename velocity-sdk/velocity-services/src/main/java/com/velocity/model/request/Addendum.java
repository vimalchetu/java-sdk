package com.velocity.model.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * This class holds the data for Addendum of verify response XML.
 * 
 * @author Vimal Kumar 
 * @date 07-January-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Addendum", propOrder = {
})
@XmlRootElement(name = "Addendum")
public class Addendum {

    /* Processing the response xml for value */
    @XmlValue private String value;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
