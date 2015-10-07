package com.weimini.cms.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IDomainCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.DomainEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("domainCache")
public class DomainCache extends MemcachedManager implements IDomainCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public DomainEntity getDomainById(int domainId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "domain_" + domainId;
		DomainEntity domainEntity = (DomainEntity) client.get(key);
		if(domainEntity == null) {
			domainEntity = publicDAO.getDomainById(domainId);
			if(domainEntity != null) {
				client.set(key, 0, domainEntity);
			}
		}
		return domainEntity;
	}

}
