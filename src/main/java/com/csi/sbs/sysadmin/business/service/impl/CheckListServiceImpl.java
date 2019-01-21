package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.clientmodel.ApiNameModel;
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

	@Override
	public CheckListEntity selectById(String id) {
		// TODO Auto-generated method stub
		return checkListDao.selectById(id);
	}

	@Override
	public Map<String, Object> getServiceInternalURL(ApiNameModel anm) {
		Map<String, Object> map = new HashMap<String, Object>();
		String apiName = anm.getApiname();
		String internaURL = "";
		try {
			internaURL = checkListDao.selectByName(apiName).getInternalurl();
		} catch (Exception e) {
			map.put("msg", "查询失败");
			map.put("code", "0");
			return map;
		}
		map.put("internaURL", internaURL);
		return map;
	}
	
}