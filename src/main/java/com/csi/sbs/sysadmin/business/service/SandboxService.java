package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.clientmodel.SandBoxModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface SandboxService {
	
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil randomOne(SandBoxModel sandBoxModel) throws Exception;

}
