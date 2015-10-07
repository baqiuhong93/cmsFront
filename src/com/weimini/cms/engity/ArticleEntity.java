package com.weimini.cms.engity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.weimini.cms.cache.IArticleCache;
import com.weimini.cms.cache.IDomainCache;
import com.weimini.cms.cache.IKeywordCache;
import com.weimini.cms.cache.INodeCache;
import com.weimini.cms.cache.IOriginCache;
import com.weimini.cms.cache.ITagCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.core.util.CacheSingleInstance;
import com.weimini.core.util.CmsConstant;
import com.weimini.core.util.SystemUtils;

public class ArticleEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String title;
	
	private String subTitle;
	
	private String shortTitle;
	
	private int click;
	
	private int nodeId;
	
	private String subNodeIds;
	
	private int sortrank;
	
	private boolean commend;
	
	private String url;
	
	private int originId;
	
	private String writer;
	
	private String imgPath;
	
	private Date releasedAt;
	
	private String description;
	
	private int pageSize;
	
	private int articleTextId;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private int domainId;
	
	private int status;
	
	private boolean released;

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


	public String[] getTexts() {
		IPublicDAO publicDAO = CacheSingleInstance.getInstance().getPublicDAO();
		ArticleTextEntity _articleTextEntity = null;
		_articleTextEntity = publicDAO.getArticleTextById(this.getArticleTextId());
		if(_articleTextEntity == null) {
			return new String[]{};
		}
		String _content = _articleTextEntity.getContent();
		if(StringUtils.isEmpty(_content)) {
			return new String[]{};
		}
		String[] contents = _articleTextEntity.getContent().split(CmsConstant.ARTICLE_PAGER_SPLIT);
		return contents;
		/**
		ArticleTextEntity _articleTextEntity = null;
		_articleTextEntity = this.getArticleTextEntity();
		if(_articleTextEntity == null) {
			IPublicDAO publicDAO = CacheSingleInstance.getInstance().getPublicDAO();
			_articleTextEntity = publicDAO.getArticleTextById(this.getArticleTextId());
			if(_articleTextEntity == null) {
				_articleTextEntity = new ArticleTextEntity();
			}
			this.setArticleTextEntity(_articleTextEntity);
		}
		String pageNo = "1";//request.getParameter("pageNo");
		String content = "";
		if(StringUtils.isNotEmpty(pageNo) && StringUtils.isNotEmpty(_articleTextEntity.getContent())) {
			int iPageNo = SystemUtils.strToInt(pageNo);
			String[] contents = _articleTextEntity.getContent().split("<div style=\"page-break-after: always;\"><span style=\"display:none\">&nbsp;</span></div>");
			if(contents != null && contents.length > 0 && contents.length <= iPageNo) {
				content = contents[iPageNo-1];
			}
		} else {
			content = _articleTextEntity.getContent().replaceAll("<div style=\"page-break-after: always;\"><span style=\"display:none\">&nbsp;</span></div>", "");
		}
		**/
		//return content;
	}
	
	public String[] getKeywordTexts() {
		String[] contents = getTexts();
		if(contents.length == 0) {
			return contents;
		}
		IKeywordCache keywordCache = CacheSingleInstance.getInstance().getKeywordCache();
		List<String> contentList = new ArrayList<String>();
		for(String content : contents) {
			List<KeywordEntity> keywords = keywordCache.queryAllKeywords();
			if(keywords != null) {
				for(KeywordEntity keyword : keywords) {
					String name = keyword.getName();
					String addr = keyword.getAddr();
					int index = content.indexOf(name);
					if(index != -1) {
						String pre_content = content.substring(0, index);
						String pre_content_lower = pre_content.toLowerCase();
						int start_a_index = pre_content_lower.indexOf("<a>");
						if(start_a_index == -1) {
							content = pre_content + "<a href='"+addr+"' target='_blank' keyword='true'>" + name + "</a>" + content.substring(index+name.length());
						} else {
							int end_a_index = pre_content_lower.indexOf("</a>");
							if(end_a_index != -1 && start_a_index < end_a_index) {
								content = pre_content + "<a href='"+addr+"' target='_blank' keyword='true'>" + name + "</a>" + content.substring(index+name.length());
							}
						}
					}
				}
			}
			contentList.add(content);
		}
		return contentList.toArray(new String[contentList.size()]);
	}
	
	
	public NodeEntity getNode() {
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		NodeEntity nodeEntity = nodeCache.getNodeById(this.getNodeId());
		if(nodeEntity == null) {
			nodeEntity = new NodeEntity();
		}
		return nodeEntity;
	}


	public List<NodeEntity> getSubNodes() {
		List<NodeEntity> _subNodes = new ArrayList<NodeEntity>();
		if(StringUtils.isNotEmpty(this.getSubNodeIds())) {
			INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
			String[] _ids = this.getSubNodeIds().split(",");
			for(String _id : _ids) {
				NodeEntity nodeEntity = nodeCache.getNodeById(SystemUtils.strToInt(_id));
				if(nodeEntity != null) {
					_subNodes.add(nodeEntity);
				}
			}
		}
		return _subNodes;
	}


	public OriginEntity getOrigin() {
		IOriginCache originCache = CacheSingleInstance.getInstance().getOriginCache();
		OriginEntity originEntity = originCache.getOriginById(this.getOriginId());
		if(originEntity == null) {
			originEntity = new OriginEntity();
		}
		return originEntity;
	}


	public DomainEntity getDomain() {
		IDomainCache domainCache = CacheSingleInstance.getInstance().getDomainCache();
		DomainEntity domainEntity = domainCache.getDomainById(this.getDomainId());
		if(domainEntity == null) {
			domainEntity = new DomainEntity();
		}
		return domainEntity;
	}
	
	public List<TagEntity> getTags() {
		IArticleCache articleCache = CacheSingleInstance.getInstance().getArticleCache();
		List<Integer> tagIds = articleCache.getArticleTagIds(this.getId());
		List<TagEntity> tagEntities = new ArrayList<TagEntity>();
		if(tagIds != null) {
			ITagCache tagCache = CacheSingleInstance.getInstance().getTagCache();
			for(int tagId : tagIds) {
				TagEntity tagEntity = tagCache.getTagById(tagId);
				if(tagEntity != null) {
					tagEntities.add(tagEntity);
				}
			}
		}
		return tagEntities;
	}
	
	public List<ArticleEntity> getTagArticles() {
		IArticleCache articleCache = CacheSingleInstance.getInstance().getArticleCache();
		List<ArticleEntity> tagArticles = articleCache.getRelationArticles(this.getId());
		return tagArticles;
	}
	
	public ArticleEntity getPreArticle() {
		
		return null;
	}
	
	public ArticleEntity getNextArticle() {
		
		return null;
	}
	
	public String getLocation() {
		INodeCache nodeCache = CacheSingleInstance.getInstance().getNodeCache();
		NodeEntity nodeEntity = nodeCache.getNodeById(this.getNodeId());
		if(nodeEntity == null) {
			return "";
		}
		StringBuffer locationStr = new StringBuffer();
		String ancestry = nodeEntity.getAncestry();
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
		if(nodeEntity != null && nodeEntity.isNav()) {
			locationStr.append("<a href=\"").append(nodeEntity.getHtmlUrl()).append("\" target=\"_blank\">").append(nodeEntity.getName()).append("</a> > ");
		}
		return locationStr.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getSubNodeIds() {
		return subNodeIds;
	}

	public void setSubNodeIds(String subNodeIds) {
		this.subNodeIds = subNodeIds;
	}

	public int getSortrank() {
		return sortrank;
	}

	public void setSortrank(int sortrank) {
		this.sortrank = sortrank;
	}

	public boolean isCommend() {
		return commend;
	}

	public void setCommend(boolean commend) {
		this.commend = commend;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOriginId() {
		return originId;
	}

	public void setOriginId(int originId) {
		this.originId = originId;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public String getImgUrl() {
		if(StringUtils.isNotEmpty(this.getImgPath())) {
			return CmsConstant.IMAGE_DOMAIN + this.getImgPath();
		} else {
			return CmsConstant.IMAGE_DOMAIN + "/default.jpg";
		}
	}

	public Date getReleasedAt() {
		return releasedAt;
	}

	public void setReleasedAt(Date releasedAt) {
		this.releasedAt = releasedAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getArticleTextId() {
		return articleTextId;
	}

	public void setArticleTextId(int articleTextId) {
		this.articleTextId = articleTextId;
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

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	
	public String getArticleFileDir() {
		return this.getDomain().getAddr() + this.getUrl().substring(0, this.getUrl().lastIndexOf("/"));
	}
	
	public String getHtmlUrl() {
		return getHtmlPageNoUrl().replaceFirst("#pageNo", "1");
	}
	
	public String getHtmlPageNoUrl() {
		return "http://" + this.getDomain().getAddr() + this.getUrl().replaceAll("#id", this.getId()+"");
	}
	
	public String getLocalStaticUrl() {
		HtmlTemplateEntity htmlTemplateEntity = this.getNode().getTempArticle();
		if(htmlTemplateEntity == null) {
			return "";
		} else {
			return CmsConstant.TOMCAT_LOCAL_URL + htmlTemplateEntity.getTemplateUrl() + "?staticHtml="+CmsConstant.ARTICLE_STATIC+"&pageNo=#pageNo&id=" + this.getId() + "&type=" + CmsConstant.HTML_FRAGMENT_LOG_TYPE_TEMPLATE;
		}
	}
	
	public int getCacheClick() {
		return CacheSingleInstance.getInstance().getArticleCache().getArticleClick(this.getId());
	}
	
}
