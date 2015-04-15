package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for TenderData
 * 
 * @author Vimal Kumar
 * @date 12-March-2015
 */
public class TenderData {

	@SerializedName("PaymentAccountDataToken")
	private String paymentAccountDataToken;

	/*@SerializedName("PaymentAccountDataToken")
	private PaymentAccountDataToken paymentAccountDataWithoutToken;*/

	@SerializedName("SecurePaymentAccountData")
	private SecurePaymentAccountData securePaymentAccountData;

	@SerializedName("EncryptionKeyId")
	private EncryptionKeyId encryptionKeyId;

	@SerializedName("SwipeStatus")
	private SwipeStatus swipeStatus;

	@SerializedName("CardData")
	private CardData cardData;

	@SerializedName("CardSecurityData")
	private CardSecurityData cardSecurityData;

	@SerializedName("EcommerceSecurityData")
	private EcommerceSecurityData ecommerceSecurityData;

	public String getPaymentAccountDataToken() {
		return paymentAccountDataToken;
	}

	public void setPaymentAccountDataToken(String paymentAccountDataToken) {
		this.paymentAccountDataToken = paymentAccountDataToken;
	}

	public SecurePaymentAccountData getSecurePaymentAccountData() {

		if (securePaymentAccountData == null) {
			securePaymentAccountData = new SecurePaymentAccountData();
		}

		return securePaymentAccountData;
	}

	public void setSecurePaymentAccountData(
			SecurePaymentAccountData securePaymentAccountData) {
		this.securePaymentAccountData = securePaymentAccountData;
	}

	public EncryptionKeyId getEncryptionKeyId() {

		if (encryptionKeyId == null) {
			encryptionKeyId = new EncryptionKeyId();
		}

		return encryptionKeyId;
	}

	public void setEncryptionKeyId(EncryptionKeyId encryptionKeyId) {
		this.encryptionKeyId = encryptionKeyId;
	}

	public SwipeStatus getSwipeStatus() {

		if (swipeStatus == null) {
			swipeStatus = new SwipeStatus();
		}

		return swipeStatus;
	}

	public void setSwipeStatus(SwipeStatus swipeStatus) {

		this.swipeStatus = swipeStatus;
	}

	public CardData getCardData() {
		if (cardData == null) {
			cardData = new CardData();
		}
		return cardData;
	}

	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}

	public EcommerceSecurityData getEcommerceSecurityData() {

		if (ecommerceSecurityData == null) {
			ecommerceSecurityData = new EcommerceSecurityData();
		}

		return ecommerceSecurityData;
	}

	public void setEcommerceSecurityData(
			EcommerceSecurityData ecommerceSecurityData) {
		this.ecommerceSecurityData = ecommerceSecurityData;
	}

	/*public PaymentAccountDataToken getPaymentAccountDataWithoutToken() {
		if (paymentAccountDataWithoutToken == null) {
			paymentAccountDataWithoutToken = new PaymentAccountDataToken();
		}

		return paymentAccountDataWithoutToken;
	}

	public void setPaymentAccountDataWithoutToken(
			PaymentAccountDataToken paymentAccountDataWithoutToken) {
		this.paymentAccountDataWithoutToken = paymentAccountDataWithoutToken;
	}*/

	public CardSecurityData getCardSecurityData() {
		return cardSecurityData;
	}

	public void setCardSecurityData(CardSecurityData cardSecurityData) {
		this.cardSecurityData = cardSecurityData;
	}

}
