package com.weimini.cms.cache;


import java.util.List;
import java.util.Set;

import com.weimini.cms.engity.ArticleEntity;
import com.weimini.core.dto.PagerDTO;
import com.weimini.core.exception.DataBaseException;

public interface IArticleCache {
	
	public List<ArticleEntity> getArticlesByPage(String paramKey, Set<Integer> nodeIds, PagerDTO pagerDto) throws DataBaseException;
	
	public void deleteArticlesByPage(int nodeId) throws DataBaseException;
	
	public ArticleEntity getArticleById(int articleId) throws DataBaseException;
	
	public int incrArticleClick(int articleId) throws DataBaseException;
	
	public int getArticleClick(int articleId) throws DataBaseException;
	
	public List<Integer> getArticleTagIds(int articleId) throws DataBaseException;
	
	public List<ArticleEntity> getRelationArticles(int articleId) throws DataBaseException;
	
	public void updateArticleStatus(int articleId) throws DataBaseException;
	
	public void deleteCacheKeys(String idstr) throws DataBaseException;
}
