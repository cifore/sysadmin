package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.IDModel;
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

	@Override
	public Map<String, Object> getAll() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		ModuleEntity ase = new ModuleEntity();
		@SuppressWarnings("unchecked")
		List<ModuleEntity> moduleList = moduleDao.findMany(ase);
		
		map.put("code", "1");
		map.put("msg", "query success");
		map.put("list", moduleList);
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertModule(ModuleEntity ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ModuleEntity moduleInfo = (ModuleEntity) moduleDao.findOne(ase);
		if(moduleInfo != null){
			map.put("code", "0");
			map.put("msg", "The Module Name has Existed");
			return map;
		}
		
		ase.setId(UUIDUtil.generateUUID());
		int insertcode = moduleDao.insert(ase);
		
		if(insertcode <= 0){
			map.put("code", "0");
			map.put("msg", "insert failed");
			return map;
		}
		
		map.put("code", "1");
		map.put("msg", "insert success");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> updateModule(ModuleEntity ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ModuleEntity findModuleInfo = new ModuleEntity();
		findModuleInfo.setId(ase.getId());
		ModuleEntity moduleInfo = (ModuleEntity) moduleDao.findOne(findModuleInfo);
		
		if(moduleInfo == null){
			map.put("code", "0");
			map.put("msg", "Can't find module information");
			return map;
		}
		
		if(moduleInfo.getName().equals(ase.getName())){
			map.put("code", "0");
			map.put("msg", "No module information changes");
			return map;
		}
		
		ModuleEntity findModuleName =  new ModuleEntity();
		findModuleName.setName(ase.getName());
		ModuleEntity moduleNameInfo = (ModuleEntity) moduleDao.findOne(findModuleName);
		
		if(moduleNameInfo != null){
			map.put("code", "0");
			map.put("msg", "The Module Name has Existed");
			return map;
		}
		
		int updatecode = moduleDao.update(ase);
		
		if(updatecode <= 0){
			map.put("code", "0");
			map.put("msg", "update failed");
			return map;
		}
		
		map.put("code", "1");
		map.put("msg", "update success");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> deleteModule(IDModel idModel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModuleEntity moduleRequest = new ModuleEntity();
		moduleRequest.setId(idModel.getId());
		ModuleEntity moduleInfo = (ModuleEntity) moduleDao.findOne(moduleRequest);
		if(moduleInfo == null){
			map.put("code","0");
			map.put("msg", "Not Found Module");
			return map;
		}
		
		
		int deletecode = moduleDao.delete(idModel.getId());
		
		if(deletecode <= 0){
			map.put("code", "0");
			map.put("msg", "delete failed");
			return map;
		}
		
		map.put("code", "1");
		map.put("msg", "delete success");
		return map;
	}

	@Override
	public Map<String, Object> findModule(IDModel idModel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		ModuleEntity moduleRequest =  new ModuleEntity();
		moduleRequest.setId(idModel.getId());
		@SuppressWarnings("unchecked")
		ModuleEntity moduleInfo = (ModuleEntity) moduleDao.findOne(moduleRequest);
		
		if(moduleInfo == null){
			map.put("code", "0");
			map.put("msg", "Not Found Module");
			return map;
		}
		
		map.put("code", "1");
		map.put("msg", "find Success");
		map.put("moduleInfo", moduleInfo);
		return map;
	}
}