package com.mints.authority.entity;

import java.util.Set;

import com.mints.base.BaseEntity;

/**
 * 角色实体类
 * @author Justin
 * @date 2017年1月12日
 */
public class SysRoles extends BaseEntity{
   
    private String name;
    private String description;
    private String isSystem;
    private Integer sort;
    private Set<SysUsers> sysUsers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public Set<SysUsers> getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(Set<SysUsers> sysUsers) {
		this.sysUsers = sysUsers;
	}
}