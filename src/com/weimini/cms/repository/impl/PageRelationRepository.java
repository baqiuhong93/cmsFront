package com.weimini.cms.repository.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.weimini.cms.engity.PageRelationEntity;
import com.weimini.cms.repository.IPageRelationRepository;
import com.weimini.core.exception.MongodbAccessException;
import com.weimini.core.mongodb.impl.AbstractBaseRepository;


@Repository
public class PageRelationRepository extends AbstractBaseRepository implements IPageRelationRepository {

	@Override
	public void createOrUpdatePageRelation(int nodeId, int type, Set<Integer> ids) throws MongodbAccessException {
		Query query = new Query();
		query.addCriteria(Criteria.where("nodeId").is(nodeId));
		PageRelationEntity pageRelation = this.findOne(query, PageRelationEntity.class);
		if(pageRelation == null) {
			pageRelation = new PageRelationEntity();
			pageRelation.setCreatedAt(new Date());
			pageRelation.setNodeId(nodeId);
			if(type ==1) {
				pageRelation.setNodeIds(new ArrayList<Integer>(ids));
			} else if(type == 2) {
				pageRelation.setFragmentIds(new ArrayList<Integer>(ids));
			}
			this.save(pageRelation);
		} else {
			if(type ==1) {
				List<Integer> dbNodeIds = pageRelation.getNodeIds();
				if(dbNodeIds == null) {
					pageRelation.setNodeIds(new ArrayList<Integer>(ids));
				} else {
					for(Integer id : ids) {
						if(!dbNodeIds.contains(id)) {
							dbNodeIds.add(id);
						}
					}
				}
				this.save(pageRelation);
				
			} else if(type == 2) {
				List<Integer> dbFragmentIds = pageRelation.getFragmentIds();
				if(dbFragmentIds == null) {
					pageRelation.setFragmentIds(new ArrayList<Integer>(ids));
				} else {
					for(Integer id : ids) {
						if(!dbFragmentIds.contains(id)) {
							dbFragmentIds.add(id);
						}
					}
				}
				this.save(pageRelation);
			}
		}
	}


}
