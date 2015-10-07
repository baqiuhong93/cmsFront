package com.weimini.cms.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IFragmentCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.FragmentEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("fragmentCache")
public class FragmentCache extends MemcachedManager implements IFragmentCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public FragmentEntity getFragmentById(int fragmentId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "fragment_" + fragmentId;
		FragmentEntity fragmentEntity = (FragmentEntity) client.get(key);
		if(fragmentEntity == null) {
			fragmentEntity = publicDAO.getFragmentById(fragmentId);
			if(fragmentEntity != null) {
				client.set(key, 0, fragmentEntity);
			}
		}
		return fragmentEntity;
	}

	@Override
	public FragmentEntity getFragmentByUniqueCode(String uniqueCode) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "fragment_uc_" + uniqueCode;
		FragmentEntity fragmentEntity = (FragmentEntity) client.get(key);
		if(fragmentEntity == null) {
			fragmentEntity = publicDAO.getFragmentByUniqueCode(uniqueCode);
			if(fragmentEntity != null) {
				client.set(key, 0, fragmentEntity);
			}
		}
		return fragmentEntity;
	}

	

}
