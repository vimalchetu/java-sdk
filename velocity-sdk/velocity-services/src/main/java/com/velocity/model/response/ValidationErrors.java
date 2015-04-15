package com.velocity.model.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the attributes for accessing the Validation ErrorResponse
 * @author anitk
 * @date 04-February-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationErrors", propOrder = {
		
})
@XmlRootElement(name = "ValidationErrors", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
public class ValidationErrors {
	
	@XmlElement(name = "ValidationError", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected List<ValidationError> validationErrorList;

	public List<ValidationError> getValidationErrorList() {
		return validationErrorList;
	}

	public void setValidationErrorList(List<ValidationError> validationErrorList) {
		this.validationErrorList = validationErrorList;
	}
	
	
	
}
