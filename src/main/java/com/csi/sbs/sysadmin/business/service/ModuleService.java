package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.entity.ModuleEntity;

public interface ModuleService {
	
	 ModuleEntity selectById(String id);
}