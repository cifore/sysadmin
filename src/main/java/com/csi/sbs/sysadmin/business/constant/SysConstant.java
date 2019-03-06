package com.csi.sbs.sysadmin.business.constant;

import java.util.HashMap;
import java.util.Map;

public class SysConstant {
	
	//查询客户URL
	public static final String GET_CUSTOMER_URL = "/deposit/account/getCustomer";
	//返回内部服务接口地址 URL
    public static final String SERVICE_INTERNAL_URL = "/sysadmin/getServiceInternalURL";
    //branchNumber
    public static final String NEXT_AVAILABLE_BRANCHNUMBER = "BranchNumber";
    //Token 状态
    public static final String TOKEN_STATE1 = "effective";//有效
    public static final String TOKEN_STATE2 = "invalid";//失效
    
    public static Map<String,Object> getTokenStateMap(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put(TOKEN_STATE1, "effective");
    	map.put(TOKEN_STATE2, "invalid");
    	
    	return map;
    }

    //deposit 服务模块-客户登录uri
    public static final String LOGIN_PATH = "/deposit/login/login";
    //token 默认有效期10天
    public static final int VALIDITYDAYS = 10;
}
