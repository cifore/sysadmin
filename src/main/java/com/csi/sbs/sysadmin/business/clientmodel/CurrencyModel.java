package com.csi.sbs.sysadmin.business.clientmodel;

import io.swagger.annotations.ApiModel;

@ApiModel
public class CurrencyModel {
	
	
	private String ccycode;

	public String getCcycode() {
		return ccycode;
	}

	public void setCcycode(String ccycode) {
		this.ccycode = ccycode == null ? null : ccycode.trim();
	}
}
