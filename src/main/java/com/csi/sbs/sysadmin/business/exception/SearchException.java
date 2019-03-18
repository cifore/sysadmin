package com.csi.sbs.sysadmin.business.exception;

import com.csi.sbs.common.business.exception.GlobalException;

@SuppressWarnings("serial")
public class SearchException extends GlobalException {
	
	public SearchException(String message, int code) {
		super(message, code);
	}
}