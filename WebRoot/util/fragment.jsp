<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="com.weimini.cms.cache.IFragmentCache"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.weimini.cms.engity.FragmentEntity"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.weimini.core.util.SystemUtils"%>
<%
ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
IFragmentCache fragmentCache = (IFragmentCache)applicationContext.getBean("fragmentCache");
String id = request.getParameter("id");
if(StringUtils.isNotEmpty(id)) {
	FragmentEntity fragmentEntity = null;
	if(id.startsWith(".")) {
		fragmentEntity = fragmentCache.getFragmentByUniqueCode(id.substring(1));
		if(fragmentEntity != null) {
			out.print(fragmentEntity.getContent());
		}
	} else {
		fragmentEntity = fragmentCache.getFragmentById(SystemUtils.strToInt(id));
		if(fragmentEntity != null) {
			out.print(fragmentEntity.getContent());
		}
	}
	
}
%>

