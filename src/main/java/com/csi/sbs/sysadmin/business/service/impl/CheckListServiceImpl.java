package com.csi.sbs.sysadmin.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.dao.CheckListDao;
import com.csi.sbs.sysadmin.business.entity.CheckListEntity;
import com.csi.sbs.sysadmin.business.service.CheckListService;

@Service("CheckListService")
public class CheckListServiceImpl implements CheckListService{

	@SuppressWarnings("rawtypes")
	@Resource
	private CheckListDao checkListDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckListEntity> queryAll() {
		
		return checkListDao.queryAll();
	}

	@Override
	public CheckListEntity selectByName(String apiname) {
		// TODO Auto-generated method stub
		return checkListDao.selectByName(apiname);
	}
	
}