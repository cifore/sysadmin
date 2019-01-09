package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.entity.UserEntity;

public interface UserService{
	UserEntity selectByUserID(String userid);
}