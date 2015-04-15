/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.Language;

/**
 * This class defines the attributes for MerchantProfileMerchantData.
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class MerchantProfileMerchantData {

	@SerializedName("Address")
	private Address address;

	@SerializedName("BankcardMerchantData")
	private BankcardMerchantData bankcardMerchantData;

	@SerializedName("CustomerServiceInternet")
	private String customerServiceInternet;

	@SerializedName("CustomerServicePhone")
	private String customerServicePhone;

	@SerializedName("ElectronicCheckingMerchantData")
	private ElectronicCheckingMerchantData electronicCheckingMerchantData;

	@SerializedName("Language")
	private Language language;

	@SerializedName("MerchantId")
	private String merchantId;

	@SerializedName("Name")
	private String name;

	@SerializedName("Phone")
	private String phone;

	@SerializedName("TaxId")
	private String taxId;

	@SerializedName("StoredValueMerchantData")
	private StoredValueMerchantData storedValueMerchantData;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public BankcardMerchantData getBankcardMerchantData() {
		return bankcardMerchantData;
	}

	public void setBankcardMerchantData(
			BankcardMerchantData bankcardMerchantData) {
		this.bankcardMerchantData = bankcardMerchantData;
	}

	public String getCustomerServiceInternet() {
		return customerServiceInternet;
	}

	public void setCustomerServiceInternet(String customerServiceInternet) {
		this.customerServiceInternet = customerServiceInternet;
	}

	public String getCustomerServicePhone() {
		return customerServicePhone;
	}

	public void setCustomerServicePhone(String customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
	}

	public ElectronicCheckingMerchantData getElectronicCheckingMerchantData() {
		return electronicCheckingMerchantData;
	}

	public void setElectronicCheckingMerchantData(
			ElectronicCheckingMerchantData electronicCheckingMerchantData) {
		this.electronicCheckingMerchantData = electronicCheckingMerchantData;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public StoredValueMerchantData getStoredValueMerchantData() {
		return storedValueMerchantData;
	}

	public void setStoredValueMerchantData(
			StoredValueMerchantData storedValueMerchantData) {
		this.storedValueMerchantData = storedValueMerchantData;
	}

}
