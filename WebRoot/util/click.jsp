<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"  trimDirectiveWhitespaces="true"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.weimini.cms.cache.INodeCache"%>
<%@page import="com.weimini.cms.cache.IArticleCache"%>
<%@page import="com.weimini.core.util.SystemUtils"%>
<%
String type = request.getParameter("type");
String id = request.getParameter("id");
String ajax = request.getParameter("ajax");
if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(id)) {
ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());

	if("1".equals(type)) {
		if("1".equals(ajax)) {
		%>
		$.get("/util/incr_node_click.jsp", {"id" : <%=id %>});
		<%
		} else {
			INodeCache nodeCache = (INodeCache)applicationContext.getBean("nodeCache");
			nodeCache.incrNodeClick(SystemUtils.strToInt(id));	
		}
	} else if("2".equals(type)) {
		if("1".equals(ajax)) {
		%>
		$.get("/util/incr_article_click.jsp", {"id" : <%=id %>});
		<%
		} else {
			IArticleCache articleCache = (IArticleCache)applicationContext.getBean("articleCache");
			articleCache.incrArticleClick(SystemUtils.strToInt(id));
		}
	}
}
%>
