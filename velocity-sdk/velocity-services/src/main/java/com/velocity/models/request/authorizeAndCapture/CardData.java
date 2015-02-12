/**
 * 
 */
package com.velocity.models.request.authorizeAndCapture;

/**
 * This model class defines the entities for CardData
 * 
 * @author vimalk2
 * @date 09-January-2015
 */
public class CardData {

	private String cardType;

	private String pan;

	private String expiryDate;

	private Track1Data track1Data;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Track1Data getTrack1Data() {
		if (track1Data == null) {
			track1Data = new Track1Data();
		}

		return track1Data;
	}

	public void setTrack1Data(Track1Data track1Data) {
		this.track1Data = track1Data;
	}
}
