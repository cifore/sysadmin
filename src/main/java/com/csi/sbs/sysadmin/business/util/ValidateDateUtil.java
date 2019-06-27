package com.csi.sbs.sysadmin.business.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.csi.sbs.sysadmin.business.clientmodel.DateRangeModel;

public class ValidateDateUtil {
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static boolean validateDateFormat(String date){
		
		String rexp1 = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if(date.matches(rexp1)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean checkDateRange(DateRangeModel ase) throws ParseException{
		
		String rexp1 = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		String fromdate = ase.getFromDate();
		String todate = ase.getToDate();
		
		if(fromdate.matches(rexp1) == false || todate.matches(rexp1) == false){
			return false;
		}
		if(format.parse(fromdate).getTime() <= format.parse(todate).getTime()){
			return true;
		}
		
		return false;
	}
	
	public static boolean validateTimeFormat(String time){
		
		try{
			Date time1 = format2.parse(time);
			return format2.format(time1).equals(time);
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean validateHolidayFormat(String date){
			
		String rexp1 = "((\\d{2}(([02468][048])|([13579][26]))((((0?[13578])|(1[02]))((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))((0?[1-9])|([1-2][0-9])|(30)))|(0?2((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))((((0?[13578])|(1[02]))((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))((0?[1-9])|([1-2][0-9])|(30)))|(0?2((0?[1-9])|(1[0-9])|(2[0-8]))))))";

		if(date.matches(rexp1)){
			return true;
		}else{
			return false;
		}
		}
}
