package com.weimini.core.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;



import org.apache.commons.lang3.StringUtils;


/**
 * 分页工具类
 * @作者 qiuhongba
 * @创建日期 2011-8-24
 * @版本 V 1.0
 *
 */
public class PagerDTO {
	
	//当前第几页
	private long pageNo=1;

	//总共多少页
	private long pageSize;
	
	//每页显示几条数据
	private long perPage = 10;
	
	//总共多少条
	private long pageCount;
	
	/* 排序方式 */
	private String orderBy;
	
	/* 查询字符串 */
	private String queryStr;
	
	/* 表单防止重复提交码 */
	private String formToken;
	
	/* 必须有参数才能做分页查询 */
	private boolean argsCanSearch;
	
	/* 如果为真,并且pageCount > 0 ,则在数据库中不进行查询 */
	private boolean userPageCount;

	public boolean isUserPageCount() {
		return userPageCount;
	}

	public void setUserPageCount(boolean userPageCount) {
		this.userPageCount = userPageCount;
	}

	public boolean isArgsCanSearch() {
		return argsCanSearch;
	}

	public void setArgsCanSearch(boolean argsCanSearch) {
		this.argsCanSearch = argsCanSearch;
	}

	public String getFormToken() {
		return formToken;
	}

	public void setFormToken(String formToken) {
		this.formToken = formToken;
	}

	public void init(long pageCount) {
		this.setPageCount(pageCount);
		boolean flag = (pageCount%this.getPerPage() == 0) ? false : true;
		long iPageSize = flag ? (pageCount/this.getPerPage()+1) : (pageCount/this.getPerPage());
		if(this.getPageNo() > iPageSize) {
			this.setPageNo(1);
		}
		this.setPageSize(iPageSize);
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getPerPage() {
		return perPage;
	}

	public void setPerPage(long perPage) {
		if(perPage > 100) {
			this.perPage = 100;
		} else {
			this.perPage = perPage;
		}
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getQueryStr() {
		return queryStr;
	}
	
	public String getEncodeQueryStr() {
		if(queryStr != null && !"".equals(queryStr)) {
			try {
				return URLEncoder.encode(queryStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public String getDecodeQueryStr() {
		if(queryStr != null && !"".equals(queryStr)) {
			try {
				return URLDecoder.decode(queryStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	public String getCacheString() {
		StringBuffer cacheKey = new StringBuffer();
		cacheKey.append(this.getPageNo()).append("#");
		cacheKey.append(this.getPerPage()).append("#");
		if(StringUtils.isNotEmpty(this.getOrderBy())) {
			cacheKey.append(this.getOrderBy()).append("#");
		}
		List<String> args = new ArrayList<String>();
		setCacheList(args);
		if(args != null && !args.isEmpty()) {
			for(String arg : args) {
				if(StringUtils.isNotEmpty(arg)) {
					cacheKey.append(arg).append("#");
				}
			}
		}
		return cacheKey.toString();
	}
	
	/**
	 * 设置缓存KEY
	 * @param cacheKeys
	 */
	public void setCacheList(List<String> args) {
		
	}

}
