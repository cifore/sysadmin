package com.csi.sbs.sysadmin.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.sysadmin.business.base.BaseDao;
import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;


@Mapper
public interface SysConfigDao<T> extends BaseDao<T> {
	
	
	   public List<SysConfigEntity> querySysConfig();

}
