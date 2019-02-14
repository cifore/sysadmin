package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.clientmodel.AddUserModel;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface UserService{
	
	UserEntity selectByUserID(String userid);
	
	@SuppressWarnings("rawtypes")
	ResultUtil addUser(AddUserModel addUserModel);
}