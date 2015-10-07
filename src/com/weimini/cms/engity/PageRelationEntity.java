package com.weimini.cms.engity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="page_relation")
public class PageRelationEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	public PageRelationEntity() {
		super();
	}

	@Id
	private String id;
	
	@Field("nodeId")
	private int nodeId;
	
	@Field("nodeIds")
	private List<Integer> nodeIds = new ArrayList<Integer>();
	
	@Field("fragmentIds")
	private List<Integer> fragmentIds = new ArrayList<Integer>();
	
	@Field("createdAt")
	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public List<Integer> getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(List<Integer> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public List<Integer> getFragmentIds() {
		return fragmentIds;
	}

	public void setFragmentIds(List<Integer> fragmentIds) {
		this.fragmentIds = fragmentIds;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
