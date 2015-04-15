package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class holds the data for AVSResult.
 * 
 * @author Vimal Kumar
 * @date 16-January-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AVSResult", propOrder = {})
@XmlRootElement(name = "AVSResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
public class AVSResult {

	@XmlElement(name = "ActualResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String actualResult;

	@XmlElement(name = "CityResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String cityResult;

	@XmlElement(name = "AddressResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String addressResult;

	@XmlElement(name = "CountryResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String countryResult;

	@XmlElement(name = "StateResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String stateResult;

	@XmlElement(name = "PostalCodeResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String postalCodeResult;

	@XmlElement(name = "PhoneResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String phoneResult;

	@XmlElement(name = "CardholderNameResult", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected String cardholderNameResult;

	public String getAddressResult() {
		return addressResult;
	}

	public void setAddressResult(String addressResult) {
		this.addressResult = addressResult;
	}

	public String getCountryResult() {
		return countryResult;
	}

	public void setCountryResult(String countryResult) {
		this.countryResult = countryResult;
	}

	public String getStateResult() {
		return stateResult;
	}

	public void setStateResult(String stateResult) {
		this.stateResult = stateResult;
	}

	public String getPostalCodeResult() {
		return postalCodeResult;
	}

	public void setPostalCodeResult(String postalCodeResult) {
		this.postalCodeResult = postalCodeResult;
	}

	public String getPhoneResult() {
		return phoneResult;
	}

	public void setPhoneResult(String phoneResult) {
		this.phoneResult = phoneResult;
	}

	public String getCardholderNameResult() {
		return cardholderNameResult;
	}

	public void setCardholderNameResult(String cardholderNameResult) {
		this.cardholderNameResult = cardholderNameResult;
	}

	public String getActualResult() {
		return actualResult;
	}

	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}

	public String getCityResult() {
		return cityResult;
	}

	public void setCityResult(String cityResult) {
		this.cityResult = cityResult;
	}

}
