package com.csi.sbs.sysadmin.business.sandbox.creditcard;

import java.math.BigDecimal;


public class CreditCardOpenSandBox {

	private String creditcardtype;

	private String issuancedate;

	private String expirydate;

	private String verificationcode;

	private BigDecimal approvedlimit;

	private BigDecimal cashadvancelimit;

	private String repaymentcycle;

	private String repaymentaccountnum;

	public String getCreditcardtype() {
		return creditcardtype;
	}

	public void setCreditcardtype(String creditcardtype) {
		this.creditcardtype = creditcardtype;
	}

	public String getIssuancedate() {
		return issuancedate;
	}

	public void setIssuancedate(String issuancedate) {
		this.issuancedate = issuancedate;
	}

	public String getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}

	public String getVerificationcode() {
		return verificationcode;
	}

	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}

	public BigDecimal getApprovedlimit() {
		return approvedlimit;
	}

	public void setApprovedlimit(BigDecimal approvedlimit) {
		this.approvedlimit = approvedlimit;
	}

	public BigDecimal getCashadvancelimit() {
		return cashadvancelimit;
	}

	public void setCashadvancelimit(BigDecimal cashadvancelimit) {
		this.cashadvancelimit = cashadvancelimit;
	}

	public String getRepaymentcycle() {
		return repaymentcycle;
	}

	public void setRepaymentcycle(String repaymentcycle) {
		this.repaymentcycle = repaymentcycle;
	}

	public String getRepaymentaccountnum() {
		return repaymentaccountnum;
	}

	public void setRepaymentaccountnum(String repaymentaccountnum) {
		this.repaymentaccountnum = repaymentaccountnum;
	}

}
