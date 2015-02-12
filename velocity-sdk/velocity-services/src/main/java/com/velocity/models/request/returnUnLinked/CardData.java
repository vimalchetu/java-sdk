/**
 * 
 */
package com.velocity.models.request.returnUnLinked;

/**
 * This model class defines the CardData entity.
 * 
 * @author vimalk2
 * @date 19-January-2015.
 */
public class CardData {

	private String cardType;

	private String pAN;

	private String expire;

	private Track1Data track1Data;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getpAN() {
		return pAN;
	}

	public void setpAN(String pAN) {
		this.pAN = pAN;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
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
