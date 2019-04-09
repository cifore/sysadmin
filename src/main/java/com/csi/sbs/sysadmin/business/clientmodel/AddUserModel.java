package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Input Params Model")
public class AddUserModel {

	@NotNull(message="developerID is a required field")
	@NotBlank(message="developerID is a required field")
	@ApiModelProperty(notes="the developer id"
	,example="111a")
	private String developerID;
	
	private String sandboxid;

	@ApiModelProperty(notes="the name of developer"
			,example="张三")
	private String developername;

	@Email(message="Incorrect mailbox format")
	@ApiModelProperty(notes="the email of the user"
	,example="example@chinasofti.com")
	private String email;
	
	
	

	public String getSandboxid() {
		return sandboxid;
	}

	public void setSandboxid(String sandboxid) {
		this.sandboxid = sandboxid;
	}

	public String getDeveloperID() {
		return developerID;
	}

	public void setDeveloperID(String developerID) {
		this.developerID = developerID;
	}

	public String getDevelopername() {
		return developername;
	}

	public void setDevelopername(String developername) {
		this.developername = developername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
