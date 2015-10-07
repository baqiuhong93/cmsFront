package com.weimini.cms.cache.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IFriendLinkCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.FriendLinkEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("friendLinkCache")
public class FriendLinkCache extends MemcachedManager implements IFriendLinkCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public List<FriendLinkEntity> queryAllFriendLinks() throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "friend_link";
		List<FriendLinkEntity> friendLinks = (List<FriendLinkEntity>) client.get(key);
		if(friendLinks == null) {
			friendLinks = publicDAO.queryAllFriendLinks();
			if(friendLinks == null) {
				friendLinks = new ArrayList<FriendLinkEntity>();
			}
			client.set(key, 0, friendLinks);
		}
		return friendLinks;
	}

	

}
