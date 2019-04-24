package com.csi.sbs.sysadmin.business.sandbox.creditcard;

import java.math.BigDecimal;

public class PostTransDeInputSandBox {

	private String creditcardnumber;
	private String transactiontime;
	private String transactionccy;
	private BigDecimal transactionamount;
	private String merchantnumber;

	public String getCreditcardnumber() {
		return creditcardnumber;
	}

	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}

	public String getTransactiontime() {
		return transactiontime;
	}

	public void setTransactiontime(String transactiontime) {
		this.transactiontime = transactiontime;
	}

	public String getTransactionccy() {
		return transactionccy;
	}

	public void setTransactionccy(String transactionccy) {
		this.transactionccy = transactionccy;
	}

	public BigDecimal getTransactionamount() {
		return transactionamount;
	}

	public void setTransactionamount(BigDecimal transactionamount) {
		this.transactionamount = transactionamount;
	}

	public String getMerchantnumber() {
		return merchantnumber;
	}

	public void setMerchantnumber(String merchantnumber) {
		this.merchantnumber = merchantnumber;
	}

}
