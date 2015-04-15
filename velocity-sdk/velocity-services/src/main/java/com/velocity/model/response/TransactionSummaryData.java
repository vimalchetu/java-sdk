package com.velocity.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class defines the attributes for accessing the TransactionSummaryData
 * 
 * @author Vimal Kumar
 * @date 14-04-2015
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionSummaryData", propOrder = {})
@XmlRootElement(name = "TransactionSummaryData", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
public class TransactionSummaryData {

	@XmlElement(name = "CashBackTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected CashBackTotals cashBackTotals;

	@XmlElement(name = "NetTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected NetTotals netTotals;

	@XmlElement(name = "ReturnTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected ReturnTotals returnTotals;

	@XmlElement(name = "SaleTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected SaleTotals saleTotals;

	@XmlElement(name = "VoidTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected VoidTotals voidTotals;

	@XmlElement(name = "PINDebitReturnTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected PINDebitReturnTotals pinDebitReturnTotals;

	@XmlElement(name = "PINDebitSaleTotals", namespace = "http://schemas.ipcommerce.com/CWS/v2.0/Transactions/Bankcard")
	protected PINDebitSaleTotals pinDebitSaleTotals;

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
