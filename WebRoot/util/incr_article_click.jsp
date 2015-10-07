<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"  trimDirectiveWhitespaces="true"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.weimini.cms.cache.IArticleCache"%>
<%@page import="com.weimini.core.util.SystemUtils"%>
<%
String id = request.getParameter("id");
if(StringUtils.isNotEmpty(id)) {
	ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	IArticleCache articleCache = (IArticleCache)applicationContext.getBean("articleCache");
	articleCache.incrArticleClick(SystemUtils.strToInt(id));
}
%>
