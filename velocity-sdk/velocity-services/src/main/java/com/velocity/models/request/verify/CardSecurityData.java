/**
 * 
 */
package com.velocity.models.request.verify;

/**
 * This model class defines the entities for CardSecurityData.
 * @author anitk
 * @date 13-January-2015
 */
public class CardSecurityData {

	private AVSData avsData;

	private String cvDataProvided;

	private String cvData;

	private KeySerialNumber keySerialNumber;

	private Pin pin;

	private IdentificationInformation identificationInformation;

	public AVSData getAvsData() {
		if (avsData == null) {
			avsData = new AVSData();
		}

		return avsData;
	}

	public void setAvsData(AVSData avsData) {
		this.avsData = avsData;
	}

	public String getCvDataProvided() {
		return cvDataProvided;
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
