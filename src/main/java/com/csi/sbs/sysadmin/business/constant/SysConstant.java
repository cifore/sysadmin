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
    //SandBoxCustomerID
    public static final String SANDBOX_CUSTOMERID = "SandBoxCustomerID";
    //SandBoxCustomerID_sample
    public static final String SANDBOX_CUSTOMERID_SAMPLE = "T";
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
    public static final int VALIDITYDAYS = 30;
    //用户类型
    public static final String USER_TYPE0 = "0";//普通用户
    public static final String USER_TYPE1 = "1";//银行柜员
    
    public static Map<String,Object> getUserTypeMap(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put(USER_TYPE0, "普通用户");
    	map.put(USER_TYPE1, "银行柜员");
    	
    	return map;
    }
    
    
    //t_customer_master(生成沙盘数据时,所需的客户模板文件名称.xls)
    public static final String T_CUSTOMER_MASTER = "t_customer_master";
    //生成沙盘数据时,所用的基金代码
    public static final String SANDBOX_FUNDCODE = "U000001";
    //生成沙盘数据时,所用的信用卡商户Number
    public static final String SANDBOX_MERCHANT_NUMBER = "HK0001001000000001";
    //生成沙盘数据时 ,所用的信用卡积分消费productcode"00001"
    public static final String SANDBOX_PRODUCTCODE = "00001";
    
    
    /**
     * 调用设置沙盘接口时,以下表会产生沙盘数据
     */
    public static final String T_CUSTOMER_MASTER_SANDBOX = "t_customer_master";
    public static final String T_CURRENTACCOUNT_MASTER = "t_currentaccount_master";
    public static final String T_SAVINGACCOUNT_MASTER = "t_savingaccount_master";
    public static final String T_FEXACCOUNT_MASTER = "t_fexaccount_master";
    public static final String T_TERMDEPOSIT_MASTER = "t_termdeposit_master";
    public static final String T_TERMDEPOSIT_DETAIL = "t_termdeposit_detail";
    public static final String T_PRECIOUSMETALACCOUNT_MASTER = "t_preciousmetalaccount_master";
    public static final String T_TRANSACTION_LOG = "t_transaction_log";
    public static final String T_STOCKTRADINGACCOUNT_MASTER = "t_stocktradingaccount_master";
    public static final String T_MUTUALFUNDACCOUNT_MASTER = "t_mutualfundaccount_master";
    public static final String T_MUTUALFUND_PLATFORM_LOG = "t_mutualfund_platform_log";
    public static final String T_STOCK_PLATFORM_LOG = "t_stock_platform_log";
    public static final String T_CREDITCARD_MASTER = "t_creditcard_master";
    public static final String T_CREDITCARD_TRANSACTION_DETAIL = "t_creditcard_transaction_detail";

    
    public static Map<String,String> getSandBoxTable(){
    	Map<String,String> map = new HashMap<String,String>();
    	map.put(T_CUSTOMER_MASTER_SANDBOX, "Customer Master Table");
    	map.put(T_CURRENTACCOUNT_MASTER, "Current Account Table");
    	map.put(T_SAVINGACCOUNT_MASTER, "Saving Account Table");
    	map.put(T_FEXACCOUNT_MASTER, "Fex Account Table");
    	map.put(T_TERMDEPOSIT_MASTER, "TermDeposit Account Table");
    	map.put(T_TERMDEPOSIT_DETAIL, "TermDeposit Detail Table");
    	map.put(T_PRECIOUSMETALACCOUNT_MASTER, "PreciousMetal Account Table");
    	map.put(T_TRANSACTION_LOG, "Transaction Log Table");
    	map.put(T_STOCKTRADINGACCOUNT_MASTER, "StockTrading Account Table");
    	map.put(T_MUTUALFUNDACCOUNT_MASTER, "MutualFund Account Table");
    	map.put(T_MUTUALFUND_PLATFORM_LOG, "MutualFund PlatForm Log Table");;
    	map.put(T_MUTUALFUND_PLATFORM_LOG, "MutualFund PlatForm Log Table");
    	map.put(T_STOCK_PLATFORM_LOG, "Stock PlatForm Log Table");
    	map.put(T_CREDITCARD_MASTER, "CreditCard Account Table");
    	map.put(T_CREDITCARD_TRANSACTION_DETAIL, "CreditCard Transaction Detail Table");

    	
    	
    	return map;
    }
    
}
