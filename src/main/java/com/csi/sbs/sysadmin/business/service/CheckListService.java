package com.csi.sbs.sysadmin.business.service;

import java.util.List;

import com.csi.sbs.sysadmin.business.entity.CheckListEntity;

public interface CheckListService {
	
	public List<CheckListEntity> queryAll();
	
	CheckListEntity selectByName(String apiname);
	
	CheckListEntity selectById(String id);
}