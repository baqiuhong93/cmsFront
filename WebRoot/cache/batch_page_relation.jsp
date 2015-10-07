<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@page import="com.weimini.core.util.PageRelationCache"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.weimini.cms.repository.IPageRelationRepository"%>
<%
Map<Integer,Set<Integer>> relationNodes = PageRelationCache.getInstance().getRelationNodes();
Iterator<Map.Entry<Integer,Set<Integer>>> it = relationNodes.entrySet().iterator();
WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
IPageRelationRepository pageRelationRepository = (IPageRelationRepository)webApplicationContext.getBean("pageRelationRepository");
while(it.hasNext()) {
	Map.Entry<Integer,Set<Integer>> entry = (Map.Entry<Integer,Set<Integer>>)it.next();
	int nodeId = entry.getKey();
	Set<Integer> nodeIds = entry.getValue();
	pageRelationRepository.createOrUpdatePageRelation(nodeId, 1, nodeIds);
}
PageRelationCache.getInstance().clearRelationNodes();

Map<Integer,Set<Integer>> relationFragments = PageRelationCache.getInstance().getRelationFragments();
Iterator<Map.Entry<Integer,Set<Integer>>> fragmentIt = relationFragments.entrySet().iterator();
while(fragmentIt.hasNext()) {
	Map.Entry<Integer,Set<Integer>> entry = (Map.Entry<Integer,Set<Integer>>)fragmentIt.next();
	int nodeId = entry.getKey();
	Set<Integer> fragmentIds = entry.getValue();
	pageRelationRepository.createOrUpdatePageRelation(nodeId, 2, fragmentIds);
}
PageRelationCache.getInstance().clearRelationFragments();
%>