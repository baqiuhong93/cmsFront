package com.weimini.core.taglib;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.weimini.core.dto.PagerDTO;


/**
 * 分页标签
 * @作者 qiuhongba
 * @创建日期 2011-8-18
 * @版本 V 1.0
 *
 */
public class PagerTag extends FreemarkerBaseTag {
	
	private static final long serialVersionUID = 1L;

	private String pagerName ="_page";
	
	private String urlPath;

	public String getPagerName() {
		return pagerName;
	}

	public void setPagerName(String pagerName) {
		this.pagerName = pagerName;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	@Override
	public void execute(Map<String, Object> rootMap) {
		if(StringUtils.isNotBlank(this.getTemplate())) {
			PagerDTO pagerDto = null;
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			pagerDto = (PagerDTO) this.pageContext.getAttribute(this.getPagerName());
			if(pagerDto == null) {
			pagerDto = (PagerDTO) request.getAttribute(this.getPagerName());
			}
			String url = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
			if(StringUtils.isNotBlank(url)) {
				url = url.substring(0, url.length()-1) + "action";
			}
			if(StringUtils.isEmpty(url)) {
				url = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping");
			}
			if(StringUtils.isEmpty(url)) {
				url = request.getRequestURI();
			}
			if(pagerDto != null) {
				String preview = request.getParameter("preview");
				
				rootMap.put("request", request);
				rootMap.put("_pager", pagerDto);
				String requestPath = request.getContextPath() + url + "?" + getQueryString(request, pagerDto) + "pageNo=#pageNo";
				if(StringUtils.isEmpty(this.getUrlPath())) {
					this.setUrlPath(requestPath);
				}
				
				if(StringUtils.isNotEmpty(preview)) {
					rootMap.put("_pagerUrl", requestPath);
				} else {
					rootMap.put("_pagerUrl",this.getUrlPath());
				}
			}
		}
		
	}
	
	private String getQueryString(HttpServletRequest request, PagerDTO pagerDto) {
		String pageNo = request.getParameter("pageNo");
		String str = request.getQueryString();
		if(StringUtils.isBlank(pageNo)) {
			if(pagerDto.isUserPageCount() && pagerDto.getPageCount() > 0) {
				return (str == null ? "" : (str.endsWith("&") ? str : (str+"&"))) + "pageCount=" + pagerDto.getPageCount() + "&";
			} else {
				return str == null ? "" : (str.endsWith("&") ? str : (str+"&"));
			}
		}
		StringBuffer querystr = new StringBuffer();
		if(StringUtils.isNotBlank(str)) {
			String[] querystrs = str.split("&");
			if(querystrs != null && querystrs.length > 0) {
				for(String s : querystrs) {
					if(!s.startsWith("pageNo") && !s.startsWith("pageCount")) {
						querystr.append(s).append("&");
					}
				}
			}
		}
		if(pagerDto.isUserPageCount()) {
			querystr.append("pageCount=" + pagerDto.getPageCount()).append("&");
		}
		return querystr.toString();
	}

}
