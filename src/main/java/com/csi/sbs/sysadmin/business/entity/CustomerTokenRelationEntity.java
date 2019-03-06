package com.csi.sbs.sysadmin.business.entity;

import java.util.Date;

public class CustomerTokenRelationEntity {
	
    private String id;

    private String logininpk;

    private String tokenid;

    private Date createdate;
    
    

    

	public String getLogininpk() {
		return logininpk;
	}

	public void setLogininpk(String logininpk) {
		this.logininpk = logininpk;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid == null ? null : tokenid.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}