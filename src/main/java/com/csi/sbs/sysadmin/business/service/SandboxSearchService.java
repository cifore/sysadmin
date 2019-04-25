package com.csi.sbs.sysadmin.business.service;


import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.SandboxSearchModel;

public interface SandboxSearchService {
	
	public Map<String, Object> getTableSandboxInfo(RestTemplate restTemplate,SandboxSearchModel ase) throws Exception;

}
