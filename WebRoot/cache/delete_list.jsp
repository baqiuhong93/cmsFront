<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.weimini.cms.cache.IArticleCache"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
String ids = request.getParameter("ids");
if(StringUtils.isNotEmpty(ids)) {
	ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	IArticleCache articleCache = (IArticleCache)applicationContext.getBean("articleCache");
	articleCache.deleteCacheKeys(ids);
}
%>
