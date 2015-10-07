<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"  trimDirectiveWhitespaces="true"%>
<%@page import="com.weimini.cms.cache.IArticleCache"%>
<%@page import="com.weimini.cms.engity.ArticleEntity"%>
<%@ taglib prefix="cms" uri="http://www.weimini.com/tags" %>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.weimini.core.util.CmsConstant"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.weimini.cms.cache.INodeCache"%>
<%@page import="com.weimini.core.util.SystemUtils"%>
<%@page import="com.weimini.core.dto.PagerDTO"%>
<%@page import="com.weimini.cms.engity.NodeEntity"%>
<%
String id = request.getParameter("id");
String type = request.getParameter("type");
String pageNo = request.getParameter("pageNo");
String template = request.getParameter("template");
if(StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(pageNo)) {
	pageContext.setAttribute("template", template);
	ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	if((CmsConstant.NODE_STATIC+"").equals(type)) {
		int nodeId = SystemUtils.strToInt(id);
		INodeCache nodeCache = (INodeCache)applicationContext.getBean("nodeCache");
		NodeEntity nodeEntity = nodeCache.getNodeById(nodeId);
		if(nodeEntity != null) {
			pageContext.setAttribute("_node_entity_jsp", nodeEntity);
			int releasedNum = nodeCache.getNodeReleasedNum(SystemUtils.strToInt(id));
			if(releasedNum > 0) {
				PagerDTO pagerDto = new PagerDTO();
				pagerDto.setPerPage(nodeEntity.getPerPageSize());
				pagerDto.init(releasedNum);
				pagerDto.setPageNo(SystemUtils.strToLong(pageNo));
				pageContext.setAttribute("_pager_jsp", pagerDto);
				%>
				<cms:page template="${template}" pagerName="_pager_jsp" urlPath="${_node_entity_jsp.htmlPageNoUrl}"/>
				<%		
			}
			
		}
	} else if((CmsConstant.ARTICLE_STATIC+"").equals(type)) {
		int articleId = SystemUtils.strToInt(id);
		IArticleCache articleCache = (IArticleCache)applicationContext.getBean("articleCache");
		ArticleEntity articleEntity = articleCache.getArticleById(articleId);
		if(articleEntity != null) {
			pageContext.setAttribute("_article_entity_jsp", articleEntity);
			int pageSize = articleEntity.getPageSize();
			if(pageSize > 0) {
				PagerDTO pagerDto = new PagerDTO();
				pagerDto.setPerPage(1);
				pagerDto.init(pageSize);
				pagerDto.setPageNo(SystemUtils.strToLong(pageNo));
				pageContext.setAttribute("_pager_jsp", pagerDto);
				%>
				<cms:page template="${template}" pagerName="_pager_jsp" urlPath="${_article_entity_jsp.htmlPageNoUrl}"/>
				<%
			}
		}
	}
}
%>
