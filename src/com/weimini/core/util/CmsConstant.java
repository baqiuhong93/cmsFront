package com.weimini.core.util;

import java.util.HashMap;
import java.util.Map;

import com.weimini.core.config.SystemConfig;


public class CmsConstant {
	
	public static String MEMCACHE_NAMESPACE = "app3@:";
	
	public static String HTML_DOMAIN_PATH = SystemConfig.getValue("domain.path");
	
	public static String CMS_ROOT_DIR = SystemConfig.getValue("cms.root.dir");
	
	public static String TOMCAT_LOCAL_URL = SystemConfig.getValue("tomcat.local.url");
	
	public static String IMAGE_DOMAIN = SystemConfig.getValue("image.domain");
	
	public static String HTML_FRAGMENT_FTL_NAME = "/html_fragment_ftl/";
	
	public static String HTML_FRAGMENT_HTML_NAME = "/html_fragment_html/";
	
	public static String ARTICLE_PAGER_SPLIT = "<div style=\"page-break-after: always;\"><span style=\"display: none;\">&nbsp;</span></div>";
	
	public static int HTML_FRAGMENT_LOG_TYPE_NODE = 1;
	
	public static int HTML_FRAGMENT_LOG_TYPE_TEMPLATE = 2;
	
	public static int NODE_STATIC = 1;
	
	public static int ARTICLE_STATIC = 2;
	
	public static int NODE_LIST_CACHE_PAGE_NO_LE = 1;
	
	public static boolean VARNISH_CACHE = false;
	
	public static Map<Integer,String> orderTypes = new HashMap<Integer, String>();
	
	static {
		orderTypes.put(1, "id desc");
		orderTypes.put(2, "updated_at desc");
		orderTypes.put(3, "commend desc, sortrank desc");
		orderTypes.put(4, "click desc");
	}
	
	
	

}
