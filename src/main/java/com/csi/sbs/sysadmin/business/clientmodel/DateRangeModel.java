package com.csi.sbs.sysadmin.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DateRangeModel {
	
	@NotNull(message="transFromDate is a required field")
	@NotBlank(message="transFromDate is a required field")
	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The start date of the transactions."
   			,example="2019-03-12")
	private String fromDate;
	
	@NotNull(message="transToDate is a required field")
	@NotBlank(message="transToDate is a required field")
   	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The end date of the transactions."
			,example="2019-05-03")
	private String toDate;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
