package com.csi.sbs.sysadmin.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.entity.CurrencyEntity;
import com.csi.sbs.sysadmin.business.dao.CurrencyDao;
import com.csi.sbs.sysadmin.business.service.CurrencyService;

@Service("CurrencyService")
public class CurrencyServiceImpl implements CurrencyService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private CurrencyDao currencyDao;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyEntity> queryAll() {
		// TODO Auto-generated method stub
		return currencyDao.queryAll();
	}


	@Override
	public CurrencyEntity queryByCcyCode(String ccycode) {	
		return currencyDao.queryByCcyCode(ccycode);
	}
}