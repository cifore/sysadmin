package com.csi.sbs.sysadmin.business.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.csi.sbs.sysadmin.business.util.Result;

import org.springframework.http.HttpStatus;


@ControllerAdvice
public class SysadminProcessException {
	
	//private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleResourceNotFoundException(NotFoundException e)
    {
        return new Result(e.getMessage(), e.getCode());
    }
    
    @ExceptionHandler(value = InsertException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleResourceInsertException(InsertException e)
    {
        return new Result(e.getMessage(), e.getCode());
    }
    
    @ExceptionHandler(value = AuthorityException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handleResourceAuthorityException(AuthorityException e)
    {
        return new Result(e.getMessage(), e.getCode());
    }

    @ExceptionHandler(value = CallOtherException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleResourceCallOtherException(CallOtherException e)
    {
        return new Result(e.getMessage(), e.getCode());
    }
    
    @ExceptionHandler(value = AcceptException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result handleResourceAcceptException(AcceptException e)
    {
        return new Result(e.getMessage(), e.getCode());
    }
    
    @ExceptionHandler(value = SearchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleResourceSearchException(SearchException e)
    {
        return new Result(e.getMessage(), e.getCode());
    }
	
}
