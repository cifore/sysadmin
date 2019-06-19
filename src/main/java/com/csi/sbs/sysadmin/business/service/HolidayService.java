package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.clientmodel.HolidayModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface HolidayService {
	
	
	
	public int save(HolidayModel holidayModel);
	
	@SuppressWarnings("rawtypes")
	public ResultUtil getAll();
	
	public boolean isHoliday(HolidayModel holidayModel);
	

}
