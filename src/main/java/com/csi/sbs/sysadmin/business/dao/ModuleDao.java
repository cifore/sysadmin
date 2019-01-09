package com.csi.sbs.sysadmin.business.dao;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.sysadmin.business.base.BaseDao;
import com.csi.sbs.sysadmin.business.entity.ModuleEntity;

@Mapper
public interface ModuleDao<T> extends BaseDao<T>{

    ModuleEntity selectById(String id);
}