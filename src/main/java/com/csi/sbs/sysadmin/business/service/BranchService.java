package com.csi.sbs.sysadmin.business.service;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.FindBranchCodeModel;
import com.csi.sbs.sysadmin.business.clientmodel.GetCountryCodeModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface BranchService {
	
	public Map<String, Object> getCountryCodes() throws Exception;
	
	public Map<String, Object> getClearingCodeByCountryCode(GetCountryCodeModel ase) throws Exception;
	
	public Map<String, Object> getBrancoByCC(FindBranchCodeModel ase) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ResultUtil addBranch(RestTemplate restTemplate);
}
