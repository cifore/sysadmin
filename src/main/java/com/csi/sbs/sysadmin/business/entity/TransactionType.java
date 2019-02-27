package com.csi.sbs.sysadmin.business.entity;

public class TransactionType{
	
	private String id;

    private String trantype;
    
    private String trantypename;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public String getTrantypename() {
		return trantypename;
	}

	public void setTrantypename(String trantypename) {
		this.trantypename = trantypename;
	}

    
}