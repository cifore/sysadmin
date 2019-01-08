package com.csi.sbs.sysadmin.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.dao.SysConfigDao;
import com.csi.sbs.sysadmin.business.entity.SysConfigEntity;
import com.csi.sbs.sysadmin.business.service.SysConfigService;


@Service("SysConfigService")
public class SysConfigServiceImpl implements SysConfigService{
	
	
	@SuppressWarnings("rawtypes")
	@Resource
	private SysConfigDao sysConfigDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<SysConfigEntity> querySysConfig(String...args) {
		return sysConfigDao.querySysConfig();
	}

}
