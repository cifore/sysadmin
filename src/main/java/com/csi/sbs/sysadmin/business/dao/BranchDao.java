package com.csi.sbs.sysadmin.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.sysadmin.business.base.BaseDao;
import com.csi.sbs.sysadmin.business.entity.BranchEntity;

@Mapper
public interface BranchDao<T> extends BaseDao<T> {
	
	List<BranchEntity> findCountryCodes();
	
	List<BranchEntity> findClearingCodeByCountry(BranchEntity ase);
}
