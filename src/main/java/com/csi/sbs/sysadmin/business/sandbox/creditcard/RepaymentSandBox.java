package com.csi.sbs.sysadmin.business.sandbox.creditcard;

import java.math.BigDecimal;

public class RepaymentSandBox {

	private String creditcardnumber;
	private BigDecimal repaymentAmount;
	private String debitaccountnumber;

	public String getCreditcardnumber() {
		return creditcardnumber;
	}

	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}

	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public String getDebitaccountnumber() {
		return debitaccountnumber;
	}

	public void setDebitaccountnumber(String debitaccountnumber) {
		this.debitaccountnumber = debitaccountnumber;
	}

}
