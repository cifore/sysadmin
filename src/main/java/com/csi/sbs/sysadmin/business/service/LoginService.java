package com.csi.sbs.sysadmin.business.service;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.entity.LoginModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface LoginService {
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil login(HeaderModel header,LoginModel loginModel,RestTemplate restTemplate) throws Exception;

}
