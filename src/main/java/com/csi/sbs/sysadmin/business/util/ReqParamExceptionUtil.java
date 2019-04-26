package com.csi.sbs.sysadmin.business.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.csi.sbs.sysadmin.business.constant.ExceptionConstant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
public class ReqParamExceptionUtil {


	/**
	 * 验证异常统一处理
	 * 
	 * @param req
	 * @param e
	 * @return
	 * @throws JsonProcessingException
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Result handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e)
			throws JsonProcessingException {
		BindingResult bindingResult = e.getBindingResult();
		String errorMesssage = "";
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMesssage += fieldError.getDefaultMessage() + "---";
		}
		return new Result(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE400001) + "/" + errorMesssage, ExceptionConstant.ERROR_CODE400001);
	}

	/**
	 * 请求body异常统一处理
	 * 
	 * @param req
	 * @param e
	 * @return
	 * @throws JsonProcessingException
	 */
	@ExceptionHandler(value = JsonParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Result handleMethodNotReadableException(HttpServletRequest req, JsonParseException e)
			throws JsonProcessingException {
		return new Result(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE400002), ExceptionConstant.ERROR_CODE400002);
		
	}
}
