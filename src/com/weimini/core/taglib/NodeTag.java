package com.weimini.core.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weimini.cms.cache.INodeCache;
import com.weimini.cms.engity.NodeEntity;

public class NodeTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String var = "node";
	
	private int nodeId;
	
	private String uniqueCode;

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}


	public int doEndTag() throws JspException {
		
		pageContext.removeAttribute(this.getVar());
		
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		INodeCache nodeCache = (INodeCache) application.getBean("nodeCache");
		NodeEntity nodeEntity = null;
		if(this.getNodeId() > 0) {
			nodeEntity = nodeCache.getNodeById(this.getNodeId());
		}
		
		if(StringUtils.isNotEmpty(this.getUniqueCode())) {
			List<NodeEntity> nodes = nodeCache.getNodesByUniqueCodes(this.getUniqueCode());
			if(nodes != null && nodes.size() > 0) {
				nodeEntity =  nodes.get(0);
			}
		}
		
		pageContext.setAttribute(this.getVar(), nodeEntity);
		
		//reset
		this.setNodeId(0);
		this.setUniqueCode("");
		return EVAL_PAGE;
	}
}
