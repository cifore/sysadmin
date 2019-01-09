package com.csi.sbs.sysadmin.business.dao;

import com.csi.sbs.sysadmin.business.entity.CheckListEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.sysadmin.business.base.BaseDao;

@Mapper
public interface CheckListDao<T> extends BaseDao <T>{

    CheckListEntity selectById(String id);
    
    CheckListEntity selectByName(String apiname);
    
    public List<CheckListEntity> queryAll();

}