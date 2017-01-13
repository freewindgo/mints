package com.mints.authority.entity;

import java.util.Date;

/**
 * 用户权限关联实体类
 * @author Justin
 * @date 2017年1月12日
 */
public class SysUsersRoles {
	private SysUsers user;//关联用户类
	private SysRoles role;//关联角色类
	private Date createDate;//记录创建时间，可用于日志
	
	public SysUsers getUser() {
		return user;
	}
	public void setUser(SysUsers user) {
		this.user = user;
	}
	public SysRoles getRole() {
		return role;
	}
	public void setRole(SysRoles role) {
		this.role = role;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
