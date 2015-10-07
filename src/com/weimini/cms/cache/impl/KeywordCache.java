package com.weimini.cms.cache.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IKeywordCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.KeywordEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("keywordCache")
public class KeywordCache extends MemcachedManager implements IKeywordCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public List<KeywordEntity> queryAllKeywords() throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "keywords";
		List<KeywordEntity> keywords = (List<KeywordEntity>) client.get(key);
		if(keywords == null) {
			keywords = publicDAO.queryAllKeywords();
			if(keywords != null) {
				client.set(key, 0, keywords);
			}
		}
		return keywords;
	}

}
