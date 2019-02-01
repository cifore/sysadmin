package com.csi.sbs.sysadmin.business.dao;

import com.csi.sbs.sysadmin.business.base.BaseDao;
import com.csi.sbs.sysadmin.business.entity.CurrencyEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurrencyDao<T> extends BaseDao <T>{

   public List<CurrencyEntity> queryAll();
   
   CurrencyEntity queryByCcyCode(String ccycode);
   
   CurrencyEntity queryById(String id);
}