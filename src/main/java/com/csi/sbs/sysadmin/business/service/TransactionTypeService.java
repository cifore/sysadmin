package com.csi.sbs.sysadmin.business.service;

import com.csi.sbs.sysadmin.business.clientmodel.AddTransactionTypeModel;
import com.csi.sbs.sysadmin.business.clientmodel.QueryTransactionTypeModel;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

public interface TransactionTypeService {
	
	
	
	@SuppressWarnings("rawtypes")
	public ResultUtil addTranType(AddTransactionTypeModel addTransactionTypeModel);

	public ResultUtil<QueryTransactionTypeModel> queryByType(String trantype);
}
