package com.csi.sbs.sysadmin.business.service;

import java.util.Map;

import com.csi.sbs.sysadmin.business.clientmodel.InsertCurrencyModel;
import com.csi.sbs.sysadmin.business.entity.CurrencyEntity;

public interface CurrencyService{
	
	Map<String,Object> queryByCcyCode(String ccycode) throws Exception;
	
	Map<String,Object> getCurrencys() throws Exception;
	
	Map<String,Object> insertCurrency(InsertCurrencyModel ase) throws Exception;
	
	Map<String,Object> updateCurrency(CurrencyEntity ase) throws Exception;
	
	Map<String,Object> deleteCurrency(String id) throws Exception;
}