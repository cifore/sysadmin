package com.csi.sbs.sysadmin.business.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("pathConfig")
public class PathConfig {
	
	@Value("${gateway.url}")
	private String gateWayUrl;
	
	@Value("${customer.template}")
	private String customerTemplate;
	
	
	

	public String getCustomerTemplate() {
		return customerTemplate;
	}

	public void setCustomerTemplate(String customerTemplate) {
		this.customerTemplate = customerTemplate;
	}

	public String getGateWayUrl() {
		return gateWayUrl;
	}

	public void setGateWayUrl(String gateWayUrl) {
		this.gateWayUrl = gateWayUrl;
	}

}
