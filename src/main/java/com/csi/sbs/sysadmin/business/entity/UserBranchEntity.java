package com.csi.sbs.sysadmin.business.entity;

public class UserBranchEntity {
	
    private String id;

    private String userid;

    private String bankid;
    
    private String sandboxid;
    
    private String dockerid;
    
    
    
    

    public String getDockerid() {
		return dockerid;
	}

	public void setDockerid(String dockerid) {
		this.dockerid = dockerid;
	}

	public String getSandboxid() {
		return sandboxid;
	}

	public void setSandboxid(String sandboxid) {
		this.sandboxid = sandboxid;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid == null ? null : bankid.trim();
    }
}