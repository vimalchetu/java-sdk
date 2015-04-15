/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName; 
import com.velocity.enums.CountryCode;
import com.velocity.enums.StateProvince;

/**
 * This class defines the attributes for Address.
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class Address {

	@SerializedName("City")
	private String city;

	@SerializedName("PostalCode")
	private String postalCode;

	@SerializedName("Street1")
	private String Street1;

	@SerializedName("Street2")
	private String Street2;

	@SerializedName("StateProvince")
	private StateProvince stateProvince;

	@SerializedName("CountryCode")
	private CountryCode countryCode;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet1() {
		return Street1;
	}

	public void setStreet1(String street1) {
		Street1 = street1;
	}

	public String getStreet2() {
		return Street2;
	}

	public void setStreet2(String street2) {
		Street2 = street2;
	}

	public StateProvince getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(StateProvince stateProvince) {
		this.stateProvince = stateProvince;
	}

	public CountryCode getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(CountryCode countryCode) {
		this.countryCode = countryCode;
	}

}
