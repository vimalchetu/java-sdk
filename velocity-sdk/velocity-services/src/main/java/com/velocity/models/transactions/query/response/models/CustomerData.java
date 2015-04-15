package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the attributes for CustomerData.
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class CustomerData {

	@SerializedName("BillingData")
	private BillingData billingData;

	@SerializedName("CustomerId")
	private String customerId;

	@SerializedName("ShippingData")
	private ShippingData shippingData;

	@SerializedName("CustomerTaxId")
	private CustomerTaxId customerTaxId;

	public CustomerTaxId getCustomerTaxId() {
		if (customerTaxId == null) {
			customerTaxId = new CustomerTaxId();
		}
		return customerTaxId;
	}

	public void setCustomerTaxId(CustomerTaxId customerTaxId) {
		this.customerTaxId = customerTaxId;
	}

	public BillingData getBillingData() {
		if (billingData == null) {
			billingData = new BillingData();
		}
		return billingData;
	}

	public void setBillingData(BillingData billingData) {
		this.billingData = billingData;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public ShippingData getShippingData() {

		if (shippingData == null) {
			shippingData = new ShippingData();
		}
		return shippingData;
	}

	public void setShippingData(ShippingData shippingData) {
		this.shippingData = shippingData;
	}

}
