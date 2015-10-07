package com.weimini.core.taglib;


import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weimini.cms.cache.IArticleCache;
import com.weimini.cms.engity.ArticleEntity;


public class TaggingTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String var = "articles";
	
	private int taggableId;
	
	private String tagableType = "Article";
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public int getTaggableId() {
		return taggableId;
	}

	public void setTaggableId(int taggableId) {
		this.taggableId = taggableId;
	}

	public String getTagableType() {
		return tagableType;
	}

	public void setTagableType(String tagableType) {
		this.tagableType = tagableType;
	}

	public int doEndTag() throws JspException {
		pageContext.removeAttribute(this.getVar());
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		IArticleCache articleCache = (IArticleCache) application.getBean("articleCache");
			
		if(this.getTagableType().equals("Article")) {
			List<ArticleEntity> relationArticles = articleCache.getRelationArticles(this.getTaggableId());
			pageContext.setAttribute(this.getVar(), relationArticles);
		}
		reset();
		return EVAL_PAGE;
	}

	private String paramsStr() {
		StringBuffer str = new StringBuffer();
		str.append("var=").append(this.getVar());
		str.append("&taggableId=").append(this.getTaggableId());
		str.append("&tagableType=").append(this.getTagableType());
		return str.toString();
	}

	private void reset() {
		this.setVar("articles");
		this.setTaggableId(0);
		this.setTagableType("Article");
		
	}
	
}
