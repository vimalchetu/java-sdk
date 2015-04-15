package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the attributes for accessing the ValidationErrorResponse
 * @author anitk
 * @date 04-February-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationError", propOrder = {
		
})
@XmlRootElement(name = "ValidationError", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
public class ValidationError {
	
	@XmlElement(name = "RuleKey", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String ruleKey;
	
	@XmlElement(name = "RuleLocationKey", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String ruleLocationKey;
	
	@XmlElement(name = "RuleMessage", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String ruleMessage;
	
	@XmlElement(name = "TransactionId", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Rest")
	protected String transactionId;

	public String getRuleKey() {
		return ruleKey;
	}

	public void setRuleKey(String ruleKey) {
		this.ruleKey = ruleKey;
	}

	public String getRuleLocationKey() {
		return ruleLocationKey;
	}

	public void setRuleLocationKey(String ruleLocationKey) {
		this.ruleLocationKey = ruleLocationKey;
	}

	public String getRuleMessage() {
		return ruleMessage;
	}

	public void setRuleMessage(String ruleMessage) {
		this.ruleMessage = ruleMessage;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	

}
