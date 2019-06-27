package com.csi.sbs.sysadmin.business.service;

import java.util.List;
import java.util.Map;

import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;

public interface SysConfigService {
	
	
	   public List<SysConfigEntity> querySysConfig(SysConfigEntity sce);
	   
	   public int updateNextAvailableCustomerNum(SysConfigEntity sce);
	   
	   public Map<String, Object> querySysConfList();
	   
	   public Map<String, Object> insertParam(SysConfigEntity sysConfigEntity);
	   
	   public Map<String, Object> updateParam(SysConfigEntity sysConfigEntity);
	   
	   public Map<String, Object> findItem(String id) throws Exception;

}
