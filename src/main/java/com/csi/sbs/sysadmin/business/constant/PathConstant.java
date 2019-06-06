package com.csi.sbs.sysadmin.business.constant;

import com.csi.sbs.common.business.constant.CommonConstant;

public class PathConstant {
	
	//获取内部服务调用地址
	public static final String SERVICE_INTERNAL_URL = "http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL;
	//网关地址(服务器)
    public static final String GATEWAY_SERVICE = "47.112.146.96:8086/sysadmin";
    //网关地址(本地)
    public static final String GATEWAY_LOCALHOST = "localhost:8086/sysadmin";
    //获取customer服务地址
    public static final String GET_CUSTOMER_URL = "http://" + CommonConstant.getDEPOSIT() + SysConstant.GET_CUSTOMER_URL;
    //创建customer 服务地址
    public static final String CREATE_CUSTOMER_URL = "http://DEPOSIT/deposit/account/customerCreation";
    //创建Account 服务地址
    public static final String CREATE_ACCOUNT_URL = "http://DEPOSIT/deposit/account/accountCreation";
    //获取下一个可用的递增序列号服务地址
    public static final String NEXT_AVAILABLE = "http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/";
    //开信用卡服务地址
    public static final String CREDITCARD_OPEN = "http://CREDITCARD/creditcard/accountOpening";
    //创建定存单服务地址
    public static final String CREATE_TERMDEPOSIT_APPLICATION = "http://DEPOSIT/deposit/term/termDepositApplication";
    //存款
    public static final String DEPOSIT = "http://DEPOSIT/deposit/account/deposit";
    //账号日期做旧服务地址(deposit)
    public static final String ACCOUNT_OLD_DATE = "http://DEPOSIT/deposit/account/accountDateProcess";
    //账号日期做旧服务地址(investment)
    public static final String ACCOUNT_OLD_DATEI = "http://INVESTMENT/investment/datehandle/accountDateProcess";
    //定存到期取款
    public static final String TERMDEPOSIT_DRAWDOWN = "http://DEPOSIT/deposit/term/termDepositDrawDown";
    //账号日期做旧服务地址(deposit-td_detail)
    public static final String ACCOUNT_OLD_DATE2 = "http://DEPOSIT/deposit/account/tdDetailDateProcess";
    //定存到期续存
    public static final String TERMDEPOSIT_RENEWAL = "http://DEPOSIT/deposit/term/termDepositRenewal";
    //获取某个沙盘的td_detail
    public static final String GETTD_DETAIL = "http://DEPOSIT/deposit/account/queryTdDetail";
    //基金买入
    public static final String FUND_BUY = "http://INVESTMENT/fund/subscription";
    //股票交易
    public static final String STOCK_TRAD = "http://INVESTMENT/investment/stock/stockTrading";
    //外汇交易
    public static final String FOREIGNEXCHANGE = "http://FOREIGNEXCHANGE/foreignExchange/currencyExchange";
    //转账
    public static final String TRANSFER = "http://DEPOSIT/deposit/account/transfer";
    //信用卡还款
    public static final String CREDITCARD_REPEYMENT = "http://CREDITCARD/creditcard/creditCardRepeyment";
    //信用卡交易
    public static final String CREDITCARD_TRANSFER = "http://CREDITCARD/creditcard/transactionPosting";
    //信用卡积分消费
    public static final String CREDITCARD_REDEMPTION = "http://CREDITCARD/point/redemption";
    //信用卡sandbox查询
    public static final String CREDITCARD_SANDBOXSEARCH = "http://CREDITCARD/creditcard/sandboxSearch";
    //投资sandbox查询
    public static final String INVESTMENT_SANDBOXSEARCH = "http://INVESTMENT/investment/datehandle/sandboxSearch";
    //depositsandbox查询
    public static final String DEPOSIT_SANDBOXSEARCH = "http://DEPOSIT/deposit/account/sandboxSearch";
    //删除沙盘数据(deposit)
    public static final String DEL_SANDBOXDATA_DEPOSIT = "http://DEPOSIT/deposit/account/delSandBoxId";
    //删除沙盘数据(creditcard)
    public static final String DEL_SANDBOXDATA_CREDITCARD = "http://CREDITCARD/creditcard/delSandBoxId";
    //删除沙盘数据(investment)
    public static final String DEL_SANDBOXDATA_INVESTMENT = "http://INVESTMENT/investment/datehandle/delSandBoxId";
    //信用卡branch数据查询
    public static final String CREDITCARD_BRANCHSEARCH = "http://CREDITCARD/creditcard/branchDataSearch";
    //投资branch数据查询
    public static final String INVESTMENT_BRANCHSEARCH = "http://INVESTMENT/investment/datehandle/branchDataSearch";
    //deposit branch数据查询
    public static final String DEPOSIT_BRANCHSEARCH = "http://DEPOSIT/deposit/account/branchDataSearch";
    
    
    
    
}
