package com.csi.sbs.sysadmin.business.service;


import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.BranchDataSearchModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface BranchDataSearchService {
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil getTableBranchInfo(RestTemplate restTemplate,BranchDataSearchModel bdsm) throws Exception;


}
