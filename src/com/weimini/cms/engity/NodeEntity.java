package com.weimini.cms.engity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.weimini.cms.cache.IDomainCache;
import com.weimini.cms.cache.IHtmlTemplateCache;
import com.weimini.cms.cache.INodeCache;
import com.weimini.core.util.CacheSingleInstance;
import com.weimini.core.util.CmsConstant;
import com.weimini.core.util.SystemUtils;

public class NodeEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	
	private int pId;
	
	private int sortrank;
	
	private String keywords;
	
	private String description;
	
	private int click;
	
	private int nodeType;
	
	private int domainId;
	
	private String url;
	
	private int perPageSize;
	
	private int tempNodeId;
	
	private int tempArticleId;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private String ancestry;
	
	private boolean nav;
	
	private String uniqueCode;
	
	private int listOrderId;
	
	private boolean released;
	
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isReleased() {
		return released;
	}

	public void setReleased(boolean released) {
		this.released = released;
	}

	public int getReleasedNum() {
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		return nodeCache.getNodeReleasedNum(this.getId());
	}
	
	/**
	public int incrNodeReleasedNum() {
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		return nodeCache.incrNodeReleasedNum(this.getId());
	}
	
	public int decrNodeReleasedNum() {
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		return nodeCache.decrNodeReleasedNum(this.getId());
	}
	**/

	public int getId() {
		return id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public int getSortrank() {
		return sortrank;
	}

	public void setSortrank(int sortrank) {
		this.sortrank = sortrank;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getClick() {
		return click;
	}
	
	public int getCacheClick() {
		return CacheSingleInstance.getInstance().getNodeCache().getNodeClick(this.getId());
	}

	public void setClick(int click) {
		this.click = click;
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

	public int getTempNodeId() {
		return tempNodeId;
	}

	public void setTempNodeId(int tempNodeId) {
		this.tempNodeId = tempNodeId;
	}

	public int getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}

	public int getTempArticleId() {
		return tempArticleId;
	}
	
	public HtmlTemplateEntity getTempArticle() {
		IHtmlTemplateCache htmlTemplateCache = CacheSingleInstance.getInstance().getHtmlTemplateCache();
		HtmlTemplateEntity htmlTemplateEntity = htmlTemplateCache.getHtmlTemplateById(this.getTempArticleId());
		return htmlTemplateEntity;
	}
	
	public HtmlTemplateEntity getTempNode() {
		IHtmlTemplateCache htmlTemplateCache = CacheSingleInstance.getInstance().getHtmlTemplateCache();
		HtmlTemplateEntity htmlTemplateEntity = htmlTemplateCache.getHtmlTemplateById(this.getTempNodeId());
		return htmlTemplateEntity;
	}

	public void setTempArticleId(int tempArticleId) {
		this.tempArticleId = tempArticleId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getAncestry() {
		return ancestry;
	}
	
	public List<Integer> getAncestryList() {
		List<Integer> ancestryList = new ArrayList<Integer>();
		ancestryList.add(this.getId());
		String _ancestry = this.getAncestry();
		if(StringUtils.isNotEmpty(_ancestry)) {
			String[] _ancestrys = _ancestry.split("\\/");
			if(_ancestrys != null) {
				for(String _id : _ancestrys) {
					ancestryList.add(SystemUtils.strToInt(_id));
				}
			}
		}
		return ancestryList;
	}
	
	public List<NodeEntity> getAncestryEntity(boolean flag) {
		List<NodeEntity> ancestryList = new ArrayList<NodeEntity>();
		if(flag) {
			ancestryList.add(this);
		}
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		String _ancestry = this.getAncestry();
		if(StringUtils.isNotEmpty(_ancestry)) {
			String[] _ancestrys = _ancestry.split("\\/");
			if(_ancestrys != null) {
				for(String _id : _ancestrys) {
					NodeEntity _nodeEntity = nodeCache.getNodeById(SystemUtils.strToInt(_id));
					if(_nodeEntity != null) {
						ancestryList.add(_nodeEntity);
					}
				}
			}
		}
		return ancestryList;
	}

	public void setAncestry(String ancestry) {
		this.ancestry = ancestry;
	}

	public boolean isNav() {
		return nav;
	}

	public void setNav(boolean nav) {
		this.nav = nav;
	}
	
	public boolean isList() {
		if(this.getNodeType() == 2) {
			return true;
		}
		return false;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public int getListOrderId() {
		return listOrderId;
	}

	public void setListOrderId(int listOrderId) {
		this.listOrderId = listOrderId;
	}
	
	public void deleteSubtreeCache() {
		if(!this.isReleased()) {
			INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
			String _ancestry = this.getAncestry();
			if(StringUtils.isNotEmpty(_ancestry)) {
				String[] _ancestrys = _ancestry.split("\\/");
				if(_ancestrys != null) {
					for(String id : _ancestrys) {
						nodeCache.deleteNodeSubtreeIds(id);
					}
				}
			}
			//nodeCache.deleteNodeById(this.getId()+"");
		}
	}
	
	public int getHtmlFragmentLogType() {
		if(this.getTempNodeId() == 0) {
			return CmsConstant.HTML_FRAGMENT_LOG_TYPE_NODE;
		} else {
			return CmsConstant.HTML_FRAGMENT_LOG_TYPE_TEMPLATE;
		}
	}
	
	public String getLocalStaticUrl() {
		String localUrl = "";
		if(this.getTempNodeId() == 0) {
			localUrl = CmsConstant.TOMCAT_LOCAL_URL + "/nodes/" + this.getDomain().getAddr() + this.getUrl().replaceFirst(".html", ".jsp") + "?staticHtml="+CmsConstant.NODE_STATIC+"&id=" + this.getId() + "&type=" + CmsConstant.HTML_FRAGMENT_LOG_TYPE_NODE;
		} else {
			HtmlTemplateEntity tempNode = this.getTempNode();
			if(tempNode != null) {
				//localUrl = tempArticle.getLocalTemplateUrl() + "?staticHtml=1&id=" + this.getId() + "&type=" + CmsConstant.HTML_FRAGMENT_LOG_TYPE_NODE_TEMPLATE + "&templateId=" + this.getTempNodeId();
				localUrl = tempNode.getLocalTemplateUrl() + "?staticHtml="+CmsConstant.NODE_STATIC+"&id=" + this.getId() + "&type=" + CmsConstant.HTML_FRAGMENT_LOG_TYPE_TEMPLATE;
			}
		}
		return localUrl;
	}
	
	public String getHtmlUrl() {
		return getHtmlPageNoUrl().replaceFirst("#pageNo", "1").replaceFirst("/index.html", "/");
	}
	
	public String getHtmlPageNoUrl() {
		return "http://" + this.getDomain().getAddr() + this.getUrl().replaceFirst("#id", String.valueOf(this.getId()));
	}
	
	public String getLocation() {
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		StringBuffer locationStr = new StringBuffer();
		String ancestry = this.getAncestry();
		if(StringUtils.isNotEmpty(ancestry)) {
			String[] ancestryIds = ancestry.split("\\/");
			if(ancestryIds != null && ancestryIds.length > 0) {
				for(String ancestryId : ancestryIds) {
					NodeEntity cate = nodeCache.getNodeById(SystemUtils.strToInt(ancestryId));
					if(cate != null && cate.isNav()) {
						locationStr.append("<a href=\"").append(cate.getHtmlUrl()).append("\" target=\"_blank\">").append(cate.getName()).append("</a> > ");
					}
				}
			}
		}
		if(this.isNav()) {
			locationStr.append("<a href=\"").append(this.getHtmlUrl()).append("\" target=\"_blank\">").append(this.getName()).append("</a> > ");
		}
		return locationStr.toString();
	}
	
	public String getNodeFileDir() {
		return this.getDomain().getAddr() + this.getUrl().replaceAll("#id", String.valueOf(this.getId())).substring(0, this.getUrl().lastIndexOf("/"));
	}
	
	public DomainEntity getDomain() {
		IDomainCache domainCache = CacheSingleInstance.getInstance().getDomainCache();
		DomainEntity domainEntity = domainCache.getDomainById(this.getDomainId());
		if(domainEntity == null) {
			domainEntity = new DomainEntity();
		}
		return domainEntity;
	}
	
	public List<NodeEntity> getChildNodes() {
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		return nodeCache.queryChildNodes(this.getId());
	}
	
}
