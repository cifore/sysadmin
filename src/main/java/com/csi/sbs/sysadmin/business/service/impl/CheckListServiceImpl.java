package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.sysadmin.business.clientmodel.ApiNameModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.dao.CheckListDao;
import com.csi.sbs.sysadmin.business.dao.ModuleDao;
import com.csi.sbs.sysadmin.business.entity.CheckListEntity;
import com.csi.sbs.sysadmin.business.exception.NotFoundException;
import com.csi.sbs.sysadmin.business.service.CheckListService;

@Service("CheckListService")
public class CheckListServiceImpl implements CheckListService{

	@SuppressWarnings("rawtypes")
	@Resource
	private CheckListDao checkListDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private ModuleDao moduleDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckListEntity> queryAll() {
		
		return checkListDao.queryAll();
	}

	@Override
	public CheckListEntity selectByName(String apiname) {
		// TODO Auto-generated method stub
		return checkListDao.selectByName(apiname);
	}

	@Override
	public CheckListEntity selectById(String id) {
		// TODO Auto-generated method stub
		return checkListDao.selectById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryApiList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CheckListEntity> apiList = checkListDao.queryAll();
		for(int i=0 ; i<apiList.size(); i++){
  		   String moduleid = apiList.get(i).getModuleid();
  		   apiList.get(i).setModulename(moduleDao.selectById(moduleid).getName());
  		}
		map.put("list", apiList);
		map.put("msg", "Get API List Success");
		map.put("code", "1");
		return map;
	}

	@Override
	public Map<String, Object> getServiceInternalURL(ApiNameModel anm) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		CheckListEntity recle = (CheckListEntity) checkListDao.selectByName(anm.getApiname());
		if(recle == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE4041001),ExceptionConstant.ERROR_CODE4041001);
		}
		map.put("internaURL", recle.getInternalurl());
		return map;
	}

	
	
}