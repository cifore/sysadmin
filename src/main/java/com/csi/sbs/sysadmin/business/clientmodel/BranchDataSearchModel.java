package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BranchDataSearchModel {

	@NotNull(message="tablename is a required field")
	@NotBlank(message="tablename is a required field")
	@ApiModelProperty(notes=""
	,example="t_customer_master")
	private String tablename;
	
	@NotNull(message="branchcode is a required field")
	@NotBlank(message="branchcode is a required field")
	@ApiModelProperty(notes=""
	,example="001")
	private String branchcode;

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	
}
