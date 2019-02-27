package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Input Params Model")
public class AddUserModel {

	@NotNull(message="userid is a required field")
	@NotBlank(message="userid is a required field")
	@ApiModelProperty(notes="the developer id"
	,example="111")
	private String userid;

	@ApiModelProperty(notes="the name of user"
			,example="张三")
	private String username;

	@Email(message="Incorrect mailbox format")
	@ApiModelProperty(notes="the email of the user"
	,example="example@chinasofti.com")
	private String email;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
