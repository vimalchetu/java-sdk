package com.velocity.model.request;

/**
 * This model class defines the entities for CardData
 * 
 * @author Vimal Kumar
 * @date 09-January-2015
 */
public class CardData {

    private String cardType;
    private String pan;
    private String expire;
    private Track1Data track1Data;
    private String cardholderName;
    private Track2Data track2Data;
    private String track2Data2;
    private String track1Data1;
    private String isExpire;
    private String isPan;
    private Pan pan1;
    private Expire expire1;

    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public Track1Data getTrack1Data() {
        if(track1Data == null){
            track1Data = new Track1Data();
        }
        return track1Data;
    }
    /*
     * public Pan getPan() { if(pan == null){ pan = new Pan(); } return pan; } public void
     * setPan(Pan pan) { this.pan = pan; } public Expire getExpire() { if(expire == null){ expire =
     * new Expire(); } return expire; } public void setExpire(Expire expire) { this.expire = expire;
     * }
     */
    public void setTrack1Data(Track1Data track1Data) {
        this.track1Data = track1Data;
    }
    public String getCardholderName() {
        return cardholderName;
    }
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    /*
     * public String getPanNumber() { return panNumber; } public void setPanNumber(String panNumber)
     * { this.panNumber = panNumber; }
     */
    public Track2Data getTrack2Data() {
        if(track2Data == null){
            track2Data = new Track2Data();
        }
        return track2Data;
    }
    public void setTrack2Data(Track2Data track2Data) {
        this.track2Data =
                track2Data;
    }
    /*
     * public String getTrack2Data() { return track2Data; } public void setTrack2Data(String
     * track2Data) { this.track2Data = track2Data; }
     */
    public String getPan() {
        return pan;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }
    public String getExpire() {
        return expire;
    }
    public void setExpire(String expire) {
        this.expire = expire;
    }
    public String getTrack2Data2() {
        return track2Data2;
    }
    public void setTrack2Data2(String track2Data2) {
        this.track2Data2 = track2Data2;
    }
    public String getTrack1Data1() {
        return track1Data1;
    }
    public void setTrack1Data1(String track1Data1) {
        this.track1Data1 = track1Data1;
    }
    public String getIsExpire() {
        return isExpire;
    }
    public void setIsExpire(String isExpire) {
        this.isExpire = isExpire;
    }
    public String getIsPan() {
        return isPan;
    }
    public void setIsPan(String isPan) {
        this.isPan = isPan;
    }
    public Pan getPan1() {
        if(pan1 == null)
        {
            pan1 = new Pan();
        }
        return pan1;
    }
    public void setPan1(Pan pan1) {
        this.pan1 = pan1;
    }
    // public Track2Data getTrack2Data() {
    // if(track2Data == null){
    // track2Data = new Track2Data();
    // }
    // return track2Data;
    // }
    //
    // public void setTrack2Data(Track2Data track2Data) {
    // this.track2Data = track2Data;
    // }
    public Expire getExpire1() {
        if(expire1 == null){
            expire1 = new Expire();
        }
        return expire1;
    }
    public void setExpire1(Expire expire1) {
        this.expire1 = expire1;
    }
}
