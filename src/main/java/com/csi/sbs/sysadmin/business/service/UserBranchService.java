package com.csi.sbs.sysadmin.business.service;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserBranchModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface UserBranchService {

	@SuppressWarnings("rawtypes")
	public ResultUtil addUserBranch(AddUserBranchModel userbranch,RestTemplate restTemplate);

}
