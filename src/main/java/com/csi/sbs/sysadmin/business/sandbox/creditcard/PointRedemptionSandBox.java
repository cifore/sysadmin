package com.csi.sbs.sysadmin.business.sandbox.creditcard;

import java.math.BigDecimal;

public class PointRedemptionSandBox {

	private String creditcardnumber;
	private String productcode;
	private BigDecimal amount;

	public String getCreditcardnumber() {
		return creditcardnumber;
	}

	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
