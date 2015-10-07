package com.weimini.core.taglib;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weimini.cms.cache.IFriendLinkCache;
import com.weimini.cms.engity.FriendLinkEntity;
import com.weimini.core.util.SystemUtils;

public class FriendLinkTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 1L;

	private String var = "friendLinks";
	
	private String typeIds = "";

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}
	
	public int doStartTag() throws JspException {
		
		pageContext.removeAttribute(this.getVar());
		
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		IFriendLinkCache friendLinkCache = (IFriendLinkCache) application.getBean("friendLinkCache");
		List<FriendLinkEntity> friendLinks = friendLinkCache.queryAllFriendLinks();
		if(StringUtils.isEmpty(this.getTypeIds())) {
			pageContext.setAttribute(this.getVar(), friendLinks);
		} else {
			String[] strTypeIds = typeIds.split(",");
			List<FriendLinkEntity> varFriendLinks = new ArrayList<FriendLinkEntity>();
			for(FriendLinkEntity friendLink : friendLinks) {
				List<Integer> fTypeIdList = friendLink.getTypeIdList();
				for(String strTypeId : strTypeIds) {
					if(fTypeIdList.contains(SystemUtils.strToInt(strTypeId))) {
						varFriendLinks.add(friendLink);
						break;
					}
				}
			}
			pageContext.setAttribute(this.getVar(), varFriendLinks);
		}
		
		//reset
		this.setVar("friendLinks");
		this.setTypeIds("");
		return EVAL_BODY_INCLUDE;
	}
}
