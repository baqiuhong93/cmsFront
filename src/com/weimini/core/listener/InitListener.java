package com.weimini.core.listener;



import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weimini.cms.cache.IArticleCache;
import com.weimini.cms.cache.IKeywordCache;
import com.weimini.cms.cache.INodeCache;
import com.weimini.cms.cache.IDomainCache;
import com.weimini.cms.cache.IFragmentCache;
import com.weimini.cms.cache.IHtmlTemplateCache;
import com.weimini.cms.cache.IOriginCache;
import com.weimini.cms.cache.ITagCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.core.util.CacheSingleInstance;

public class InitListener implements ServletContextListener {
	
	private static Log log = LogFactory.getLog(InitListener.class);

	public void contextInitialized(ServletContextEvent sc) {
		log.info("cms InitListener start!");
		ServletContext servletContext = sc.getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		INodeCache nodeCache = (INodeCache) applicationContext.getBean("nodeCache");
		IDomainCache domainCache = (IDomainCache) applicationContext.getBean("domainCache");
		IFragmentCache fragmentCache = (IFragmentCache) applicationContext.getBean("fragmentCache");
		IOriginCache originCache = (IOriginCache) applicationContext.getBean("originCache");
		IPublicDAO publicDAO  = (IPublicDAO) applicationContext.getBean("publicDAO");
		IArticleCache articleCache = (IArticleCache) applicationContext.getBean("articleCache");
		IHtmlTemplateCache htmlTemplateCache = (IHtmlTemplateCache) applicationContext.getBean("htmlTemplateCache");
		ITagCache tagCache = (ITagCache) applicationContext.getBean("tagCache");
		IKeywordCache keywordCache = (IKeywordCache) applicationContext.getBean("keywordCache");
		
		CacheSingleInstance cacheSingle = CacheSingleInstance.getInstance();
		cacheSingle.setNodeCache(nodeCache);
		cacheSingle.setDomainCache(domainCache);
		cacheSingle.setFragmentCache(fragmentCache);
		cacheSingle.setOriginCache(originCache);
		cacheSingle.setPublicDAO(publicDAO);
		cacheSingle.setArticleCache(articleCache);
		cacheSingle.setHtmlTemplateCache(htmlTemplateCache);
		cacheSingle.setTagCache(tagCache);
		cacheSingle.setKeywordCache(keywordCache);
		
		//init released_num and click
		//nodeCache.initNodeReleasedNumAndClick();

	}
	
	
	public void contextDestroyed(ServletContextEvent sc) {
		log.info("cms InitListener end!");
			
	}


}
