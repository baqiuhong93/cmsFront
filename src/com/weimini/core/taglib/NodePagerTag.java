package com.weimini.core.taglib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weimini.cms.cache.IArticleCache;
import com.weimini.cms.cache.INodeCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.ArticleEntity;
import com.weimini.cms.engity.NodeEntity;
import com.weimini.core.dto.PagerDTO;
import com.weimini.core.util.CmsConstant;
import com.weimini.core.util.SystemUtils;
import com.weimini.core.util.PageRelationCache;


public class NodePagerTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private String var = "articles";
	
	/* 栏目id,多个id时间用英文逗号隔开 */
	private String nodeIds = "";
	
	/* 栏目唯一编码,多个唯一编码之间用英文逗号隔开 */
	private String uniqueCodes = "";
	
	/* 页数 */
	private int pageNo = 1;
	
	/* 每页条数 */
	private int perPage = 10;
	
	/* 排序方式 */
	private int orderType = 1;
	
	//private String ftlContent;
	
	/* 排除的栏目id */
	private String excludeIds = "";
	
	/* 包含的栏目id */
	private String includeIds = "";

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getIncludeIds() {
		return includeIds;
	}

	public void setIncludeIds(String includeIds) {
		this.includeIds = includeIds;
	}

	public String getExcludeIds() {
		return excludeIds;
	}

	public void setExcludeIds(String excludeIds) {
		this.excludeIds = excludeIds;
	}

	public String getNodeIds() {
		return nodeIds;
	}

	public void setNodeIds(String nodeIds) {
		this.nodeIds = nodeIds;
	}

	public String getUniqueCodes() {
		return uniqueCodes;
	}

	public void setUniqueCodes(String uniqueCodes) {
		this.uniqueCodes = uniqueCodes;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	
	/**
	@Override
		public int doAfterBody() throws JspException {
			 BodyContent  bc  =  getBodyContent();  
			 try {
				 if(bc != null) {
					 this.ftlContent = bc.getString();
					 bodyContent.clear();
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
			return super.doAfterBody();
		}

		@Override
		public BodyContent getBodyContent() {
			return super.getBodyContent();
		}
	**/
	
	
	@Override
	public int doStartTag() throws JspException {
		
		pageContext.removeAttribute(this.getVar());
		pageContext.removeAttribute("_page_" + this.getVar());
		
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
		INodeCache nodeCache = (INodeCache) application.getBean("nodeCache");
		IArticleCache articleCache = (IArticleCache) application.getBean("articleCache");
		
		int recordRows = 0;
		List<NodeEntity> nodes = null;
		Set<Integer> nodeListIds = new HashSet<Integer>();
		//没有包含和排除
		if(StringUtils.isEmpty(this.getIncludeIds()) && StringUtils.isEmpty(this.getExcludeIds())) {
			
			if(StringUtils.isNotEmpty(this.getUniqueCodes())) {
				nodes = nodeCache.getNodesByUniqueCodes(this.getUniqueCodes());
			}
			
			if(StringUtils.isNotEmpty(this.getNodeIds())) {
				nodes = nodeCache.getNodesByNodeIds(this.getNodeIds());
			}
			
			if(nodes != null) {
				for(NodeEntity node : nodes) {
					nodeListIds.add(node.getId());
					recordRows = recordRows + node.getReleasedNum();
				}
			}
			
		} else {
			
			if(StringUtils.isNotEmpty(this.getUniqueCodes())) {
				nodes = nodeCache.getNodesByUniqueCodes(this.getUniqueCodes());
				if(nodes != null && nodes.size() > 0) {
					for(int i=0,len = nodes.size();i<len;i++) {
						NodeEntity node = nodes.get(i);
						recordRows = recordRows + node.getReleasedNum();
						List<Integer> subNodeIds = nodeCache.getNodeSubtreeIds(node);
						if(subNodeIds != null && subNodeIds.size() > 0) {
							nodeListIds.addAll(subNodeIds);
						}
					}
				}
			}
			
			if(StringUtils.isNotEmpty(this.getNodeIds())) {
				nodes = nodeCache.getNodesByNodeIds(this.getNodeIds());
				if(nodes != null && nodes.size() > 0) {
					for(int i=0,len=nodes.size();i<len;i++) {
						NodeEntity node = nodes.get(i);
						recordRows = recordRows + node.getReleasedNum();
						List<Integer> subNodeIds = nodeCache.getNodeSubtreeIds(node);
						if(subNodeIds != null && subNodeIds.size() > 0) {
							nodeListIds.addAll(subNodeIds);
						}
					}
				}
			}
			
			//判断是频道还是分类
			if(nodeListIds != null) {
				Iterator<Integer> it=nodeListIds.iterator();
				List<Integer> iIds = this.getIncludeIdList();
				List<Integer> eIds = this.getExcludeIdList();
				while(it.hasNext()){
					boolean isRemove = false;
					int nodeId = it.next();
					NodeEntity node = nodeCache.getNodeById(nodeId);
					if(node != null && !node.isList()) {
						it.remove();
						isRemove = true;
					}
					if(!isRemove) {
						if(eIds.contains(nodeId) && !iIds.contains(nodeId)) {
							it.remove();
							int cacheReleasedNum = node.getReleasedNum();
							if(recordRows >= cacheReleasedNum) {
								recordRows = recordRows - cacheReleasedNum;
							}
						}
					}
				}
			}
		}
		
		
		if(nodeListIds != null && nodeListIds.size() > 0) {
			IPublicDAO publicDAO = (IPublicDAO) application.getBean("publicDAO");
			if(this.getPerPage() < 0) {
				this.setPerPage(1);
			}
			PagerDTO pagerDto = new PagerDTO();
			pagerDto.setPageNo(this.getPageNo());
			pagerDto.setUserPageCount(true);
			pagerDto.setPerPage(this.getPerPage());
			
			pagerDto.init(recordRows);
		
			
			String orderCond = CmsConstant.orderTypes.get(this.getOrderType());
			if(StringUtils.isNotEmpty(orderCond)) {
				pagerDto.setOrderBy(orderCond);
			} else {
				pagerDto.setOrderBy(CmsConstant.orderTypes.get(1));
			}
			List<ArticleEntity> articles = null;
			if(this.getPageNo() > CmsConstant.NODE_LIST_CACHE_PAGE_NO_LE) {
				articles = publicDAO.getArticlesByPage(nodeListIds, pagerDto);
			} else {
				articles = articleCache.getArticlesByPage(paramsStr(true), nodeListIds, pagerDto);
			}
			pageContext.setAttribute(this.getVar(), articles);
			pageContext.setAttribute("_page_" + this.getVar(), pagerDto);
			
			if(CmsConstant.VARNISH_CACHE) {
				//记录引用关系
				HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
				String preview = request.getParameter("preview");
				if(StringUtils.isEmpty(preview)) {
					String idStr = request.getParameter("id");
					String typeStr = request.getParameter("type");
					if(StringUtils.isNotEmpty(idStr)) {
						
						if("1".equals(typeStr)) { //频道
							PageRelationCache.getInstance().addNodes(SystemUtils.strToInt(idStr), nodeListIds);
						}
						
						if("2".equals(typeStr) && this.getPageNo() == 1) { //列表
							if(!idStr.equals(this.getNodeIds())) {
								PageRelationCache.getInstance().addNodes(SystemUtils.strToInt(idStr), nodeListIds);
							}
						}
					}
				}
			}
			
			/**
			Map<String,Object> rootParams = new HashMap<String, Object>();
			rootParams.put(this.getVar(), articles);
			
			
			String staticHtml = request.getParameter("staticHtml");
			String typeIdStr = request.getParameter("id");
			String typeStr = request.getParameter("type");
			if(StringUtils.isNotEmpty(staticHtml)) {
				
				if(StringUtils.isNotEmpty(typeIdStr) && StringUtils.isNotEmpty(typeStr)) {
					int type = SystemUtils.strToInt(typeStr);
					int typeId = SystemUtils.strToInt(typeIdStr);
					HtmlFragmentLogEntity htmlFragmentLog = null;
					
					Date nowDate = (Date) pageContext.getAttribute("__tag_date");
					if(nowDate == null) {
						nowDate = new Date();
						pageContext.setAttribute("__tag_date", nowDate);
					}
					
					
					htmlFragmentLog = publicDAO.getHtmlFragmentLog(typeId, type, paramsStr(false));
					if(htmlFragmentLog == null) {
						htmlFragmentLog = new HtmlFragmentLogEntity();
						
						htmlFragmentLog.setCreatedAt(nowDate);
						htmlFragmentLog.setFtlContent(this.getFtlContent());
						htmlFragmentLog.setFtlParams(paramsStr(false));
						htmlFragmentLog.setTypeId(typeId);
						htmlFragmentLog.setType(type);
						htmlFragmentLog.setUpdatedAt(nowDate);
						
						Set<Integer> subNodes = new HashSet<Integer>();
						
						StringBuffer nodeIdsBuffer = new StringBuffer();
						Integer[] nodeArray = nodeListIds.toArray(new Integer[nodeListIds.size()]);
						for(int i=0,len=nodeArray.length;i<len;i++) {
							nodeIdsBuffer.append(nodeArray[i]);
							if(i != len-1) {
								nodeIdsBuffer.append(",");
							}
						}
						List<NodeEntity> _nodes = nodeCache.getNodesByNodeIds(nodeIdsBuffer.toString());
						for(NodeEntity _node : _nodes) {
							subNodes.addAll(nodeCache.getNodeSubtreeIds(_node));
						}
						
						StringBuffer _subNodeStr = new StringBuffer();
						_subNodeStr.append(",");
						Integer[] _subNodes = subNodes.toArray(new Integer[subNodes.size()]);
						for(int i=0,len=_subNodes.length;i<len;i++) {
							_subNodeStr.append(_subNodes[i]);
							_subNodeStr.append(",");
						}
						
						htmlFragmentLog.setNodeIds(_subNodeStr.toString());
						
						publicDAO.insertHtmlFragmentLog(htmlFragmentLog);
					} else {
						htmlFragmentLog.setCreatedAt(nowDate);
						htmlFragmentLog.setUpdatedAt(nowDate);
						htmlFragmentLog.setFtlContent(this.getFtlContent());
						
						Set<Integer> subNodes = new HashSet<Integer>();
						
						StringBuffer nodeIdsBuffer = new StringBuffer();
						Integer[] nodeArray = nodeListIds.toArray(new Integer[nodeListIds.size()]);
						for(int i=0,len=nodeArray.length;i<len;i++) {
							nodeIdsBuffer.append(nodeArray[i]);
							if(i != len-1) {
								nodeIdsBuffer.append(",");
							}
						}
						List<NodeEntity> _nodes = nodeCache.getNodesByNodeIds(nodeIdsBuffer.toString());
						for(NodeEntity _node : _nodes) {
							subNodes.addAll(nodeCache.getNodeSubtreeIds(_node));
						}
						
						StringBuffer _subNodeStr = new StringBuffer();
						_subNodeStr.append(",");
						Integer[] _subNodes = subNodes.toArray(new Integer[subNodes.size()]);
						for(int i=0,len=_subNodes.length;i<len;i++) {
							_subNodeStr.append(_subNodes[i]);
							_subNodeStr.append(",");
						}
						
						htmlFragmentLog.setNodeIds(_subNodeStr.toString());
						
						publicDAO.updateHtmlFragmentLogById(htmlFragmentLog);
						
					}

					
			
				}
				
			}
			**/
		}
		reset();
		
		return EVAL_BODY_INCLUDE;
	}

	private String paramsStr(boolean pageNo) {
		StringBuffer str = new StringBuffer();
		str.append("var=").append(this.getVar());
		str.append("&nodeIds=").append(this.getNodeIds());
		str.append("&perPage=").append(this.getPerPage());
		str.append("&orderType=").append(this.getOrderType());
		str.append("&excludeIds=").append(this.getExcludeIds());
		str.append("&includeIds=").append(this.getIncludeIds());
		if(pageNo) {
			str.append("&pageNo=").append(this.getPageNo());
		}
		return str.toString();
	}
	
	
	private void reset() {
		this.setVar("articles");
		this.setNodeIds("");
		this.setUniqueCodes("");
		this.setPageNo(1);
		this.setPerPage(10);
		this.setOrderType(1);
		this.setExcludeIds("");
	}
	
	private List<Integer> getExcludeIdList() {
		List<Integer> excludeList = new ArrayList<Integer>();
		if(StringUtils.isNotEmpty(this.getExcludeIds())) {
			String[] ids = this.getExcludeIds().split(",");
			if(ids != null) {
				for(String id: ids) {
					int iId = SystemUtils.strToInt(id);
					if(!excludeList.contains(iId)) {
						excludeList.add(iId);
					}
				}
			}
		}
		return excludeList;
	}
	
	private List<Integer> getIncludeIdList() {
		List<Integer> includeList = new ArrayList<Integer>();
		if(StringUtils.isNotEmpty(this.getIncludeIds())) {
			String[] ids = this.getIncludeIds().split(",");
			if(ids != null) {
				for(String id: ids) {
					int iId = SystemUtils.strToInt(id);
					if(!includeList.contains(iId)) {
						includeList.add(iId);
					}
				}
			}
		}
		return includeList;
	}

}
