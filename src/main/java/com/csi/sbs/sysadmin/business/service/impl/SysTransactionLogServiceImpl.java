package com.csi.sbs.sysadmin.business.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csi.sbs.sysadmin.business.dao.SysTransactionLogDao;
import com.csi.sbs.sysadmin.business.entity.SysTransactionLogEntity;
import com.csi.sbs.sysadmin.business.service.SysTransactionLogService;



@Service("SysTransactionLogService")
public class SysTransactionLogServiceImpl  implements SysTransactionLogService{

	@Resource
	private SysTransactionLogDao stlDao;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void writeTransactionLog(SysTransactionLogEntity stl) {
		
		stlDao.insert(stl);
					
	}
		

}
