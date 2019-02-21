package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Input Params Model")
public class AddUserBranchModel {

	@NotNull(message="userid is a required field")
	@NotBlank(message="userid is a required field")
	private String userid;

	@NotNull(message="bankid is a required field")
	@NotBlank(message="bankid is a required field")
	private String bankid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

}
