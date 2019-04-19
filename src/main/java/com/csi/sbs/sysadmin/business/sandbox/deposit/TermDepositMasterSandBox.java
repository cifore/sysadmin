package com.csi.sbs.sysadmin.business.sandbox.deposit;



public class TermDepositMasterSandBox {

	private String tdAccountNumber;
	private String tdCcy;
	private String tdAmount;
	private String tdContractPeriod;
	private String debitAccountNumber;
	
	
	
	
	

	public String getTdAmount() {
		return tdAmount;
	}

	public void setTdAmount(String tdAmount) {
		this.tdAmount = tdAmount;
	}

	public String getTdAccountNumber() {
		return tdAccountNumber;
	}

	public void setTdAccountNumber(String tdAccountNumber) {
		this.tdAccountNumber = tdAccountNumber;
	}

	public String getTdCcy() {
		return tdCcy;
	}

	public void setTdCcy(String tdCcy) {
		this.tdCcy = tdCcy;
	}

	public String getTdContractPeriod() {
		return tdContractPeriod;
	}

	public void setTdContractPeriod(String tdContractPeriod) {
		this.tdContractPeriod = tdContractPeriod;
	}

	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}

	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}

}
