package com.csi.sbs.sysadmin.business.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.sysadmin.business.clientmodel.HolidayModel;
import com.csi.sbs.sysadmin.business.service.HolidayService;
import com.csi.sbs.sysadmin.business.util.HolidayUtil;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/sysadmin")
public class HolidayController {
	
	
	@Resource
	private HolidayService holidayService;
	
	
	@Resource
	private RestTemplate restTemplate;
	
	
	/**
	 * 初始化全年的节假日信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/initHoliday", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to return holiday", notes = "version 0.0.1")
	public ResultUtil initHoliday() throws Exception {
		try {
			return HolidayUtil.getHoliday(holidayService);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 判断某个日期是否是法定节假日
	 * @param date
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/isHoliday/{date}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to isholiday", notes = "version 0.0.1")
	public boolean isHoliday(@ApiParam(name = "date", value = "date eg: 20191001 ", required = true) @PathVariable("date") String date,
			HttpServletRequest request) throws Exception {
		try {
			HolidayModel holidayModel = new HolidayModel();
			holidayModel.setDay(date);
			return holidayService.isHoliday(holidayModel);
		} catch (Exception e) {
			throw e;
		}
	}

}
