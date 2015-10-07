package com.weimini.core.taglib;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weimini.cms.cache.IArticleCache;
import com.weimini.cms.engity.ArticleEntity;
import com.weimini.core.dto.PagerDTO;
import com.weimini.core.util.SystemUtils;

public class ArticlePagerTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private String var = "article";
	
	private String articleId;
	
	/**
	private String tagContent;

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}
	**/

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	/**
	public int doAfterBody() throws JspException {
		 BodyContent  bc  =  getBodyContent();  
		 try {
			 if(bc != null) {
				 tagContent = bc.getString();
				 bodyContent.clear();
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doAfterBody();
	}

	@Override
	public BodyContent getBodyContent() {
		return super.getBodyContent();
	}
	**/

	public int doEndTag() throws JspException {
		pageContext.removeAttribute(this.getVar());
		pageContext.removeAttribute("_page_" + this.getVar());
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		//HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
		
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		IArticleCache articleCache = (IArticleCache) application.getBean("articleCache");
		ArticleEntity articleEntity = articleCache.getArticleById(SystemUtils.strToInt(this.getArticleId()));
		
		/**
		if(StringUtils.isNotEmpty(this.getTagContent()) && articleEntity != null) {
			
			Map<String, Object> rootParams = new HashMap<String, Object>();
			rootParams.put("article", articleEntity);
			String freemarkerContent = "";
			try {
				freemarkerContent = FreemarkerUtil.process(request, response, "article_" +  this.getArticleId() , this.getTagContent().trim(), rootParams);
			} catch( Exception e) {
				freemarkerContent = "系统错误";
				e.printStackTrace();
			}
			try {
				BodyContent bc = getBodyContent();
				bc.getEnclosingWriter().write(freemarkerContent);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		**/
		if(articleEntity != null) {
			String pageNo = request.getParameter("pageNo");
			PagerDTO pagerDto = new PagerDTO();
			pagerDto.setPageNo(SystemUtils.strToLong(pageNo));
			pagerDto.setPerPage(1);
			pagerDto.init(articleEntity.getPageSize());
			pageContext.setAttribute("_page_" + this.getVar(), pagerDto);
		}
		
		pageContext.setAttribute(this.getVar(), articleEntity);
		reset();
		return EVAL_PAGE;
	}
	
	private void reset() {
		this.setVar("article");
		this.setArticleId("");
		
	}
	

}
