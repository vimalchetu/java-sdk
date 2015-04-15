package com.velocity.model.request;

import com.velocity.model.request.Email;

/**
 * This model class defines the entities for AVSData
 * 
 * @author anitk
 * @date 13-January-2015
 */
public class AVSData {

	private CardholderName cardholderName;
	private String street;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String phone;
	private Email email;

	/* Attribute for AVSData value exists or not. */
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

	public Email getEmail() {
		if (email == null) {
			email = new Email();
		}
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public CardholderName getCardholderName() {
		if (cardholderName == null) {
			cardholderName = new CardholderName();
		}
		return cardholderName;
	}

	public void setCardholderName(CardholderName cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
