package com.mints.util;

import java.util.List;

/**
 * 分页工具类
 * 
 * @author Justin
 * @param <T>
 * @date 2017年1月4日
 */
public class Pager<T> {
	private static final Integer MAX_PAGE_SIZE = 500;// 每一页最大的记录数

	// 排序方式 递增、递减
	private enum Order {
		asc, desc
	}

	private List<T> resultList;// 返回的结果集
	private int pageTotal = 1;// 总页数
	private int pageNo = 1;// 当前页数
	private int pageSize = 20;// 每页显示数据数
	private int dataTotal;// 总数据数

	private String searchBy;// 查找的字段
	private String searchValue;// 查找字段的值
	private String orderBy;// 排序字段
	private Order order;// 排序方式

	
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	//总页数，无需set，计算得到
	public int getPageTotal() {
		pageTotal = dataTotal / pageSize;
		if(pageTotal == 0){
			pageTotal = 1;
		}
		if (dataTotal % pageSize > 0) {
			pageTotal++;
		}
		return pageTotal;
	}

	public int getPageNo() {
		return pageNo;
	}

	//避免用户输入0或负数
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}

	}

	public int getPageSize() {
		return pageSize;
	}

	//设置每页显示的数据量
	public void setPageSize(int pageSize) {
		if(pageSize < 1){
			pageSize = 1;
		}else if(pageSize > MAX_PAGE_SIZE){
			pageSize = MAX_PAGE_SIZE;
		}else{
			this.pageSize = pageSize;
		}
	}

	public int getDataTotal() {
		return dataTotal;
	}

	public void setDataTotal(int dataTotal) {
		this.dataTotal = dataTotal;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
