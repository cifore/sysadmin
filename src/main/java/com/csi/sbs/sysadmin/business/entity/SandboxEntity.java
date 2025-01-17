package com.csi.sbs.sysadmin.business.entity;

import java.util.Date;

public class SandboxEntity {
	
    private String id;

    private String sandboxid;

    private String state;

    private Date createdate;
    
    private Date lastupdateddate;
    
    
    //表外字段
    private int count;//随机获取count条数据
    
    
    
    
    
    

    public Date getLastupdateddate() {
		return lastupdateddate;
	}

	public void setLastupdateddate(Date lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSandboxid() {
        return sandboxid;
    }

    public void setSandboxid(String sandboxid) {
        this.sandboxid = sandboxid == null ? null : sandboxid.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}