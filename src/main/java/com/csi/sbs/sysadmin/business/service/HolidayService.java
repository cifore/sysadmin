package com.csi.sbs.sysadmin.business.service;

import java.util.Map;

import com.csi.sbs.sysadmin.business.clientmodel.HolidayModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface HolidayService {
	
	
	
	public int save(HolidayModel holidayModel);
	
	@SuppressWarnings("rawtypes")
	public ResultUtil getAll();
	
	public boolean isHoliday(HolidayModel holidayModel);
	
	public Map<String, Object> insertHoliday(HolidayModel holidayModel) throws Exception;
	
	public Map<String, Object> updateHoliday(HolidayModel holidayModel) throws Exception;
	
	public Map<String, Object> deleteHoliday(HolidayModel holidayModel) throws Exception;
	
	public Map<String, Object> queryHolidayList() throws Exception;
	
	public Map<String, Object> getHolidayInfo(HolidayModel holidayModel) throws Exception;

}
