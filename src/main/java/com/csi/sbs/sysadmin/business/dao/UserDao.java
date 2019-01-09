package com.csi.sbs.sysadmin.business.dao;

import com.csi.sbs.sysadmin.business.entity.UserEntity;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.sysadmin.business.base.BaseDao;

@Mapper
public interface UserDao<T> extends BaseDao<T> {

    UserEntity selectByUserID(String userid);

}