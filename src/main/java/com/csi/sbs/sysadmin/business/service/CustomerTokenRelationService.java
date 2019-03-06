package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.entity.CustomerTokenRelationEntity;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface CustomerTokenRelationService {
	
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil save(CustomerTokenRelationEntity cte) throws Exception;

}
