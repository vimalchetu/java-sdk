package com.velocity.model.request.capture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class holds the data for TransactionSummaryData for capture method
 * response.
 * 
 * @author Vimal Kumar
 * @date 07-January-2015
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionSummaryData", propOrder = {})
@XmlRootElement(name = "TransactionSummaryData")
public class TransactionSummaryData {

	@XmlElement(name = "CashBackTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private CashBackTotals cashBackTotals;

	@XmlElement(name = "NetTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private NetTotals netTotals;

	@XmlElement(name = "ReturnTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private ReturnTotals returnTotals;

	@XmlElement(name = "SaleTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private SaleTotals saleTotals;

	@XmlElement(name = "VoidTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private VoidTotals voidTotals;

	@XmlElement(name = "PINDebitReturnTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private PINDebitReturnTotals pinDebitReturnTotals;

	@XmlElement(name = "PINDebitSaleTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	private PINDebitSaleTotals pinDebitSaleTotals;

	public CashBackTotals getCashBackTotals() {
		return cashBackTotals;
	}

	public void setCashBackTotals(CashBackTotals cashBackTotals) {
		this.cashBackTotals = cashBackTotals;
	}

	public NetTotals getNetTotals() {
		return netTotals;
	}

	public void setNetTotals(NetTotals netTotals) {
		this.netTotals = netTotals;
	}

	public ReturnTotals getReturnTotals() {
		return returnTotals;
	}

	public void setReturnTotals(ReturnTotals returnTotals) {
		this.returnTotals = returnTotals;
	}

	public SaleTotals getSaleTotals() {
		return saleTotals;
	}

	public void setSaleTotals(SaleTotals saleTotals) {
		this.saleTotals = saleTotals;
	}

	public VoidTotals getVoidTotals() {
		return voidTotals;
	}

	public void setVoidTotals(VoidTotals voidTotals) {
		this.voidTotals = voidTotals;
	}

	public PINDebitReturnTotals getPinDebitReturnTotals() {
		return pinDebitReturnTotals;
	}

	public void setPinDebitReturnTotals(
			PINDebitReturnTotals pinDebitReturnTotals) {
		this.pinDebitReturnTotals = pinDebitReturnTotals;
	}

	public PINDebitSaleTotals getPinDebitSaleTotals() {
		return pinDebitSaleTotals;
	}

	public void setPinDebitSaleTotals(PINDebitSaleTotals pinDebitSaleTotals) {
		this.pinDebitSaleTotals = pinDebitSaleTotals;
	}
}
