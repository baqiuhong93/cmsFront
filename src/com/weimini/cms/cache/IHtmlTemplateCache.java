package com.weimini.cms.cache;


import com.weimini.cms.engity.HtmlTemplateEntity;
import com.weimini.core.exception.DataBaseException;

public interface IHtmlTemplateCache {
	
	public HtmlTemplateEntity getHtmlTemplateById(int htmlTemplateId) throws DataBaseException;
}
