package com.csi.sbs.sysadmin.business.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.dao.ModuleDao;
import com.csi.sbs.sysadmin.business.entity.ModuleEntity;
import com.csi.sbs.sysadmin.business.service.ModuleService;

@Service("ModuleService")
public class ModuleServiceImpl implements ModuleService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private ModuleDao moduleDao;

	@Override
	public ModuleEntity selectById(String id) {
		
		return moduleDao.selectById(id);
	}
}