package com.csi.sbs.sysadmin.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.clientmodel.PermissionModel;
import com.csi.sbs.sysadmin.business.dao.BranchDao;
import com.csi.sbs.sysadmin.business.dao.UserBranchDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;
import com.csi.sbs.sysadmin.business.entity.UserBranchEntity;
import com.csi.sbs.sysadmin.business.service.PermissionService;


@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private UserBranchDao userBranchDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDao branchDao;

	@SuppressWarnings("unchecked")
	@Override
	public boolean validate(PermissionModel permissionModel) {
		//根据UserID 去查询 userbranch 关系表
		UserBranchEntity userbranch = new UserBranchEntity();
		userbranch.setUserid(permissionModel.getUserID());
		List<UserBranchEntity> list = userBranchDao.findMany(userbranch);
		if(list==null){
			//无权限
			return false;
		}
		BranchEntity branch = new BranchEntity();
		branch.setCountrycode(permissionModel.getCountryCode());
		branch.setClearingcode(permissionModel.getClearingCode());
		branch.setBranchcode(permissionModel.getBranchCode());
		BranchEntity rebranch = (BranchEntity) branchDao.findOne(branch);
		if(rebranch==null){
			//无权限
			return false;
		}
		return true;
	}

}
