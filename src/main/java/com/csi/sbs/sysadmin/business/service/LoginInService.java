package com.csi.sbs.sysadmin.business.service;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.HeaderModel;
import com.csi.sbs.sysadmin.business.clientmodel.LoginModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface LoginInService {
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil loginIn(HeaderModel header,LoginModel loginModel,RestTemplate restTemplate) throws Exception;

}
