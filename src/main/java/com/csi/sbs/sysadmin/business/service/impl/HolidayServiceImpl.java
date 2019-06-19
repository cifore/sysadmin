package com.csi.sbs.sysadmin.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.HolidayModel;
import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.csi.sbs.sysadmin.business.dao.HolidayDao;
import com.csi.sbs.sysadmin.business.entity.HolidayEntity;
import com.csi.sbs.sysadmin.business.service.HolidayService;
import com.csi.sbs.sysadmin.business.util.ResponseUtil;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("HolidayService")
public class HolidayServiceImpl implements HolidayService{

	
	@SuppressWarnings("rawtypes")
	@Resource
	private HolidayDao holidayDao;

	@SuppressWarnings("unchecked")
	@Override
	public int save(HolidayModel holidayModel) {
		// model change
		HolidayEntity addHolidayEntity = new HolidayEntity();
		addHolidayEntity.setCountrycode(holidayModel.getCountrycode());
		addHolidayEntity.setDay(holidayModel.getDay());
		addHolidayEntity.setId(UUIDUtil.generateUUID());
		return holidayDao.insert(addHolidayEntity);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultUtil getAll() {
		HolidayEntity searchHolidayEntity = new HolidayEntity();
		@SuppressWarnings("unchecked")
		List<HolidayEntity> resHolidayList = holidayDao.findMany(searchHolidayEntity);
	    if(resHolidayList!=null && resHolidayList.size()>0){
	    	return ResponseUtil.responseS(ExceptionConstant.SUCCESS_CODE200, resHolidayList);
	    }
	    return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isHoliday(HolidayModel holidayModel) {
		//model change
		HolidayEntity searchHolidayEntity = new HolidayEntity();
		searchHolidayEntity.setDay(holidayModel.getDay());
		HolidayEntity res_search = (HolidayEntity) holidayDao.findOne(searchHolidayEntity);
		if(res_search==null){
			return false;
		}
		return true;
	}
}
