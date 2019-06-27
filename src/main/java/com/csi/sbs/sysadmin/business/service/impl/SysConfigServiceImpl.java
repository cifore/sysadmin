package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.dao.SysConfigDao;
import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;
import com.csi.sbs.sysadmin.business.service.SysConfigService;


@Service("SysConfigService")
public class SysConfigServiceImpl implements SysConfigService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private SysConfigDao sysConfigDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<SysConfigEntity> querySysConfig(SysConfigEntity sce) {
		return sysConfigDao.querySysConfig(sce);
	}

	@Override
	public int updateNextAvailableCustomerNum(SysConfigEntity sce) {
		return sysConfigDao.updateNextAvailableCustomerNum(sce);
	}

	@Override
	public Map<String, Object> querySysConfList() {
		Map<String, Object> map = new HashMap<String, Object>();
		SysConfigEntity requestParam = new SysConfigEntity();
		
		@SuppressWarnings("unchecked")
		List<SysConfigEntity> list = sysConfigDao.findMany(requestParam);
				
		map.put("code", "1");
		map.put("msg", "query succeed");
		map.put("list", list);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertParam(SysConfigEntity sysConfigEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysConfigEntity.getItem() == null || sysConfigEntity.getItem().length() == 0){
			map.put("code", "0");
			map.put("msg", "Item can't be empty");
			return map;
		}
		if(sysConfigEntity.getValue() == null || sysConfigEntity.getValue().length() == 0){
			map.put("code", "0");
			map.put("msg", "Value can't be empty");
			return map;
		}
		
		SysConfigEntity params = (SysConfigEntity) sysConfigDao.findOne(sysConfigEntity);
		if(params != null){
			map.put("code", "0");
			map.put("msg", "The Item Has Existed");
			return map;
		}
		
		SysConfigEntity queryList =  new SysConfigEntity();
		List<SysConfigEntity> list = sysConfigDao.findMany(queryList);
		
		sysConfigEntity.setId(UUIDUtil.generateUUID());
		sysConfigEntity.setSort(list.size() + 1);
		sysConfigDao.insert(sysConfigEntity);
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> updateParam(SysConfigEntity sysConfigEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(sysConfigEntity.getId() == null || sysConfigEntity.getId().length() == 0){
			map.put("code", "0");
			map.put("msg", "ID can't be empty");
			return map;
		}
		
		SysConfigEntity paramRequest = new SysConfigEntity();
		paramRequest.setId(sysConfigEntity.getId());
		SysConfigEntity paramInfo = (SysConfigEntity) sysConfigDao.findOne(paramRequest);
		if(paramInfo == null){
			map.put("code", "0");
			map.put("msg", "The item is not found");
			return map;
		}
		
		if(sysConfigEntity.getItem() != null && paramInfo.getItem().equals(sysConfigEntity.getItem()) == false){
			map.put("code", "0");
			map.put("msg", "Item name can't be changed");
			return map;
		}
		
		if(paramInfo.getRemark() != null && paramInfo.getRemark().equals(sysConfigEntity.getRemark()) && paramInfo.getValue().equals(sysConfigEntity.getValue())){
			map.put("code", "0");
			map.put("msg", "No item info changes");
			return map;
		}
		
		sysConfigDao.update(sysConfigEntity);
		
		map.put("code", "1");
		map.put("msg", "update succeed");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findItem(String id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		SysConfigEntity itemRequest = new SysConfigEntity();
		itemRequest.setId(id);
		SysConfigEntity itemInfo = (SysConfigEntity) sysConfigDao.findOne(itemRequest);
		
		if(itemInfo == null){
			map.put("code", "0");
			map.put("msg", "Not found item");
			return map;
		}
		
		map.put("code", "1");
		map.put("msg", "query succeed");
		map.put("itemInfo", itemInfo);
		return map;
	}

}
