package com.csi.sbs.sysadmin.business.clientmodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ApiNameModel {
	
	
	@ApiModelProperty(value="this is apiname")
	private String apiname;

	public String getApiname() {
		return apiname;
	}

	public void setApiname(String apiname) {
		this.apiname = apiname;
	}
	   
	   
	   

}
