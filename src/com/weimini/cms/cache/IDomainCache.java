package com.weimini.cms.cache;


import com.weimini.cms.engity.DomainEntity;
import com.weimini.core.exception.DataBaseException;

public interface IDomainCache {

	public DomainEntity getDomainById(int domainId) throws DataBaseException;
}
