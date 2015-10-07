package com.weimini.cms.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.jdbc.driver.DatabaseError;

import com.weimini.cms.engity.ArticleEntity;
import com.weimini.cms.engity.ArticleTextEntity;
import com.weimini.cms.engity.DomainEntity;
import com.weimini.cms.engity.FragmentEntity;
import com.weimini.cms.engity.FriendLinkEntity;
import com.weimini.cms.engity.HtmlFragmentLogEntity;
import com.weimini.cms.engity.HtmlTemplateEntity;
import com.weimini.cms.engity.KeywordEntity;
import com.weimini.cms.engity.NodeEntity;
import com.weimini.cms.engity.OriginEntity;
import com.weimini.cms.engity.TagEntity;
import com.weimini.core.dto.PagerDTO;
import com.weimini.core.exception.DataBaseException;

public interface IPublicDAO {
	
	public List<NodeEntity> getNodes(String nodeIds, String uniqueCodes) throws DataBaseException;
	
	public List<Integer> queryNodeArticleIds(String nodeId, PagerDTO pagerDto) throws DataBaseException;
	
	public NodeEntity getNodeById(String nodeId) throws DataBaseException;
	
	public int getNodeClickById(int nodeId) throws DataBaseException;
	
	public NodeEntity getNodeByUniqueCode(String uniqueCode) throws DataBaseException;
	
	public List<Integer> getNodeSubtreeIds(NodeEntity nodeEntity) throws DataBaseException;
	
	public List<Integer> queryChildNodes(int nodeId) throws DataBaseException;
	
	public List<ArticleEntity> getArticlesByPage(Set<Integer> nodeIds, PagerDTO pagerDto) throws DataBaseException;
	
	public DomainEntity getDomainById(int domainId) throws DataBaseException;
	
	public OriginEntity getOriginById(int originId) throws DataBaseException;
	
	public ArticleEntity getArticleById(int articleId) throws DataBaseException;
	
	public int getArticleClickById(int articleId) throws DataBaseException;
	
	public List<Integer> getTags(int taggableId, String taggableType) throws DataBaseException;
	
	public TagEntity getTagById(int tagId) throws DataBaseException;
	
	public List<Integer> getTaggableIds(int tagId, String taggableType) throws DataBaseException;
	
	public ArticleTextEntity getArticleTextById(int articleTextId) throws DataBaseException;
	
	public FragmentEntity getFragmentById(int fragmentId) throws DataBaseException;
	
	public FragmentEntity getFragmentByUniqueCode(String uniqueCode) throws DataBaseException;
	
	public HtmlFragmentLogEntity insertHtmlFragmentLog(HtmlFragmentLogEntity htmlFragmentLog) throws DataBaseException;
	
	public int getMaxHtmlFragmentLogIdByType(int typeId, int type) throws DataBaseException;
	
	public List<String> getHtmlFragmentLogs(int id, int typeId, int type) throws DataBaseException;
	
	public HtmlFragmentLogEntity getHtmlFragmentLogById(int id) throws DataBaseException;
	
	public int updateHtmlFragmentLogById(HtmlFragmentLogEntity htmlFragmentLogEntity) throws DataBaseException;
	
	public int deleteHtmlFragmentLogs(int typeId, int type) throws DataBaseException;
	
	public HtmlTemplateEntity getHtmlTemplateById( int htmlTemplateId ) throws DataBaseException;
	
	public int updateNodeStatus(int nodeId) throws DataBaseException;
	
	public int updateArticleStatus(int articleId) throws DataBaseException;
	
	public int getNodeReleasedNum(int nodeId) throws DataBaseException;
	
	public HtmlFragmentLogEntity getHtmlFragmentLog(int typeId, int type, String ftlParams) throws DataBaseException;
	
	public List<Map<String,Object>> queryHtmlFragmentLogsByNodeId(String nodeId) throws DataBaseException;
	
	/**
	public int updateNodeReleasedNum(int nodeId, int releasedNum) throws DataBaseException;
	**/
	public int updateArticleClick(int articleId, int click) throws DataBaseException;
	
	public int updateNodeClick(int nodeId, int click) throws DataBaseException;
	/**
	public List<Map<String,Object>> queryNodeReleasedNumAndClick() throws DataBaseException;
	**/
	
	public List<Integer> queryNodesByTemplateId(int templateId) throws DataBaseException;
	
	public List<Integer> queryArticlesByTemplateId(int templateId) throws DataBaseException;
	
	public List<KeywordEntity> queryAllKeywords() throws DataBaseException;
	
	public List<FriendLinkEntity> queryAllFriendLinks() throws DataBaseException;
}
