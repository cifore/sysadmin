package com.csi.sbs.sysadmin.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.dao.CustomerTokenRelationDao;
import com.csi.sbs.sysadmin.business.entity.CustomerTokenRelationEntity;
import com.csi.sbs.sysadmin.business.service.CustomerTokenRelationService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;


@Service("CustomerTokenRelationService")
public class CustomerTokenRelationServiceImpl implements CustomerTokenRelationService{

	
	@SuppressWarnings("rawtypes")
	@Resource
	private CustomerTokenRelationDao customerTokenRelationDao;
	
	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil save(CustomerTokenRelationEntity cte) throws Exception {
		ResultUtil result = new ResultUtil();
		//校验是否已经授权
		CustomerTokenRelationEntity recte = (CustomerTokenRelationEntity) customerTokenRelationDao.findOne(cte);
		if(recte!=null){
			result.setCode("0");
			result.setMsg("The user has been authorized");
			return result;
		}
		cte.setId(UUIDUtil.generateUUID());
		cte.setCreatedate(format1.parse(format1.format(new Date())));
		customerTokenRelationDao.insert(cte);
		result.setCode("1");
		result.setMsg("Authorization successful");
		return result;
	}
}
