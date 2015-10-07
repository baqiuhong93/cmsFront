package com.weimini.cms.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IOriginCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.OriginEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("originCache")
public class OriginCache extends MemcachedManager implements IOriginCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public OriginEntity getOriginById(int originId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "origin_" + originId;
		OriginEntity originEntity = (OriginEntity) client.get(key);
		if(originEntity == null) {
			originEntity = publicDAO.getOriginById(originId);
			if(originEntity != null) {
				client.set(key, 0, originEntity);
			}
		}
		return originEntity;
	}

}
