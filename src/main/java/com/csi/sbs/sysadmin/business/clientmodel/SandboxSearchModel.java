package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SandboxSearchModel{
	
	@NotNull(message="tablename is a required field")
	@NotBlank(message="tablename is a required field")
	@ApiModelProperty(notes=""
	,example="t_customer_master")
	private String tablename;
	
	@NotNull(message="sandboxid is a required field")
	@NotBlank(message="sandboxid is a required field")
	@ApiModelProperty(notes=""
	,example="111111")
	private String sandboxid;

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getSandboxid() {
		return sandboxid;
	}

	public void setSandboxid(String sandboxid) {
		this.sandboxid = sandboxid;
	}
}