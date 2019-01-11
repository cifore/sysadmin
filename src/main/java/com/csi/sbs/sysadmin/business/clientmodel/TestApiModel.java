package com.csi.sbs.sysadmin.business.clientmodel;

public class TestApiModel {
	
	private String apiaddress;
	private String inputDesc;
	private String requestmode;
	
	public String getRequestmode() {
		return requestmode;
	}
	public void setRequestmode(String requestmode) {
		this.requestmode = requestmode;
	}
	public String getApiaddress() {
		return apiaddress;
	}
	public void setApiaddress(String apiaddress) {
		this.apiaddress = apiaddress;
	}
	public String getInputDesc() {
		return inputDesc;
	}
	public void setInputDesc(String inputDesc) {
		this.inputDesc = inputDesc;
	}
}