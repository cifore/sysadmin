package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.entity.CurrencyEntity;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.InsertCurrencyModel;
import com.csi.sbs.sysadmin.business.dao.CurrencyDao;
import com.csi.sbs.sysadmin.business.service.CurrencyService;

//Alina currency 相关方法
@Service("CurrencyService")
public class CurrencyServiceImpl implements CurrencyService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private CurrencyDao currencyDao;

	@Override
	public Map<String, Object> queryByCcyCode(String ccycode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		CurrencyEntity currencyInfo = currencyDao.queryByCcyCode(ccycode);
		if(currencyInfo == null){
			map.put("msg", "Currency Not Supported");
            map.put("code", "0");
		}else{
			map.put("msg", "Check Success");
            map.put("code", "1");
            map.put("ccyInfo", currencyInfo);
		}
		return map;
	}

	//Alina 获取所有currencys
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCurrencys() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CurrencyEntity> list = null;		
		 
		try{
			 list = currencyDao.queryAll();
         }catch(Exception e){
      	   	 map.put("msg", "查询失败");
             map.put("code", "0");
             return map;
         }
		map.put("msg", "查询成功");
		map.put("code", "1");
		map.put("list", list);
		return map;
	}

	//Alina 插入currency
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertCurrency(InsertCurrencyModel ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		CurrencyEntity currencyInfo = currencyDao.queryByCcyCode(ase.getCcycode());
		if(currencyInfo != null){
			map.put("msg", "The Ccycode Has Existed");
			map.put("code", "0");
			return map;
		}
		CurrencyEntity newCurrency = new CurrencyEntity();
		newCurrency.setId(UUIDUtil.generateUUID());
		newCurrency.setCurrency(ase.getCurrency());
		newCurrency.setCcycode(ase.getCcycode());
		newCurrency.setCcyplaces(ase.getCcyplaces());
		newCurrency.setBankbuy(ase.getBankbuy());
		newCurrency.setBanksell(ase.getBanksell());
		if(currencyDao.insert(newCurrency) > 0){
			map.put("msg", "Add Currency Success");
			map.put("code", "1");
		}else{
			map.put("msg", "Add Currency Failed");
			map.put("code", "0");
		}
		return map;
	}
	
	//Alina 更新currency
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> updateCurrency(CurrencyEntity ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		CurrencyEntity checkCurrency  = currencyDao.queryById(ase.getId());
		if(checkCurrency.getCcycode().equals(ase.getCcycode()) == false){
			map.put("msg", "Can't Change Ccy Code");
			map.put("code", "0");
			return map;
		}
		if(currencyDao.update(ase) > 0){
			map.put("msg", "Update Success");
			map.put("code", "1");
		}else{
			map.put("msg", "Update Failed");
			map.put("code", "0");
		}
		return map;
	}
	
	//Alina 删除currency
	@Override
	public Map<String, Object> deleteCurrency(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(currencyDao.delete(id) > 0){
			map.put("msg", "Delete Success");
			map.put("code", "1");
		}else{
			map.put("msg", "Delete Failed");
			map.put("code", "0");
		}
		return map;
	}
}