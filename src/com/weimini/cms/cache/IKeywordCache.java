package com.weimini.cms.cache;

import java.util.List;

import com.weimini.cms.engity.KeywordEntity;
import com.weimini.core.exception.DataBaseException;

public interface IKeywordCache {
	
	public List<KeywordEntity> queryAllKeywords() throws DataBaseException;

}
