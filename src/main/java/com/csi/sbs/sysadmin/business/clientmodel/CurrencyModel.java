package com.csi.sbs.sysadmin.business.clientmodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CurrencyModel {
	
	
	@ApiModelProperty(value="this is ccy code")
	private String ccycode;

	public String getCcycode() {
		return ccycode;
	}

	public void setCcycode(String ccycode) {
		this.ccycode = ccycode;
	}
	   
	   
	   

}
