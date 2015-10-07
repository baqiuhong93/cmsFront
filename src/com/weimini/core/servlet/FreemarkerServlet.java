package com.weimini.core.servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Freemarker Servlet
 * @作者 rails
 * @创建日期 2012-2-29
 * @版本 V 1.0
 *
 */
public class FreemarkerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	public static GenericServlet jspSupportServlet = null;

	@Override
	public void init() throws ServletException {
		super.init();
		jspSupportServlet = this;
	}
	
	

}
