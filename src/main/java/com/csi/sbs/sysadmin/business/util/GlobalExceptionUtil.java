package com.csi.sbs.sysadmin.business.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionUtil {
	
	private ObjectMapper objectMapper = new ObjectMapper();

	
	/**
     * validate exception
     * @param req
     * @param e
     * @return
     * @throws MethodArgumentNotValidException
	 * @throws JsonProcessingException 
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) throws MethodArgumentNotValidException, JsonProcessingException {
    	ResultUtil r = new ResultUtil();
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "---";
        }
        r.setMsg(errorMesssage);
        r.setCode("0");
        return objectMapper.writeValueAsString(r);
    }
	
	
}
