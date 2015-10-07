<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="cms" uri="http://www.weimini.com/tags" %>
<cms:list var="${param['var']}" nodeIds="${param['nodeIds']}" excludeIds="${param['excludeIds']}" orderType="${param['orderType']}" pageNo="${param['pageNo']}" perPage="${param['perPage']}" includeIds="${param['includeIds']}"></cms:list>
