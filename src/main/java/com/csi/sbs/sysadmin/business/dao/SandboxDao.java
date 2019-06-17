package com.csi.sbs.sysadmin.business.dao;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.sysadmin.business.base.BaseDao;
import com.csi.sbs.sysadmin.business.entity.SandboxEntity;

@Mapper
public interface SandboxDao<T> extends BaseDao<T> {
	
	
	public SandboxEntity randomOne(SandboxEntity sandboxEntity);
	
	public int markUsed(SandboxEntity sandboxEntity);    
}