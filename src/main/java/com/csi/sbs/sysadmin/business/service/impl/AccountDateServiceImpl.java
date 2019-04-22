package com.csi.sbs.sysadmin.business.service.impl;

import javax.annotation.Resource;

import com.csi.sbs.sysadmin.business.dao.UserBranchDao;
import com.csi.sbs.sysadmin.business.service.AccountDateService;

public class AccountDateServiceImpl implements AccountDateService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private UserBranchDao userBranchDao;

	@Override
	public void accountOldDateHandle() {
		userBranchDao.accountOldDateHandle();
	}

}
