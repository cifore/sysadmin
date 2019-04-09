package com.csi.sbs.sysadmin.business.service;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserModel;
import com.csi.sbs.sysadmin.business.clientmodel.PermissionModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface PermissionService {

	@SuppressWarnings("rawtypes")
	public ResultUtil validate(PermissionModel permissionModel);
	
	@SuppressWarnings("rawtypes")
	public ResultUtil userAuthorize(RestTemplate restTemplate,AddUserModel addUserModel) throws Exception;

}
