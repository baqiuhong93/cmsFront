package com.weimini.cms.cache.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.IArticleCache;
import com.weimini.cms.cache.INodeCache;
import com.weimini.cms.cache.ITagCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.ArticleEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.dto.PagerDTO;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.CmsConstant;
import com.weimini.core.util.SystemUtils;

@Component("articleCache")
public class ArticleCache extends MemcachedManager implements IArticleCache {
	
	@Autowired
	private IPublicDAO publicDAO;
	
	@Autowired
	private ITagCache tagCache;
	
	@Autowired
	private INodeCache nodeCache;
	
	public List<ArticleEntity> getArticlesByPage(String paramKey, Set<Integer> nodeIds, PagerDTO pagerDto) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE +"articles_" + paramKey;
		List<ArticleEntity> articles = null;
		articles = (List<ArticleEntity>) client.get(key);
		if(articles == null) {
			articles = publicDAO.getArticlesByPage(nodeIds, pagerDto);
			if(articles != null) {
				for(int nodeId : nodeIds) {
					String cacheKey = CmsConstant.MEMCACHE_NAMESPACE +"cachekey_" + nodeId;
					Set<String> cacheKeys = (Set<String>) client.get(cacheKey);
					if(cacheKeys == null) {
						cacheKeys = new HashSet<String>();
					}
					cacheKeys.add(paramKey);
					client.set(cacheKey, 0, cacheKeys);
				}
				client.set(key, 0, articles);
			}
		}
		return articles == null ? Collections.EMPTY_LIST : articles;
	}
	
	public void deleteCacheKeys(String idstr) throws DataBaseException {
		String[] ids = idstr.split(",");
		for(String id : ids) {
			if(StringUtils.isNotEmpty(id)) {
				nodeCache.deleteNodeReleasedNum(SystemUtils.strToInt(id));
				String cacheKey = CmsConstant.MEMCACHE_NAMESPACE +"cachekey_" + id;
				Set<String> cacheKeys = (Set<String>) client.get(cacheKey);
				if(cacheKeys != null) {
					for(String key : cacheKeys) {
						client.delete(CmsConstant.MEMCACHE_NAMESPACE +"articles_" + key);
					}
				}
			}
		}
	}
	
	public void deleteArticlesByPage(int nodeId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE +"articles_";
		List<Map<String,Object>> fragmentLogs = publicDAO.queryHtmlFragmentLogsByNodeId(String.valueOf(nodeId));
		if(fragmentLogs != null) {
			for(Map<String, Object> fragmentLog : fragmentLogs) {
				int type = (Integer) fragmentLog.get("type");
				if(type == CmsConstant.HTML_FRAGMENT_LOG_TYPE_NODE) {
					client.delete(key+(String)fragmentLog.get("ftl_params")+"&pageNo=1");
				} else if (type == CmsConstant.HTML_FRAGMENT_LOG_TYPE_TEMPLATE) {
					for(int i=1;i<=CmsConstant.NODE_LIST_CACHE_PAGE_NO_LE;i++) {
						client.delete(key+(String)fragmentLog.get("ftl_params")+"&pageNo="+i);
					}
				}
			}
		}
	}

	@Override
	public ArticleEntity getArticleById(int articleId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE +"article_" + articleId;
		ArticleEntity articleEntity = (ArticleEntity) client.get(key);
		if(articleEntity == null) {
			articleEntity = publicDAO.getArticleById(articleId);
			if(articleEntity != null) {
				client.set(key, 0, articleEntity);
			}
		}
		return articleEntity;
	}

	@Override
	public int incrArticleClick(int articleId)  throws DataBaseException{
		String key = CmsConstant.MEMCACHE_NAMESPACE + "article_click_" + articleId;
		int click = getArticleClick(articleId) + 1;
		client.set(key, 0, click);
		if(click % 10 == 0) {
			publicDAO.updateArticleClick(articleId, click);
		}
		return click;
	}
	
	public int getArticleClick(int articleId)  throws DataBaseException{
		String key = CmsConstant.MEMCACHE_NAMESPACE + "article_click_" + articleId;
		Integer click = (Integer)client.get(key);
		if(click == null) {
			click = publicDAO.getArticleClickById(articleId);
		}
		return click == null ? 1 : click;
	}
	
	public List<Integer> getArticleTagIds(int articleId)  throws DataBaseException{
		String key = CmsConstant.MEMCACHE_NAMESPACE + "article_tag_ids_" + articleId;
		List<Integer> articleTagIds = null;
		articleTagIds = (List<Integer>) client.get(key);
		if(articleTagIds == null) {
			articleTagIds = publicDAO.getTags(articleId, "Article");
			if(articleTagIds != null) {
				client.set(key, 0, articleTagIds);
			}
		}
		return articleTagIds;
	}

	@Override
	public List<ArticleEntity> getRelationArticles(int articleId)  throws DataBaseException{
		List<Integer> tagIds = getArticleTagIds(articleId);
		List<ArticleEntity> relationArticles = new ArrayList<ArticleEntity>();
		if(tagIds != null) {
			for(int tagId : tagIds) {
				List<Integer> taggableIds = tagCache.getTaggableIds(tagId, "Article");
				if(taggableIds != null) {
					for(int taggableId : taggableIds) {
						if(taggableId != articleId) {
							ArticleEntity articleEntity = getArticleById(taggableId);
							if(articleEntity != null) {
								relationArticles.add(articleEntity);
							}
						}
					}
				}
			}
		}
		return relationArticles;
	}

	@Override
	public void updateArticleStatus(int articleId) throws DataBaseException {
		String key = CmsConstant.MEMCACHE_NAMESPACE +"article_" + articleId;
		ArticleEntity articleEntity = (ArticleEntity) client.get(key);
		if(articleEntity != null) {
			articleEntity.setStatus(2);
			articleEntity.setReleased(true);
			client.set(key, 0, articleEntity);
			publicDAO.updateArticleStatus(articleId);
		}
	}

	

}
