package com.weimini.cms.engity;

import java.io.Serializable;
import java.util.Date;

import com.weimini.core.util.CmsConstant;
import com.weimini.core.util.SystemUtils;


public class HtmlTemplateEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private int typeId;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplateUrl() {
		return "/templates/" + SystemUtils.dateToStr(this.getCreatedAt(), "yyyy/MM/dd/") + this.getId() + ".jsp";
	}
	
	public String getLocalTemplateUrl() {
		return CmsConstant.TOMCAT_LOCAL_URL + this.getTemplateUrl();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
