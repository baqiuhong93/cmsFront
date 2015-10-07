package com.weimini.core.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weimini.cms.cache.IFragmentCache;
import com.weimini.cms.engity.FragmentEntity;
import com.weimini.core.config.SystemConfig;
import com.weimini.core.util.CmsConstant;
import com.weimini.core.util.SystemUtils;
import com.weimini.core.util.PageRelationCache;

public class FragmentTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private int fragmentId;
	
	private int defaultFragmentId;
	
	private String uniqueCode;
	
	private String defaultUniqueCode;

	public int getFragmentId() {
		return fragmentId;
	}

	public void setFragmentId(int fragmentId) {
		this.fragmentId = fragmentId;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public int getDefaultFragmentId() {
		return defaultFragmentId;
	}

	public void setDefaultFragmentId(int defaultFragmentId) {
		this.defaultFragmentId = defaultFragmentId;
	}

	public String getDefaultUniqueCode() {
		return defaultUniqueCode;
	}

	public void setDefaultUniqueCode(String defaultUniqueCode) {
		this.defaultUniqueCode = defaultUniqueCode;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		IFragmentCache fragmentCache = (IFragmentCache) application.getBean("fragmentCache");
		FragmentEntity fragmentEntity = null;
		if(StringUtils.isNotEmpty(this.getUniqueCode())) {
			fragmentEntity = fragmentCache.getFragmentByUniqueCode(this.getUniqueCode());
			if(fragmentEntity == null && StringUtils.isNotEmpty(this.getDefaultUniqueCode())) {
				fragmentEntity = fragmentCache.getFragmentByUniqueCode(this.getDefaultUniqueCode());
			}
		}
		if(this.getFragmentId() > 0) {
			fragmentEntity = fragmentCache.getFragmentById(this.getFragmentId());
			if(fragmentEntity == null && this.getDefaultFragmentId() > 0) {
				fragmentEntity = fragmentCache.getFragmentById(this.getDefaultFragmentId());
			}
		}
		if(fragmentEntity != null) {
			try {
				String preview = request.getParameter("preview");
				if(StringUtils.isNotEmpty(preview)) {
					pageContext.getOut().print("<div style='position:relative;margin:0 auto;'><span style='width:40px;height:22px;display:block;position:absolute;right:0;top-22px;background:#3333FF;color:#fff;text-align:center;z-index:10000;'><a style='color: #F8F8F8;' href='" + SystemConfig.getValue("cms.url") + "/admin/fragments/"+fragmentEntity.getId()+"/edit' target='_edit_fragment'>Edit</a></span>" + fragmentEntity.getContent() + "</div>");
				} else {
					pageContext.getOut().print(fragmentEntity.getContent());
					
					if(CmsConstant.VARNISH_CACHE) {
						String idStr = request.getParameter("id");
						String typeStr = request.getParameter("type");
						if(StringUtils.isNotEmpty(idStr)) {
							
							if("1".equals(typeStr)) { //频道
								PageRelationCache.getInstance().addFragment(SystemUtils.strToInt(idStr), fragmentEntity.getId());
							}
							
							if("2".equals(typeStr)) { //列表
								String pageNo = request.getParameter("pageNo");
								
								if(StringUtils.isNotEmpty(pageNo)) {
									pageNo = "1";
								}
								
								if(SystemUtils.strToInt(pageNo) == 1) {
									PageRelationCache.getInstance().addFragment(SystemUtils.strToInt(idStr), fragmentEntity.getId());
								}
								
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}
	
}
