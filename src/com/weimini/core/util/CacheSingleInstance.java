package com.weimini.core.util;


import com.weimini.cms.cache.IArticleCache;
import com.weimini.cms.cache.IKeywordCache;
import com.weimini.cms.cache.INodeCache;
import com.weimini.cms.cache.IDomainCache;
import com.weimini.cms.cache.IFragmentCache;
import com.weimini.cms.cache.IHtmlTemplateCache;
import com.weimini.cms.cache.IOriginCache;
import com.weimini.cms.cache.ITagCache;
import com.weimini.cms.dao.IPublicDAO;

public class CacheSingleInstance {
	
	private INodeCache nodeCache;
	
	private IDomainCache domainCache;
	
	private IOriginCache originCache;
	
	private IFragmentCache fragmentCache;
	
	private IPublicDAO publicDAO;
	
	private IArticleCache articleCache;
	
	private IHtmlTemplateCache htmlTemplateCache;
	
	private ITagCache tagCache;
	
	private IKeywordCache keywordCache;
	
	public IKeywordCache getKeywordCache() {
		return keywordCache;
	}

	public void setKeywordCache(IKeywordCache keywordCache) {
		this.keywordCache = keywordCache;
	}

	public ITagCache getTagCache() {
		return tagCache;
	}

	public void setTagCache(ITagCache tagCache) {
		this.tagCache = tagCache;
	}

	public IHtmlTemplateCache getHtmlTemplateCache() {
		return htmlTemplateCache;
	}

	public void setHtmlTemplateCache(IHtmlTemplateCache htmlTemplateCache) {
		this.htmlTemplateCache = htmlTemplateCache;
	}

	public IArticleCache getArticleCache() {
		return articleCache;
	}

	public void setArticleCache(IArticleCache articleCache) {
		this.articleCache = articleCache;
	}

	public IPublicDAO getPublicDAO() {
		return publicDAO;
	}

	public void setPublicDAO(IPublicDAO publicDAO) {
		this.publicDAO = publicDAO;
	}

	public INodeCache getNodeCache() {
		return nodeCache;
	}

	public void setNodeCache(INodeCache nodeCache) {
		this.nodeCache = nodeCache;
	}

	public IDomainCache getDomainCache() {
		return domainCache;
	}

	public void setDomainCache(IDomainCache domainCache) {
		this.domainCache = domainCache;
	}

	public IOriginCache getOriginCache() {
		return originCache;
	}

	public void setOriginCache(IOriginCache originCache) {
		this.originCache = originCache;
	}

	public IFragmentCache getFragmentCache() {
		return fragmentCache;
	}

	public void setFragmentCache(IFragmentCache fragmentCache) {
		this.fragmentCache = fragmentCache;
	}

	private static CacheSingleInstance cacheSingleInstance;
	
	private CacheSingleInstance() {
		
	}
	
	public static synchronized CacheSingleInstance getInstance() {
		if(cacheSingleInstance == null) {
			cacheSingleInstance = new CacheSingleInstance();
		}
		return cacheSingleInstance;
	}

}
