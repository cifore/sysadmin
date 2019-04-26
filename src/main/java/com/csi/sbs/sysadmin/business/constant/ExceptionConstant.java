package com.csi.sbs.sysadmin.business.constant;

import java.util.HashMap;
import java.util.Map;

public class ExceptionConstant {
	
	/**
	 * 正常码
	 */
	public static final int SUCCESS_CODE2001001 = 2001001;//登录成功
	public static final int SUCCESS_CODE2001002 = 2001002;//添加成功
	public static final int SUCCESS_CODE2001003 = 2001003;//给developer 指派沙盒成功
	public static final int SUCCESS_CODE2001004 = 2001004;//获取所有会产生沙盘数据的表成功
	
	/**
     * 错误码
     */
	public static final int ERROR_CODE2021001 = 2021001;//登录失败
	public static final int ERROR_CODE2021002 = 2021002;//登录名已经存在
	public static final int ERROR_CODE2021003 = 2021003;//customerNumber已经存在
	public static final int ERROR_CODE2021004 = 2021004;//customerNumber不正确
	public static final int ERROR_CODE2021005 = 2021005;//user已经存在
	public static final int ERROR_CODE2021006 = 2021006;//branch number 已经达到最大
	public static final int ERROR_CODE2021007 = 2021007;//branchnumber,countryCode,clearcode已经存在
	public static final int ERROR_CODE2021008 = 2021008;//用户已经授权
	
	public static final int ERROR_CODE4031001 = 4031001;//授权失败
	public static final int ERROR_CODE4031002 = 4031002;//已经授权
	
	public static final int ERROR_CODE4041001 = 4041001;//根据API Name 未查到内部调用地址
	public static final int ERROR_CODE4041002 = 4041002;//DeveloperID 不存在
	public static final int ERROR_CODE4041003 = 4041003;//BankID does not exist
	public static final int ERROR_CODE4041004 = 4041004;//tablename not exist
	public static final int ERROR_CODE4041005 = 4041005;//Record Not Found
	
	public static final int ERROR_CODE400001 = 400001;//必填字段不全
	public static final int ERROR_CODE400002 = 400002;//请求body格式有误
	
	
	public static final int ERROR_CODE5001001 = 5001001;//获取客户信息失败
	public static final int ERROR_CODE5001002 = 5001002;//添加登录用户失败
	public static final int ERROR_CODE5001003 = 5001003;//根据API Name 查内部调用地址失败
    public static final int ERROR_CODE5001004 = 5001004;//customerNumber校验失败
    public static final int ERROR_CODE5001005 = 5001005;//调用服务接口地址失败
    public static final int ERROR_CODE5001006 = 5001006;//调用系统参数失败
    public static final int ERROR_CODE5001007 = 5001007;//给developer 指派沙盒失败
	public static final int ERROR_CODE5001008 = 5001008;//获取所有会产生沙盘数据的表失败
    
    public static Map<Integer,String> getExceptionMap(){
    	Map<Integer,String> map = new HashMap<Integer,String>();
    	map.put(ERROR_CODE2021001, "Login Fail");//登录失败
    	map.put(ERROR_CODE2021002, "Login name already exists");//登录名已经存在
    	map.put(ERROR_CODE2021003, "Customer Number already exists");//customerNumber已经存在
    	map.put(ERROR_CODE2021004, "Customer Number is not correct");//customerNumber不正确
    	map.put(ERROR_CODE2021005, "User already exists");//user已经存在
    	map.put(ERROR_CODE2021006, "Branch number has reached its maximum");//branch number 已经达到最大
    	map.put(ERROR_CODE2021007, "---");//branchnumber,countryCode,clearcode已经存在
    	map.put(ERROR_CODE2021008, "This user has authorized");//用户已经授权
    	
    	map.put(ERROR_CODE4031001, "Authorization failed,There's dirty data");//授权失败
    	map.put(ERROR_CODE4031002, "Authorized");//已经授权
    	
    	map.put(ERROR_CODE400001, "Required field incomplete");//必填字段不全
    	map.put(ERROR_CODE400002, "Incorrect requesting format");//请求body格式有误
    	
    	map.put(ERROR_CODE4041001, "Not Found InternalService Url");//根据API Name 未查到内部调用地址
    	map.put(ERROR_CODE4041002, "DeveloperID does not exist");//DeveloperID 不存在
    	map.put(ERROR_CODE4041003, "BankID does not exist");//BankID does not exist
    	map.put(ERROR_CODE4041004, "tablename not exist");//BankID does not exist
    	map.put(ERROR_CODE4041005, "Record Not Found");//Record Not Found
    	
    	map.put(ERROR_CODE5001001, "Get Customer Fail");//获取客户信息失败
    	map.put(ERROR_CODE5001002, "Failed to add login user");//添加登录用户失败
    	map.put(ERROR_CODE5001003, "Get InternalService Fail");//根据API Name 查内部调用地址失败
    	map.put(ERROR_CODE5001004, "Covermer Number check failed");//customerNumber校验失败
    	map.put(ERROR_CODE5001005, "Failed to call service interface address");//调用服务接口地址失败
    	map.put(ERROR_CODE5001006, "Failed to call system parameters");//调用系统参数失败
    	map.put(ERROR_CODE5001007, "Appoint SandBoxId Fail For developer");//给developer 指派沙盒失败
    	map.put(ERROR_CODE5001008, "Get SandBox Table Fail");//获取所有会产生沙盘数据的表失败
    	
    	return map;
    }
    
    public static Map<Integer,String> getSuccessMap(){
    	Map<Integer,String> map = new HashMap<Integer,String>();
    	map.put(SUCCESS_CODE2001001, "Login Success");//登录成功
    	map.put(SUCCESS_CODE2001002, "Add Success");//添加成功
    	map.put(SUCCESS_CODE2001003, "Appoint SandBoxId Success For developer");//给developer 指派沙盒成功
    	map.put(SUCCESS_CODE2001004, "Get SandBox Table Success");//获取所有会产生沙盘数据的表成功
    	
    	return map;
    }

}
