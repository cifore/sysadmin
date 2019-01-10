package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.entity.SysTransactionLogEntity;

public interface SysTransactionLogService {
	
	void writeTransactionLog(SysTransactionLogEntity stl);

}
