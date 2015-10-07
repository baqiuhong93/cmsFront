package com.weimini.cms.cache.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IHtmlTemplateCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.HtmlTemplateEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("htmlTemplateCache")
public class HtmlTemplateCache extends MemcachedManager implements IHtmlTemplateCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public HtmlTemplateEntity getHtmlTemplateById(int htmlTemplateId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "html_template_" + htmlTemplateId;
		HtmlTemplateEntity htmlTemplateEntity = (HtmlTemplateEntity) client.get(key);
		htmlTemplateEntity = null;
		if(htmlTemplateEntity == null) {
			htmlTemplateEntity = publicDAO.getHtmlTemplateById(htmlTemplateId);
			if(htmlTemplateEntity != null) {
				File file = new File(CmsConstant.CMS_ROOT_DIR + htmlTemplateEntity.getTemplateUrl());
				try {
					if(file.exists()) {
						htmlTemplateEntity.setContent(FileUtils.readFileToString(file, "utf-8"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				client.set(key, 0, htmlTemplateEntity);
			}
		}
		return htmlTemplateEntity;
	}

}
