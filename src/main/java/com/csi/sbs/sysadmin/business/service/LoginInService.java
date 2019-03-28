package com.csi.sbs.sysadmin.business.service;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.AddLoginUserModel;
import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.LoginModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface LoginInService {
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil loginIn(HeaderModel header,LoginModel loginModel,RestTemplate restTemplate) throws Exception;

	@SuppressWarnings("rawtypes")
	public ResultUtil authorize(String loginPK,RestTemplate restTemplate) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ResultUtil createLoginUser(AddLoginUserModel alm,RestTemplate restTemplate) throws Exception;
}
