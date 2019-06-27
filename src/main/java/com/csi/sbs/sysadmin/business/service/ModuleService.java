package com.csi.sbs.sysadmin.business.service;

import java.util.Map;

import com.csi.sbs.sysadmin.business.clientmodel.IDModel;
import com.csi.sbs.sysadmin.business.entity.ModuleEntity;

public interface ModuleService {
	
	 ModuleEntity selectById(String id);
	 
	 public Map<String, Object> getAll();
	 
	 public Map<String, Object> insertModule(ModuleEntity ase) throws Exception;
	 
	 public Map<String, Object> updateModule(ModuleEntity ase) throws Exception;
	 
	 public Map<String, Object> deleteModule(IDModel idModel) throws Exception;
	 
	 public Map<String, Object> findModule(IDModel idModel) throws Exception;
}