package com.csi.sbs.sysadmin.business.service;

import java.util.Map;

import com.csi.sbs.sysadmin.business.clientmodel.FindBranchCodeModel;
import com.csi.sbs.sysadmin.business.clientmodel.GetCountryCodeModel;

public interface BranchService {
	
	public Map<String, Object> getCountryCodes() throws Exception;
	
	public Map<String, Object> getClearingCodeByCountryCode(GetCountryCodeModel ase) throws Exception;
	
	public Map<String, Object> getBrancoByCC(FindBranchCodeModel ase) throws Exception;
	
}
