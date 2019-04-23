package com.csi.sbs.sysadmin.business.sandbox.deposit;

public class TransactionSandBox {

	private String transferOutAccountNumber;
	private String transferInAccountNumber;
	private String transferAmount;

	public String getTransferOutAccountNumber() {
		return transferOutAccountNumber;
	}

	public void setTransferOutAccountNumber(String transferOutAccountNumber) {
		this.transferOutAccountNumber = transferOutAccountNumber;
	}

	public String getTransferInAccountNumber() {
		return transferInAccountNumber;
	}

	public void setTransferInAccountNumber(String transferInAccountNumber) {
		this.transferInAccountNumber = transferInAccountNumber;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

}
