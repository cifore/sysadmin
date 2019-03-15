package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.entity.TokenEntity;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface TokenService {
	
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil save(TokenEntity token) throws Exception;
	
	
	public void setTokenExpired();

}
