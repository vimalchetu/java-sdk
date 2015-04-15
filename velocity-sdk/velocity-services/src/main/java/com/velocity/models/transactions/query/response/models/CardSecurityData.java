package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the entities for CardSecurityData.
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class CardSecurityData {

	@SerializedName("AVSData")
	private String avsData;

	@SerializedName("CvDataProvided")
	private String cvDataProvided;

	@SerializedName("CvData")
	private String cvData;

	@SerializedName("KeySerialNumber")
	private KeySerialNumber keySerialNumber;

	@SerializedName("Pin")
	private Pin pin;

	@SerializedName("IdentificationInformation")
	private IdentificationInformation identificationInformation;

	public String getCvDataProvided() {
		return cvDataProvided;
	}

	public String getAvsData() {
		return avsData;
	}

	public void setAvsData(String avsData) {
		this.avsData = avsData;
	}

	public void setCvDataProvided(String cvDataProvided) {
		this.cvDataProvided = cvDataProvided;
	}

	public String getCvData() {
		return cvData;
	}

	public void setCvData(String cvData) {
		this.cvData = cvData;
	}

	public KeySerialNumber getKeySerialNumber() {

		if (keySerialNumber == null) {
			keySerialNumber = new KeySerialNumber();
		}

		return keySerialNumber;
	}

	public void setKeySerialNumber(KeySerialNumber keySerialNumber) {
		this.keySerialNumber = keySerialNumber;
	}

	public Pin getPin() {

		if (pin == null) {
			pin = new Pin();
		}
		return pin;
	}

	public void setPin(Pin pin) {
		this.pin = pin;
	}

	public IdentificationInformation getIdentificationInformation() {

		if (identificationInformation == null) {
			identificationInformation = new IdentificationInformation();
		}

		return identificationInformation;
	}

	public void setIdentificationInformation(
			IdentificationInformation identificationInformation) {
		this.identificationInformation = identificationInformation;
	}

}
