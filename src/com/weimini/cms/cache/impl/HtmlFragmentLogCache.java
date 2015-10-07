package com.weimini.cms.cache.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IHtmlFragmentLogCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.HtmlFragmentLogEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("htmlFragmentLogCache")
public class HtmlFragmentLogCache extends MemcachedManager implements IHtmlFragmentLogCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public HtmlFragmentLogEntity getHtmlFragmentLogById(int htmlFragmentLogId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "html_fragment_log_" + htmlFragmentLogId;
		HtmlFragmentLogEntity htmlFragmentLogEntity = (HtmlFragmentLogEntity) client.get(key);
		if(htmlFragmentLogEntity == null) {
			htmlFragmentLogEntity = publicDAO.getHtmlFragmentLogById(htmlFragmentLogId);
			if(htmlFragmentLogEntity != null) {
				client.set(key, 0, htmlFragmentLogEntity);
			}
		}
		return htmlFragmentLogEntity;
	}

}
