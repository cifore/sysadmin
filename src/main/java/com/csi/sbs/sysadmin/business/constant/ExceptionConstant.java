package com.csi.sbs.sysadmin.business.constant;

import java.util.HashMap;
import java.util.Map;

public class ExceptionConstant {
	
	/**
	 * 正常码
	 */
	public static final int SUCCESS_CODE2001001 = 2001001;//登录成功
	
	/**
     * 错误码
     */
	public static final int ERROR_CODE2021001 = 2021001;//登录失败
	
	public static final int ERROR_CODE4031001 = 4031001;//授权失败
	public static final int ERROR_CODE4031002 = 4031002;//已经授权
	
	public static final int ERROR_CODE5001001 = 5001001;//获取客户信息失败
	
    
    public static Map<Integer,String> getExceptionMap(){
    	Map<Integer,String> map = new HashMap<Integer,String>();
    	map.put(ERROR_CODE2021001, "Login Fail");//登录失败
    	
    	map.put(ERROR_CODE4031001, "Authorization failed,There's dirty data");//授权失败
    	map.put(ERROR_CODE4031002, "Authorized");//已经授权
    	
    	map.put(ERROR_CODE5001001, "Get Customer Fail");//获取客户信息失败
    	return map;
    }
    
    public static Map<Integer,String> getSuccessMap(){
    	Map<Integer,String> map = new HashMap<Integer,String>();
    	map.put(SUCCESS_CODE2001001, "Login Success");//登录成功
    	
    	
    	return map;
    }

}
