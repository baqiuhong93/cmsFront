package com.weimini.cms.engity;

import java.io.Serializable;

public class KeywordEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int Id;
	
	private String name;
	
	private String addr;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
