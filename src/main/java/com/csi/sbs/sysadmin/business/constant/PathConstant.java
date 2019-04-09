package com.csi.sbs.sysadmin.business.constant;

import com.csi.sbs.common.business.constant.CommonConstant;

public class PathConstant {
	
	
	public static final String SERVICE_INTERNAL_URL = "http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL;
	//网关地址(服务器)
    public static final String GATEWAY_SERVICE = "117.78.38.89:8086/sysadmin";
    //网关地址(本地)
    public static final String GATEWAY_LOCALHOST = "localhost:8086/sysadmin";
    public static final String GET_CUSTOMER_URL = "http://" + CommonConstant.getDEPOSIT() + SysConstant.GET_CUSTOMER_URL;
}
