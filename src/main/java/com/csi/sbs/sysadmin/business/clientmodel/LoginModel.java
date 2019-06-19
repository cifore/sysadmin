package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class LoginModel {

	@NotNull(message="loginname is a required field")
	@NotBlank(message="loginname is a required field")
	@ApiModelProperty(notes=""
	,example="WongYan")
	private String loginname;

	@NotNull(message="loginpwd is a required field")
	@NotBlank(message="loginpwd is a required field")
	@ApiModelProperty(notes=""
	,example="123456")
	private String loginpwd;

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpwd() {
		return loginpwd;
	}

	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}

}
