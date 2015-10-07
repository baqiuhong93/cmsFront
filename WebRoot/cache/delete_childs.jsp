<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.weimini.cms.cache.INodeCache"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.weimini.core.util.SystemUtils"%>
<%
String id = request.getParameter("id");
if(StringUtils.isNotEmpty(id)) {
	ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	INodeCache nodeCache = (INodeCache)applicationContext.getBean("nodeCache");
	nodeCache.deleteChildNodes(SystemUtils.strToInt(id));
}

%>