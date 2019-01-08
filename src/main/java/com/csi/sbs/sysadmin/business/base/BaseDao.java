package com.csi.sbs.sysadmin.business.base;


public interface BaseDao<T> {
	
	
    int insert(T t);  
   
    int delete(String id);  
    
    int update(T t);

}
