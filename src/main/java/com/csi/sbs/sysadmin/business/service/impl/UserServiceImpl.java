package com.csi.sbs.sysadmin.business.service.impl;

import com.csi.sbs.sysadmin.business.dao.UserDao;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private UserDao userDao;

	@Override
	public UserEntity selectByUserID(String userid) {
		// TODO Auto-generated method stub
		return userDao.selectByUserID(userid);
	}
	
}