package com.csi.sbs.sysadmin.business.service;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserBranchModel;
import com.csi.sbs.sysadmin.business.clientmodel.DockerModel;
import com.csi.sbs.sysadmin.business.clientmodel.SandBoxModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface UserBranchService {

	@SuppressWarnings("rawtypes")
	public ResultUtil addUserBranch(AddUserBranchModel userbranch,RestTemplate restTemplate);

	@SuppressWarnings("rawtypes")
	public ResultUtil appSandBoxForDeveloper(SandBoxModel sbm,RestTemplate restTemplate) throws Exception;

	@SuppressWarnings("rawtypes")
	public ResultUtil appDockerForDeveloper(DockerModel dm,RestTemplate restTemplate) throws Exception;


}
