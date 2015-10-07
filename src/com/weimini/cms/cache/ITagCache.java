package com.weimini.cms.cache;


import java.util.List;

import com.weimini.cms.engity.TagEntity;
import com.weimini.core.exception.DataBaseException;

public interface ITagCache {
	
	public TagEntity getTagById(int tagId) throws DataBaseException;
	
	public List<Integer> getTaggableIds(int tagId, String taggableType) throws DataBaseException;
	
}
