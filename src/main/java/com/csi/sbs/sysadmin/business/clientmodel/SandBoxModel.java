package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class SandBoxModel {

	@NotNull(message="developerId is a required field")
	@NotBlank(message="developerId is a required field")
	@ApiModelProperty(notes="",example="5ce51202ebd6453d71d86129")
	private String developerId;

	public String getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}

}
