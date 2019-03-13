package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class AddLoginUserModel {

	@NotNull(message="customerNumber is a required field")
	@NotBlank(message="customerNumber is a required field")
	@ApiModelProperty(notes=""
	,example="001032706")
	private String customerNumber;

	@NotNull(message="loginName is a required field")
	@NotBlank(message="loginName is a required field")
	@ApiModelProperty(notes=""
	,example="pim")
	private String loginName;

	@NotNull(message="loginPwd is a required field")
	@NotBlank(message="loginPwd is a required field")
	@ApiModelProperty(notes=""
	,example="123456")
	private String loginPwd;

	@NotNull(message="developerID is a required field")
	@NotBlank(message="developerID is a required field")
	@ApiModelProperty(notes=""
	,example="5de8b7acf522422c9ed2b740bb1c1775")
	private String developerID;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getDeveloperID() {
		return developerID;
	}

	public void setDeveloperID(String developerID) {
		this.developerID = developerID;
	}

}
