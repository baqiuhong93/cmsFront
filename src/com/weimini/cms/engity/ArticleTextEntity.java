package com.weimini.cms.engity;

import java.io.Serializable;

public class ArticleTextEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String Content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	

}
