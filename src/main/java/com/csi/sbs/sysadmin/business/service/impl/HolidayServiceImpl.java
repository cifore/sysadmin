package com.csi.sbs.sysadmin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.csi.sbs.sysadmin.business.util.ValidateDateUtil;

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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertHoliday(HolidayModel holidayModel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String holidayDate = holidayModel.getDay();
		if(holidayDate == null || holidayDate.length() == 0){
			map.put("code", "0");
			map.put("msg", "day is a requiry field");
			return map;
		}
		
		if(!ValidateDateUtil.validateHolidayFormat(holidayDate)){
			map.put("code", "0");
			map.put("msg", "Incorrect holiday day format");
			return map;
		}
		
		HolidayEntity holidayRequest = new HolidayEntity();
		holidayRequest.setCountrycode(holidayModel.getCountrycode());
		holidayRequest.setDay(holidayModel.getDay());
		
		HolidayEntity holidayInfo = (HolidayEntity) holidayDao.findOne(holidayRequest);
		
		if(holidayInfo != null){
			map.put("code", "0");
			map.put("msg", "The holiday has exsited");
			return map;
		}
		
		holidayRequest.setId(UUIDUtil.generateUUID());
		holidayDao.insert(holidayRequest);
		
		map.put("code", "1");
		map.put("msg", "insert succeed");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> updateHoliday(HolidayModel holidayModel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(holidayModel.getId() == null || holidayModel.getId().length() == 0){
			map.put("code", "0");
			map.put("msg", "id is a required field");
			return map;
		}
		
		String holidayDate = holidayModel.getDay();
		
		if(holidayDate != null && holidayDate.length() > 0 && !ValidateDateUtil.validateHolidayFormat(holidayDate)){
			map.put("code", "0");
			map.put("msg", "Incorrect holiday day format");
			return map;
		}
		HolidayEntity holidayRequest = new HolidayEntity();
		holidayRequest.setCountrycode(holidayModel.getCountrycode());
		holidayRequest.setDay(holidayModel.getDay());
		
		HolidayEntity holidayInfo = (HolidayEntity) holidayDao.findOne(holidayRequest);
		
		if(holidayInfo != null){
			if(holidayInfo.getId().equals(holidayModel.getId()) == false){
				map.put("msg", "The holiday has exsited");
			}
			if(holidayInfo.getId().equals(holidayModel.getId()) == true){
				map.put("msg", "No holiday info changes");
			}
			map.put("code", "0");
			return map;
		}
		
		holidayRequest.setId(holidayModel.getId());
		holidayDao.update(holidayRequest);
		
		map.put("code", "1");
		map.put("msg", "udpate succeed");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> deleteHoliday(HolidayModel holidayModel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = holidayModel.getId();
		if(id == null || id.length() == 0){
			map.put("code", "0");
			map.put("msg", "id is a required field");
			return map;
		}
		
		HolidayEntity holidayRequest = new HolidayEntity();
		holidayRequest.setId(id);
		holidayRequest.setCountrycode(holidayModel.getCountrycode());
		holidayRequest.setDay(holidayModel.getDay());
		HolidayEntity holidayInfo = (HolidayEntity) holidayDao.findOne(holidayRequest);
		
		if(holidayInfo == null){
			map.put("code", "0");
			map.put("msg", "No holiday info");
			return map;
		}
		
		holidayDao.delete(id);
		map.put("code", "1");
		map.put("msg", "delete succeed");
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryHolidayList() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		HolidayEntity holidayRequest = new HolidayEntity();
		List<HolidayEntity> list = holidayDao.findMany(holidayRequest);
		
		map.put("code", "1");
		map.put("msg", "query succeed");
		map.put("list", list);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getHolidayInfo(HolidayModel holidayModel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(holidayModel.getId() == null || holidayModel.getId().length() == 0){
			map.put("code", "0");
			map.put("msg", "id is a required field");
			return map;
		}
		HolidayEntity holidayRequest = new HolidayEntity();
		holidayRequest.setId(holidayModel.getId());
		holidayRequest.setCountrycode(holidayModel.getCountrycode());
		holidayRequest.setDay(holidayModel.getDay());
		
		HolidayEntity holidayInfo = (HolidayEntity) holidayDao.findOne(holidayRequest);
		if(holidayInfo == null){
			map.put("code", "0");
			map.put("msg", "No holiday info");
			return map;
		}
		
		map.put("code", "0");
		map.put("msg", "query succeed");
		map.put("holidayInfo", holidayInfo);
		return map;
	}
}
