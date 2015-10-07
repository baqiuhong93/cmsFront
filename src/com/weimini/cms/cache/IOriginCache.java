package com.weimini.cms.cache;


import com.weimini.cms.engity.OriginEntity;
import com.weimini.core.exception.DataBaseException;

public interface IOriginCache {
	
	public OriginEntity getOriginById(int originId) throws DataBaseException;
}
