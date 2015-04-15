/**
 * 
 */
package com.velocity.models.transactions.query.response.models;

import com.google.gson.annotations.SerializedName;
import com.velocity.enums.ApplicationLocation;
import com.velocity.enums.EncryptionType;
import com.velocity.enums.HardwareType;
import com.velocity.enums.PINCapability;
import com.velocity.enums.ReadCapability;

/**
 * This class defines the attributes for ApplicationData.
 * 
 * @author Vimal Kumar
 * @date 11-March-2015
 */
public class ApplicationData {

	@SerializedName("ApplicationAttended")
	private boolean applicationAttended;

	@SerializedName("ApplicationLocation")
	private ApplicationLocation applicationLocation;

	@SerializedName("DeveloperId")
	private String developerId;

	@SerializedName("DeviceSerialNumber")
	private String deviceSerialNumber;

	@SerializedName("EncryptionType")
	private EncryptionType encryptionType;

	@SerializedName("HardwareType")
	private HardwareType hardwareType;

	@SerializedName("PINCapability")
	private PINCapability pINCapability;

	@SerializedName("PTLSSocketId")
	private String pTLSSocketId;

	@SerializedName("ReadCapability")
	private ReadCapability readCapability;

	@SerializedName("SerialNumber")
	private String serialNumber;

	@SerializedName("SoftwareVersion")
	private String softwareVersion;

	@SerializedName("SoftwareVersionDate")
	private String softwareVersionDate;

	@SerializedName("VendorId")
	private String vendorId;

	public boolean isApplicationAttended() {
		return applicationAttended;
	}

	public void setApplicationAttended(boolean applicationAttended) {
		this.applicationAttended = applicationAttended;
	}

	public ApplicationLocation getApplicationLocation() {
		return applicationLocation;
	}

	public void setApplicationLocation(ApplicationLocation applicationLocation) {
		this.applicationLocation = applicationLocation;
	}

	public String getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}

	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public EncryptionType getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(EncryptionType encryptionType) {
		this.encryptionType = encryptionType;
	}

	public HardwareType getHardwareType() {
		return hardwareType;
	}

	public void setHardwareType(HardwareType hardwareType) {
		this.hardwareType = hardwareType;
	}

	public PINCapability getpINCapability() {
		return pINCapability;
	}

	public void setpINCapability(PINCapability pINCapability) {
		this.pINCapability = pINCapability;
	}

	public String getpTLSSocketId() {
		return pTLSSocketId;
	}

	public void setpTLSSocketId(String pTLSSocketId) {
		this.pTLSSocketId = pTLSSocketId;
	}

	public ReadCapability getReadCapability() {
		return readCapability;
	}

	public void setReadCapability(ReadCapability readCapability) {
		this.readCapability = readCapability;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getSoftwareVersionDate() {
		return softwareVersionDate;
	}

	public void setSoftwareVersionDate(String softwareVersionDate) {
		this.softwareVersionDate = softwareVersionDate;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

}
