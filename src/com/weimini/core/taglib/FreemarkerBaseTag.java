package com.weimini.core.taglib;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.weimini.core.util.FreemarkerUtil;

/**
 * 支持Freemarker模板的基类
 * @作者 qiuhongba
 * @创建日期 2011-8-18
 * @版本 V 1.0
 *
 */
public abstract class FreemarkerBaseTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	/**模板名称全路径*/
	private String template;
	
	/**模板所使用的编码*/
	private String encoding;


	public String getTemplate() {
		return template;
	}


	public void setTemplate(String template) {
		this.template = template;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int doEndTag() throws JspException {
		if(StringUtils.isNotBlank(this.getTemplate())) {
			Map<String,Object> rootMap = new HashMap<String,Object>();
			String data;
			try {
				execute(rootMap);
				String includePagerJsp = (String) rootMap.get("_include_pager_jsp");
				if(StringUtils.isNotEmpty(includePagerJsp)) {
					pageContext.getOut().print(includePagerJsp);
				} else {
					data = FreemarkerUtil.process(this.getTemplate(), rootMap);
					pageContext.getOut().print(data);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}


	public abstract void execute(Map<String,Object> rootMap);

}
