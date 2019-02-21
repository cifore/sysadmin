package com.csi.sbs.sysadmin.business.service.impl;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.AddUserModel;
import com.csi.sbs.sysadmin.business.clientmodel.ReUserModel;
import com.csi.sbs.sysadmin.business.dao.UserDao;
import com.csi.sbs.sysadmin.business.entity.UserEntity;
import com.csi.sbs.sysadmin.business.service.UserService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil addUser(AddUserModel addUserModel) {
		ResultUtil result = new ResultUtil();
		//check user
		UserEntity user = new UserEntity();
		user.setUserid(addUserModel.getUserid());
		UserEntity reuser = (UserEntity) userDao.findOne(user);
		if(reuser!=null){
			result.setCode("0");
			result.setMsg("User already exists");
			return result;
		}
		user.setEmail(addUserModel.getEmail());
		user.setId(UUIDUtil.generateUUID());
		user.setUsername(addUserModel.getUsername());
		userDao.insert(user);
		//返回信息
		ReUserModel reum = new ReUserModel();
		reum.setUserid(addUserModel.getUserid());
		reum.setUsername(addUserModel.getUsername());
		reum.setEmail(addUserModel.getEmail());
		result.setCode("1");
		result.setMsg("add User Success");
		result.setData(reum);
		return result;
	}
	
}