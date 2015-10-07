package com.weimini.core.util;


import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.weimini.core.servlet.FreemarkerServlet;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.IncludePage;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * freemarker工具类
 * @author rails
 *
 */
public class FreemarkerUtil {

    public static final String KEY_REQUEST = "Request";
    public static final String KEY_INCLUDE = "include_page";
    public static final String KEY_REQUEST_PRIVATE = "__FreeMarkerServlet.Request__";
    public static final String KEY_REQUEST_PARAMETERS = "RequestParameters";
    public static final String KEY_SESSION = "Session";
    public static final String KEY_APPLICATION = "Application";
    public static final String KEY_APPLICATION_PRIVATE = "__FreeMarkerServlet.Application__";
    public static final String KEY_JSP_TAGLIBS = "JspTaglibs";

    private static final String ATTR_REQUEST_MODEL = ".freemarker.Request";
    private static final String ATTR_REQUEST_PARAMETERS_MODEL = ".freemarker.RequestParameters";
    private static final String ATTR_SESSION_MODEL = ".freemarker.Session";
    private static final String ATTR_APPLICATION_MODEL = ".freemarker.Application";
    private static final String ATTR_JSP_TAGLIBS_MODEL = ".freemarker.JspTaglibs";

	
	private static Configuration freemarker_cfg = null;
	
