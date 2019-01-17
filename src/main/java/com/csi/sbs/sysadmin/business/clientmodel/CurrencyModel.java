package com.csi.sbs.sysadmin.business.clientmodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CurrencyModel {
	
	
	private String ccycode;

	public String getCcycode() {
		return ccycode;
	}

	public void setCcycode(String ccycode) {
		this.ccycode = ccycode;
	}
	   
	   
	   

}
