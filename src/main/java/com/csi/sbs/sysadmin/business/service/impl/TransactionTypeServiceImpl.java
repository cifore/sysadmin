package com.csi.sbs.sysadmin.business.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.sysadmin.business.clientmodel.AddTransactionTypeModel;
import com.csi.sbs.sysadmin.business.clientmodel.QueryTransactionTypeModel;
import com.csi.sbs.sysadmin.business.dao.TransactionTypeDao;
import com.csi.sbs.sysadmin.business.entity.TransactionType;
import com.csi.sbs.sysadmin.business.service.TransactionTypeService;
import com.csi.sbs.sysadmin.business.util.ResultUtil;

@Service("TransactionTypeService")
public class TransactionTypeServiceImpl implements TransactionTypeService {

	@SuppressWarnings("rawtypes")
	@Resource
	private TransactionTypeDao transactionTypeDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public ResultUtil addTranType(AddTransactionTypeModel addTransactionTypeModel) {
		ResultUtil result = new ResultUtil();
		//根据交易类型代码查询是否存在
		TransactionType trantype = new TransactionType();
		trantype.setTrantype(addTransactionTypeModel.getTrantype());
		TransactionType retrantype = (TransactionType) transactionTypeDao.findOne(trantype);
		if(retrantype!=null){
			result.setCode("0");
			result.setMsg("This type already exists");
			return result;
		}
		trantype.setId(UUIDUtil.generateUUID());
		trantype.setTrantypename(addTransactionTypeModel.getTrantypename());
		transactionTypeDao.insert(trantype);
		
		result.setCode("1");
		result.setMsg("add success trantype:"+trantype.getTrantype());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResultUtil<QueryTransactionTypeModel> queryByType(String trantype) {
		ResultUtil<QueryTransactionTypeModel> result = new ResultUtil<QueryTransactionTypeModel>();
		TransactionType type = new TransactionType();
		type.setTrantype(trantype);
		TransactionType retype = (TransactionType) transactionTypeDao.findOne(type);
		if(retype==null){
			result.setCode("0");
			result.setMsg("search fail");
			return result;
		}
		//model change
		QueryTransactionTypeModel typemodel = new QueryTransactionTypeModel();
		typemodel.setTrantype(trantype);
		typemodel.setTrantypename(retype.getTrantypename());
		result.setCode("1");
		result.setMsg("search success");
		result.setData(typemodel);
		return result;
	}
}
