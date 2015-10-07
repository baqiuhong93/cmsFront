package com.weimini.cms.engity;

import java.io.Serializable;
import java.util.Date;

public class HtmlFragmentLogEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int typeId;
	
	private int type;
	
	private String ftlContent;
	
	private String ftlParams;
	
	private String nodeIds;
	
	private Date createdAt;
	
	private Date updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFtlParams() {
		return ftlParams;
	}

	public void setFtlParams(String ftlParams) {
		this.ftlParams = ftlParams;
	}

	public String getFtlContent() {
		return ftlContent;
	}

	public void setFtlContent(String ftlContent) {
		this.ftlContent = ftlContent;
	}

	public String getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
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
