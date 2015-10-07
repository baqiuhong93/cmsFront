package com.weimini.cms.repository;

import java.util.Set;

import com.weimini.core.exception.MongodbAccessException;


public interface IPageRelationRepository {
	
	public void createOrUpdatePageRelation(int nodeId, int type, Set<Integer> ids) throws MongodbAccessException;
	
}
