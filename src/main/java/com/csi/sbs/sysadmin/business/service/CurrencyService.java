package com.csi.sbs.sysadmin.business.service;

import java.util.List;

import com.csi.sbs.sysadmin.business.entity.CurrencyEntity;

public interface CurrencyService{
	
	public List<CurrencyEntity> queryAll();
	
	public boolean queryByCcyCode(String ccycode);
}