	static {
		freemarker_cfg = new Configuration();
		try {
			//freemarker_cfg.setDirectoryForTemplateLoading(new File("C:/Users/bqh/Workspaces/MyEclipse20%for Spring 8.6/test/WebRoot/WEB-INF/templates"));
			try {
				freemarker_cfg.setDirectoryForTemplateLoading(new File(FreemarkerUtil.class.getResource("/").getPath().replace("/classes/", "/templates/").replaceAll("%20", " ")));
				freemarker_cfg.setObjectWrapper(new DefaultObjectWrapper());   
				freemarker_cfg.setSetting(Configuration.CACHE_STORAGE_KEY, "strong:50, soft:500");
				freemarker_cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(50, 500));
				freemarker_cfg.setDefaultEncoding("UTF-8");
				freemarker_cfg.setSetting("number_format", "##########");
			} catch (TemplateException e) {
				e.printStackTrace();
			}  
		} catch (IOException e) {
			e.printStackTrace();
		}   
		

	}
	
	/**
	 * 根据模板和数据渲染页面 
	 * @param templateName 模板路径名称
	 * @param rootParams 数据
	 * @return 渲染后的结果
	 * @throws IOException
	 */
	public static String process(HttpServletRequest request, HttpServletResponse response,String templateName, Object rootParams) throws IOException {
		Template template = freemarker_cfg.getTemplate(templateName);
		if(template != null) {
			Writer out = new StringWriter();
			try {
				if(request != null && response != null) {
					ServletContext servletContext = null;
					HttpSession session = request.getSession(false);
					if(session == null) {
						session = request.getSession(true);
					}
					servletContext = session.getServletContext();
					//System.out.println(servletContext + " ============================");
					
					AllHttpScopesHashModel templateModel = (AllHttpScopesHashModel) createModel(new DefaultObjectWrapper(), servletContext, request, response, session);
					if(rootParams != null) {
						if(rootParams instanceof Map) {
							templateModel.putAll((Map)rootParams);
							template.process(templateModel, out);
							return out.toString();
						} else if(rootParams instanceof Model) {
							Model model = (Model) rootParams;
							templateModel.putAll(model.asMap());
							template.process(templateModel, out);
							return out.toString();
						}
						template.process(rootParams, out);
						return out.toString();
					} else {
						template.process(templateModel, out);
						return out.toString();
					}
				} else {
					template.process(rootParams, out);
					return out.toString();
				}
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			return out.toString();
		} else {
			return "";
		}
	}
	
	public static String process(String templateName, Object rootParams) throws IOException {
		return process(null, null, templateName, rootParams);
	}
	
	public static String process(HttpServletRequest request, HttpServletResponse response, String templateId, String templateContent, Object rootParams) throws IOException, TemplateException {
		Template template = new Template(templateId, new StringReader(templateContent), freemarker_cfg);
		if(template != null) {
			Writer out = new StringWriter();
			if(request != null && response != null) {
				ServletContext servletContext = null;
				HttpSession session = request.getSession(false);
				if(session == null) {
					session = request.getSession(true);
				}
				servletContext = session.getServletContext();
				
				AllHttpScopesHashModel templateModel = (AllHttpScopesHashModel) createModel(new DefaultObjectWrapper(), servletContext, request, response, session);
				if(rootParams != null) {
					if(rootParams instanceof Map) {
						templateModel.putAll((Map)rootParams);
						template.process(templateModel, out);
						return out.toString();
					} else if(rootParams instanceof Model) {
						Model model = (Model) rootParams;
						templateModel.putAll(model.asMap());
						template.process(templateModel, out);
						return out.toString();
					}
					template.process(rootParams, out);
					return out.toString();
				} else {
					template.process(templateModel, out);
					return out.toString();
				}
			} else {
				template.process(rootParams, out);
				return out.toString();
			}
		} else {
			return "";
		}
	}
	
	
	protected static TemplateModel createModel(ObjectWrapper wrapper,ServletContext servletContext, final HttpServletRequest request,final HttpServletResponse response, HttpSession session) throws TemplateModelException {
		GenericServlet servlet = FreemarkerServlet.jspSupportServlet;
		AllHttpScopesHashModel params = new AllHttpScopesHashModel(wrapper,servletContext, request);

		ServletContextHashModel servletContextModel = (ServletContextHashModel) servletContext.getAttribute(ATTR_APPLICATION_MODEL);
		if (servletContextModel == null) {
			servletContextModel = new ServletContextHashModel(servlet, wrapper);
			servletContext.setAttribute(ATTR_APPLICATION_MODEL,servletContextModel);
			TaglibFactory taglibs = new TaglibFactory(servletContext);
			servletContext.setAttribute(ATTR_JSP_TAGLIBS_MODEL, taglibs);
		}
		params.putUnlistedModel(KEY_APPLICATION, servletContextModel);
		params.putUnlistedModel(KEY_APPLICATION_PRIVATE,servletContextModel);
		params.putUnlistedModel(KEY_JSP_TAGLIBS,(TemplateModel) servletContext.getAttribute(ATTR_JSP_TAGLIBS_MODEL));
		
		HttpSessionHashModel sessionModel;
		/**
		HttpSession session = request.getSession(false);
		if (session != null) {
			sessionModel = (HttpSessionHashModel) session.getAttribute(ATTR_SESSION_MODEL);
			if (sessionModel == null) {
				sessionModel = new HttpSessionHashModel(session, wrapper);
				session.setAttribute(ATTR_SESSION_MODEL, sessionModel);
			}
		} else {
			session = request.getSession(true);
			sessionModel = new HttpSessionHashModel(session, wrapper);
			session.setAttribute(ATTR_SESSION_MODEL, sessionModel);
		}
		**/
		
		sessionModel = (HttpSessionHashModel) session.getAttribute(ATTR_SESSION_MODEL);
		if (sessionModel == null) {
			sessionModel = new HttpSessionHashModel(session, wrapper);
			session.setAttribute(ATTR_SESSION_MODEL, sessionModel);
		}
		params.putUnlistedModel(KEY_SESSION, sessionModel);

		HttpRequestHashModel requestModel = (HttpRequestHashModel) request.getAttribute(ATTR_REQUEST_MODEL);
		if (requestModel == null || requestModel.getRequest() != request) {
			requestModel = new HttpRequestHashModel(request, response,wrapper);
			request.setAttribute(ATTR_REQUEST_MODEL, requestModel);
			request.setAttribute(ATTR_REQUEST_PARAMETERS_MODEL,new HttpRequestParametersHashModel(request));
		}
		
		params.putUnlistedModel(KEY_REQUEST, requestModel);
		params.putUnlistedModel(KEY_INCLUDE, new IncludePage(request,response));
		params.putUnlistedModel(KEY_REQUEST_PRIVATE, requestModel);

		HttpRequestParametersHashModel requestParametersModel = (HttpRequestParametersHashModel) request.getAttribute(ATTR_REQUEST_PARAMETERS_MODEL);
		params.putUnlistedModel(KEY_REQUEST_PARAMETERS,requestParametersModel);
		return params;
	}


}
