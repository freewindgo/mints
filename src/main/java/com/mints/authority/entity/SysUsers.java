package com.mints.authority.entity;

import java.util.Date;
import java.util.Set;

import com.mints.base.BaseEntity;

/**
 * 用户实体类
 * @author Justin
 * @date 2017年1月12日
 */
public class SysUsers extends BaseEntity{
   
    private String username;
    private String password;
    private String name;
    private String depId;
    private Date loginDate;
    private String loginIp;
    private String isValid;
    private String status;
    private Set<SysRoles> sysRoles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public Set<SysRoles> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRoles> sysRoles) {
		this.sysRoles = sysRoles;
	}
}