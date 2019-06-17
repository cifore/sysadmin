package com.csi.sbs.sysadmin.business.util;

import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;

public class ResponseUtil {
	
	/**
	 * 异常返回
	 * @param code
	 * @param t
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ResultUtil responseE(int code,T t){
		ResultUtil result = new ResultUtil();
		result.setCode(String.valueOf(code));
		result.setMsg(ExceptionConstant.getExceptionMap().get(code));
		result.setData(t);
		return result;
	}
	
	
	/**
	 * 成功返回
	 * @param code
	 * @param t
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ResultUtil responseS(int code,T t){
		ResultUtil result = new ResultUtil();
		result.setCode(String.valueOf(code));
		result.setMsg(ExceptionConstant.getSuccessMap().get(code));
		result.setData(t);
		return result;
	}

}
