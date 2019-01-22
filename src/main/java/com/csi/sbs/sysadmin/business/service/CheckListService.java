package com.csi.sbs.sysadmin.business.service;

import java.util.List;
import java.util.Map;

import com.csi.sbs.sysadmin.business.clientmodel.ApiNameModel;
import com.csi.sbs.sysadmin.business.entity.CheckListEntity;

public interface CheckListService {
	
	public List<CheckListEntity> queryAll();
	
	CheckListEntity selectByName(String apiname);
	
	CheckListEntity selectById(String id);
	
	public Map<String, Object>getServiceInternalURL(ApiNameModel anm);
}