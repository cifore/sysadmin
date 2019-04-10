package com.csi.sbs.sysadmin.business.dao;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.sysadmin.business.base.BaseDao;
import com.csi.sbs.sysadmin.business.entity.UserBranchEntity;

@Mapper
public interface UserBranchDao<T> extends BaseDao<T> {

	public int appSandBoxForDeveloper(UserBranchEntity ube);
}
