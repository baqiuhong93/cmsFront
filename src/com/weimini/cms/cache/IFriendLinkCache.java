package com.weimini.cms.cache;

import java.util.List;

import com.weimini.cms.engity.FriendLinkEntity;
import com.weimini.core.exception.DataBaseException;

public interface IFriendLinkCache {
	
	public List<FriendLinkEntity> queryAllFriendLinks() throws DataBaseException;

}
