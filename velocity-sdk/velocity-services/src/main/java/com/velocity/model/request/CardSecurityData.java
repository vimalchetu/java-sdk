package com.velocity.model.request;

/**
 * This model class defines the entities for CardSecurityData.
 * 
 * @author anitk
 * @date 13-January-2015
 */
public class CardSecurityData {

    private AVSData avsData;
    private String cvDataProvided;
    private CVData cvData;
    private KeySerialNumber keySerialNumber;
    private Pin pin;
    private IdentificationInformation identificationInformation;

    public AVSData getAvsData() {
        if(avsData == null){
            avsData = new AVSData();
        }
        return avsData;
    }
    public void setAvsData(AVSData avsData) {
        this.avsData = avsData;
    }
    public CVData getCvData() {
        if(cvData == null){
            cvData = new CVData();
        }
        return cvData;
    }
    public void setCvData(CVData cvData) {
        this.cvData = cvData;
    }
    public KeySerialNumber getKeySerialNumber() {
        if(keySerialNumber == null){
            keySerialNumber = new KeySerialNumber();
        }
        return keySerialNumber;
    }
    public String getCvDataProvided() {
        return cvDataProvided;
    }
    public void setCvDataProvided(String cvDataProvided) {
        this.cvDataProvided = cvDataProvided;
    }
    public void setKeySerialNumber(KeySerialNumber keySerialNumber) {
        this.keySerialNumber = keySerialNumber;
    }
    public Pin getPin() {
        if(pin == null){
            pin = new Pin();
        }
        return pin;
    }
    public void setPin(Pin pin) {
        this.pin = pin;
    }
    public IdentificationInformation getIdentificationInformation() {
        if(identificationInformation == null){
            identificationInformation = new IdentificationInformation();
        }
        return identificationInformation;
    }
    public void setIdentificationInformation(
            IdentificationInformation identificationInformation) {
        this.identificationInformation = identificationInformation;
    }
}
