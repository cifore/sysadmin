package com.csi.sbs.sysadmin.business.sandbox.foreignexchange;

public class ExchangeSandBox {

	private String fexAccountNumber;
	private String actionCode;
	private String ccycode;
	private String exchangeAmount;
	private String debitaccountnumber;

	public String getFexAccountNumber() {
		return fexAccountNumber;
	}

	public void setFexAccountNumber(String fexAccountNumber) {
		this.fexAccountNumber = fexAccountNumber;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getCcycode() {
		return ccycode;
	}

	public void setCcycode(String ccycode) {
		this.ccycode = ccycode;
	}

	public String getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(String exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public String getDebitaccountnumber() {
		return debitaccountnumber;
	}

	public void setDebitaccountnumber(String debitaccountnumber) {
		this.debitaccountnumber = debitaccountnumber;
	}

}
