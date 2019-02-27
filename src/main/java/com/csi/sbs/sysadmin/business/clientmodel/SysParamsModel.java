package com.csi.sbs.sysadmin.business.clientmodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SysParamsModel {
	
	@ApiModelProperty(notes="the information what user want to get"
			,example="ClearingCode,BranchNumber")
	private String item;
	

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	} 

}
