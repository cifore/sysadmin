package com.csi.sbs.sysadmin.business.service;

import java.util.List;

import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;

public interface SysConfigService {
	
	
	   public List<SysConfigEntity> querySysConfig(SysConfigEntity sce);
	   
	   public int updateNextAvailableCustomerNum(SysConfigEntity sce);

}
