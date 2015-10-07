package com.weimini.cms.cache.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.ITagCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.TagEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;

@Component("tagCache")
public class TagCache extends MemcachedManager implements ITagCache {
	
	@Autowired
	private IPublicDAO publicDAO;

	@Override
	public TagEntity getTagById(int tagId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "tag_" + tagId;
		TagEntity tagEntity = (TagEntity) client.get(key);
		if(tagEntity == null) {
			tagEntity = publicDAO.getTagById(tagId);
			if(tagEntity != null) {
				client.set(key, 0, tagEntity);
			}
		}
		return tagEntity;
	}
	
	public List<Integer> getTaggableIds(int tagId, String taggableType) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "taggable_" + tagId + "_" + taggableType;
		List<Integer> taggables = (List<Integer>) client.get(key);
		if(taggables == null) {
			taggables = publicDAO.getTaggableIds(tagId, taggableType);
			if(taggables != null) {
				client.set(key, 0, taggables);
			}
		}
		return taggables;
	}
}
