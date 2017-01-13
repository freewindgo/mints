package com.mints.base;

import java.util.Date;

/**
 * Base实体类，用于其他实体类继承
 * @author Justin
 * @date 2017年1月12日
 */
public class BaseEntity {
	
	protected String id;
	protected Date createDate;
	protected Date modifyDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	

}
