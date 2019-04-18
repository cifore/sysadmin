package com.csi.sbs.sysadmin.business.constant;

import com.csi.sbs.common.business.constant.CommonConstant;

public class PathConstant {
	
	//获取内部服务调用地址
	public static final String SERVICE_INTERNAL_URL = "http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL;
	//网关地址(服务器)
    public static final String GATEWAY_SERVICE = "117.78.38.89:8086/sysadmin";
    //网关地址(本地)
    public static final String GATEWAY_LOCALHOST = "localhost:8086/sysadmin";
    //获取customer服务地址
    public static final String GET_CUSTOMER_URL = "http://" + CommonConstant.getDEPOSIT() + SysConstant.GET_CUSTOMER_URL;
    //创建customer 服务地址
    public static final String CREATE_CUSTOMER_URL = "http://DEPOSIT/deposit/account/customerCreation";
    //t_customer_master模板地址
    public static final String CUSTOMER_TEMPLATE = "D://t_customer_master.xls";
    //创建Account 服务地址
    public static final String CREATE_ACCOUNT_URL = "http://DEPOSIT/deposit/account/accountCreation";

}
