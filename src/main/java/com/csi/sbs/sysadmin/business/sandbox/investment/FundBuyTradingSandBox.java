package com.csi.sbs.sysadmin.business.sandbox.investment;

import java.math.BigDecimal;

public class FundBuyTradingSandBox {

	private String fundaccountnumber;
	private String fundcode;
	private BigDecimal tradingamount;
	private String debitaccountnumber;

	public String getFundaccountnumber() {
		return fundaccountnumber;
	}

	public void setFundaccountnumber(String fundaccountnumber) {
		this.fundaccountnumber = fundaccountnumber;
	}

	public String getFundcode() {
		return fundcode;
	}

	public void setFundcode(String fundcode) {
		this.fundcode = fundcode;
	}

	

	public BigDecimal getTradingamount() {
		return tradingamount;
	}

	public void setTradingamount(BigDecimal tradingamount) {
		this.tradingamount = tradingamount;
	}

	public String getDebitaccountnumber() {
		return debitaccountnumber;
	}

	public void setDebitaccountnumber(String debitaccountnumber) {
		this.debitaccountnumber = debitaccountnumber;
	}

}
