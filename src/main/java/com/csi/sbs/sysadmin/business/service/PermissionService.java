package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.clientmodel.PermissionModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface PermissionService {

	@SuppressWarnings("rawtypes")
	public ResultUtil validate(PermissionModel permissionModel);

}
