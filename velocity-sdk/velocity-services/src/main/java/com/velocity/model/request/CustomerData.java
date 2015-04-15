package com.velocity.model.request;

/**
 * This model class defines the entities for CustomerData
 * 
 * @author Vimal Kumar
 * @date 09-January-2015
 */
public class CustomerData {

    private BillingData billingData;
    private String customerId;
    private CustomerTaxId customerTaxId;
    private ShippingData shippingData;

    public CustomerTaxId getCustomerTaxId() {
        if(customerTaxId == null){
            customerTaxId = new CustomerTaxId();
        }
        return customerTaxId;
    }
    public void setCustomerTaxId(CustomerTaxId customerTaxId) {
        this.customerTaxId = customerTaxId;
    }
    public BillingData getBillingData() {
        if(billingData == null){
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
        if(shippingData == null){
            shippingData = new ShippingData();
        }
        return shippingData;
    }
    public void setShippingData(ShippingData shippingData) {
        this.shippingData = shippingData;
    }
}
