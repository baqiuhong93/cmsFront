package com.weimini.cms.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.weimini.cms.dao.IPublicDAO;
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
import com.weimini.core.dao.BaseDAO;
import com.weimini.core.dto.PagerDTO;
import com.weimini.core.exception.DataBaseException;
import com.weimini.core.util.SystemUtils;


@Repository
public class PublicDAO extends BaseDAO implements IPublicDAO {
	
	@Override
	public List<Integer> getNodeSubtreeIds(NodeEntity nodeEntity)throws DataBaseException {
		int id = nodeEntity.getId();
		String cncestry = nodeEntity.getAncestry();
		if(StringUtils.isEmpty(cncestry)) {
			cncestry = id + "";
		} else {
			cncestry = cncestry+"/"+id;
		}
		String sql = "SELECT id FROM `nodes` WHERE released = 1 and (nodes.id = "+id+" or nodes.ancestry like '"+cncestry+"/%' or nodes.ancestry = '"+cncestry+"')";
		return this.queryForList(sql, Integer.class);
	}
	


	@Override
	public List<ArticleEntity> getArticlesByPage(Set<Integer> nodeIds, PagerDTO pagerDto) throws DataBaseException {
		String sql = "select * from articles where released = 1 and status > 0";
		StringBuffer whereSql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if(nodeIds != null && !nodeIds.isEmpty()) {
			Integer[] nodeArray = nodeIds.toArray(new Integer[nodeIds.size()]);
			whereSql.append(" and ( ");
			for(int i=0,len=nodeArray.length;i<len;i++) {
				whereSql.append("ancestry like '%,"+nodeArray[i]+",%'");
				if(i != len-1) {
					whereSql.append(" or ");
				}
			}
			whereSql.append(") ");
		}
		List<ArticleEntity> articles = null;
		articles = this.queryByPage(sql + whereSql.toString(), args.toArray(), new RowMapper<ArticleEntity>() {
			@Override
			public ArticleEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				ArticleEntity article = new ArticleEntity();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setSubTitle(rs.getString("sub_title"));
				article.setShortTitle(rs.getString("short_title"));
				article.setClick(rs.getInt("click"));
				article.setNodeId(rs.getInt("node_id"));
				article.setSubNodeIds(rs.getString("sub_node_ids"));
				article.setSortrank(rs.getInt("sortrank"));
				article.setCommend(rs.getBoolean("commend"));
				article.setUrl(rs.getString("url"));
				article.setOriginId(rs.getInt("origin_id"));
				article.setWriter(rs.getString("writer"));
				article.setImgPath(rs.getString("img_path"));
				article.setReleasedAt(rs.getTimestamp("released_at"));
				article.setDescription(rs.getString("description"));
				article.setPageSize(rs.getInt("page_size"));
				article.setArticleTextId(rs.getInt("article_text_id"));
				article.setCreatedAt(rs.getTimestamp("created_at"));
				article.setUpdatedAt(rs.getTimestamp("updated_at"));
				article.setDomainId(rs.getInt("domain_id"));
				return article;
			}
		}, pagerDto);
		return articles;
	}

	@Override
	public NodeEntity getNodeById(String nodeId) throws DataBaseException {
		NodeEntity categorie = null;
		String sql = "select * from nodes";
		StringBuffer whereSql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(nodeId)) {
		whereSql.append(" and id = ?");
		args.add(SystemUtils.strToInt(nodeId));
		categorie = this.queryForObject(sql + whereSql.toString().replaceFirst("and", "where"), args.toArray(), new RowMapper<NodeEntity>() {
			public NodeEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				NodeEntity node = new NodeEntity();
				node.setId(rs.getInt("id"));
				node.setName(rs.getString("name"));
				node.setpId(rs.getInt("pId"));
				node.setSortrank(rs.getInt("sortrank"));
				node.setKeywords(rs.getString("keywords"));
				node.setDescription(rs.getString("description"));
				node.setNodeType(rs.getInt("node_type"));
				node.setDomainId(rs.getInt("domain_id"));
				node.setUrl(rs.getString("url"));
				node.setPerPageSize(rs.getInt("per_page_size"));
				node.setNav(rs.getBoolean("nav"));
				node.setTempNodeId(rs.getInt("temp_node_id"));
				node.setTempArticleId(rs.getInt("temp_article_id"));
				node.setCreatedAt(rs.getTimestamp("created_at"));
				node.setUpdatedAt(rs.getTimestamp("updated_at"));
				node.setAncestry(rs.getString("ancestry"));
				node.setUniqueCode(rs.getString("unique_code"));
				node.setListOrderId(rs.getInt("list_order_id"));
				node.setClick(rs.getInt("click"));
				node.setStatus(rs.getInt("status"));
				node.setReleased(rs.getBoolean("released"));
				return node;
			}
		});
		}
		return categorie;
	}

	@Override
	public NodeEntity getNodeByUniqueCode(String uniqueCode)
			throws DataBaseException {
		NodeEntity categorie = null;
		String sql = "select * from nodes";
		StringBuffer whereSql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(uniqueCode)) {
		whereSql.append(" and unique_code = ?");
		args.add(uniqueCode);
		categorie = this.queryForObject(sql + whereSql.toString().replaceFirst("and", "where"), args.toArray(), new RowMapper<NodeEntity>() {
			public NodeEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				NodeEntity node = new NodeEntity();
				node.setId(rs.getInt("id"));
				node.setName(rs.getString("name"));
				node.setpId(rs.getInt("pId"));
				node.setSortrank(rs.getInt("sortrank"));
				node.setKeywords(rs.getString("keywords"));
				node.setDescription(rs.getString("description"));
				node.setNodeType(rs.getInt("node_type"));
				node.setDomainId(rs.getInt("domain_id"));
				node.setUrl(rs.getString("url"));
				node.setPerPageSize(rs.getInt("per_page_size"));
				node.setNav(rs.getBoolean("nav"));
				node.setTempNodeId(rs.getInt("temp_node_id"));
				node.setTempArticleId(rs.getInt("temp_article_id"));
				node.setCreatedAt(rs.getTimestamp("created_at"));
				node.setUpdatedAt(rs.getTimestamp("updated_at"));
				node.setAncestry(rs.getString("ancestry"));
				node.setUniqueCode(rs.getString("unique_code"));
				node.setListOrderId(rs.getInt("list_order_id"));
				return node;
			}
		});
		}
		return categorie;
	}

	@Override
	public List<NodeEntity> getNodes(String nodeIds, String uniqueCodes)
			throws DataBaseException {
		List<NodeEntity> nodes = null;
		String sql = "select * from nodes";
		StringBuffer whereSql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(nodeIds)) {
			if(nodeIds.indexOf(",") != -1) {
				whereSql.append(" and id in (" + nodeIds + ")");
			} else {
				whereSql.append(" and id = ?");
				args.add(SystemUtils.strToInt(nodeIds));
			}
		}
		
		if(StringUtils.isNotEmpty(uniqueCodes)) {
			if(uniqueCodes.indexOf(",") != -1) {
				whereSql.append(" and unique_code in (" + nodeIds + ")");
			} else {
				whereSql.append(" and unique_code = ?");
				args.add(uniqueCodes);
			}
		}
		nodes = this.query(sql + whereSql.toString().replaceFirst("and", "where"), args.toArray(), new RowMapper<NodeEntity>() {
			@Override
			public NodeEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				NodeEntity node = new NodeEntity();
				node.setId(rs.getInt("id"));
				node.setName(rs.getString("name"));
				node.setpId(rs.getInt("pId"));
				node.setSortrank(rs.getInt("sortrank"));
				node.setKeywords(rs.getString("keywords"));
				node.setDescription(rs.getString("description"));
				node.setNodeType(rs.getInt("node_type"));
				node.setDomainId(rs.getInt("domain_id"));
				node.setUrl(rs.getString("url"));
				node.setPerPageSize(rs.getInt("per_page_size"));
				node.setNav(rs.getBoolean("nav"));
				node.setTempNodeId(rs.getInt("temp_node_id"));
				node.setTempArticleId(rs.getInt("temp_article_id"));
				node.setCreatedAt(rs.getTimestamp("created_at"));
				node.setUpdatedAt(rs.getTimestamp("updated_at"));
				node.setAncestry(rs.getString("ancestry"));
				node.setUniqueCode(rs.getString("unique_code"));
				node.setListOrderId(rs.getInt("list_order_id"));
				return node;
			}
		});
		return nodes;
	}



	@Override
	public DomainEntity getDomainById(int domainId) throws DataBaseException {
		DomainEntity domainEntity = null;
		String sql = "select * from domains where id = ?";
		
		domainEntity = this.queryForObject(sql, new Object[] { domainId }, new RowMapper<DomainEntity>() {
			public DomainEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				DomainEntity domainEntity = new DomainEntity();
				domainEntity.setId(rs.getInt("id"));
				domainEntity.setAddr(rs.getString("addr"));
				domainEntity.setCreatedAt(rs.getTimestamp("created_at"));
				domainEntity.setUpdatedAt(rs.getTimestamp("updated_at"));
				return domainEntity;
			}
		});
		return domainEntity;
	}



	@Override
	public OriginEntity getOriginById(int originId) throws DataBaseException {
		OriginEntity originEntity = null;
		String sql = "select * from origins where id = ?";
		
		originEntity = this.queryForObject(sql, new Object[] { originId }, new RowMapper<OriginEntity>() {
			public OriginEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				OriginEntity originEntity = new OriginEntity();
				originEntity.setId(rs.getInt("id"));
				originEntity.setName(rs.getString("name"));
				originEntity.setAddr(rs.getString("addr"));
				originEntity.setCreatedAt(rs.getTimestamp("created_at"));
				originEntity.setUpdatedAt(rs.getTimestamp("updated_at"));
				return originEntity;
			}
		});
		return originEntity;
	}



	@Override
	public ArticleEntity getArticleById(int articleId) throws DataBaseException {
		String sql = "select * from articles where id = ?";
		return this.queryForObject(sql, new Object[] { articleId }, new RowMapper<ArticleEntity>() {
			@Override
			public ArticleEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				ArticleEntity article = new ArticleEntity();
				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setSubTitle(rs.getString("sub_title"));
				article.setShortTitle(rs.getString("short_title"));
				article.setClick(rs.getInt("click"));
				article.setNodeId(rs.getInt("node_id"));
				article.setSubNodeIds(rs.getString("sub_node_ids"));
				article.setSortrank(rs.getInt("sortrank"));
				article.setCommend(rs.getBoolean("commend"));
				article.setUrl(rs.getString("url"));
				article.setOriginId(rs.getInt("origin_id"));
				article.setWriter(rs.getString("writer"));
				article.setImgPath(rs.getString("img_path"));
				article.setReleasedAt(rs.getTimestamp("released_at"));
				article.setDescription(rs.getString("description"));
				article.setPageSize(rs.getInt("page_size"));
				article.setArticleTextId(rs.getInt("article_text_id"));
				article.setCreatedAt(rs.getTimestamp("created_at"));
				article.setUpdatedAt(rs.getTimestamp("updated_at"));
				article.setDomainId(rs.getInt("domain_id"));
				article.setStatus(rs.getInt("status"));
				article.setReleased(rs.getBoolean("released"));
				return article;
			}
		});
	}



	@Override
	public ArticleTextEntity getArticleTextById(int articleTextId) throws DataBaseException {
		String sql = "select id,content from article_texts where id = ?";
		return this.queryForObject(sql, new Object[] { articleTextId }, new RowMapper<ArticleTextEntity>() {
			@Override
			public ArticleTextEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				ArticleTextEntity articleText = new ArticleTextEntity();
				articleText.setId(rs.getInt("id"));
				articleText.setContent(rs.getString("content"));
				return articleText;
			}
		});
	}



	@Override
	public FragmentEntity getFragmentById(int fragmentId) throws DataBaseException {
		String sql = "select id,unique_code,content from fragments where id = ?";
		return this.queryForObject(sql, new Object[] { fragmentId }, new RowMapper<FragmentEntity>() {
			@Override
			public FragmentEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				FragmentEntity fragmentEntity = new FragmentEntity();
				fragmentEntity.setId(rs.getInt("id"));
				fragmentEntity.setUniqueCode(rs.getString("unique_code"));
				fragmentEntity.setContent(rs.getString("content"));
				return fragmentEntity;
			}
		});
	}

	@Override
	public FragmentEntity getFragmentByUniqueCode(String uniqueCode) throws DataBaseException {
		String sql = "select id,unique_code,content from fragments where unique_code = ?";
		return this.queryForObject(sql, new Object[] { uniqueCode }, new RowMapper<FragmentEntity>() {
			@Override
			public FragmentEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				FragmentEntity fragmentEntity = new FragmentEntity();
				fragmentEntity.setId(rs.getInt("id"));
				fragmentEntity.setUniqueCode(rs.getString("unique_code"));
				fragmentEntity.setContent(rs.getString("content"));
				return fragmentEntity;
			}
		});
	}

	@Override
	public HtmlFragmentLogEntity insertHtmlFragmentLog(HtmlFragmentLogEntity htmlFragmentLog) throws DataBaseException {
		String sql = "insert into html_fragment_logs(type_id, type, ftl_content, ftl_params, node_ids, created_at, updated_at) values(?, ?, ?, ?, ?, ?, ?)";
		KeyHolder key = this.updateBackKey(sql, new Object[] {htmlFragmentLog.getTypeId(), htmlFragmentLog.getType(), htmlFragmentLog.getFtlContent(), htmlFragmentLog.getFtlParams(), htmlFragmentLog.getNodeIds(), htmlFragmentLog.getCreatedAt(), htmlFragmentLog.getUpdatedAt()});
		if(key != null) {
			htmlFragmentLog.setId(key.getKey().intValue());
		}
		return htmlFragmentLog;
	}



	@Override
	public int getMaxHtmlFragmentLogIdByType(int typeId, int type) throws DataBaseException {
		String sql = "select max(id) from html_fragment_logs where type_id =? and type = ?";
		return this.queryForInt(sql, new Object[] {typeId, type});
	}



	@Override
	public int deleteHtmlFragmentLogs(int typeId, int type) throws DataBaseException {
		String maxIdSql = "select max(created_at) as created_at from html_fragment_logs where type_id =? and type = ?";
		Map<String,Object> maxRecord = this.queryForMap(maxIdSql, new Object[] {typeId, type});
		if(maxRecord != null) {
			String sql = "delete from html_fragment_logs where type_id =? and type = ? and created_at < ?";
			return this.update(sql, new Object[] {typeId, type, (Date)maxRecord.get("created_at")});
		}
		return 0;
	}



	@Override
	public List<String> getHtmlFragmentLogs(int id, int typeId, int type)
			throws DataBaseException {
		String sql = "select ftl_path from html_fragment_logs where type_id =? and type = ? and id <= ?";
		return this.queryForList(sql, String.class, new Object[] {typeId, type, id});
	}



	@Override
	public HtmlTemplateEntity getHtmlTemplateById(int htmlTemplateId) throws DataBaseException {
		String sql = "select id, name, type_id, created_at, updated_at from html_templates where id = ?";
		return this.queryForObject(sql, new Object[] { htmlTemplateId }, new RowMapper<HtmlTemplateEntity>() {
			@Override
			public HtmlTemplateEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				HtmlTemplateEntity htmlTemplateEntity = new HtmlTemplateEntity();
				htmlTemplateEntity.setId(rs.getInt("id"));
				htmlTemplateEntity.setName(rs.getString("name"));
				htmlTemplateEntity.setTypeId(rs.getInt("type_id"));
				htmlTemplateEntity.setCreatedAt(rs.getTimestamp("created_at"));
				htmlTemplateEntity.setUpdatedAt(rs.getTimestamp("updated_at"));
				return htmlTemplateEntity;
			}
		});
	}



	@Override
	public int updateArticleStatus(int articleId) throws DataBaseException {
		String sql = "update articles set status = 2, released = 1 where id = ?";
		int effectRows = this.update(sql, new Object[] {articleId});
		return effectRows;
	}



	@Override
	public int updateNodeStatus(int nodeId) throws DataBaseException {
		String sql = "update nodes set status = 2, released = 1 where id = ?";
		return this.update(sql, new Object[] {nodeId});
	}



	@Override
	public int getNodeReleasedNum(int nodeId) throws DataBaseException {
		String sql = "select count(*) from articles where released = 1 and ancestry like '%,"+nodeId+",%'";
		return this.queryForInt(sql, new Object[] {});
	}



	@Override
	public HtmlFragmentLogEntity getHtmlFragmentLog(int typeId, int type, String ftlParams) throws DataBaseException {
		String sql = "select * from html_fragment_logs where type_id = ? and type = ? and ftl_params = ?";
		return this.queryForObject(sql, new Object[] { typeId, type, ftlParams}, new RowMapper<HtmlFragmentLogEntity>() {
			@Override
			public HtmlFragmentLogEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				HtmlFragmentLogEntity htmlFragmentLogEntity = new HtmlFragmentLogEntity();
				htmlFragmentLogEntity.setId(rs.getInt("id"));
				htmlFragmentLogEntity.setNodeIds(rs.getString("node_ids"));
				htmlFragmentLogEntity.setCreatedAt(rs.getTimestamp("created_at"));
				htmlFragmentLogEntity.setFtlParams(rs.getString("ftl_params"));
				htmlFragmentLogEntity.setType(rs.getInt("type"));
				htmlFragmentLogEntity.setTypeId(rs.getInt("type_id"));
				htmlFragmentLogEntity.setUpdatedAt(rs.getTimestamp("updated_at"));
				htmlFragmentLogEntity.setFtlContent(rs.getString("ftl_content"));
				return htmlFragmentLogEntity;
			}
		});
	}



	@Override
	public HtmlFragmentLogEntity getHtmlFragmentLogById(int id) throws DataBaseException {
		String sql = "select * from html_fragment_logs where id = ?";
		return this.queryForObject(sql, new Object[] { id }, new RowMapper<HtmlFragmentLogEntity>() {
			@Override
			public HtmlFragmentLogEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				HtmlFragmentLogEntity htmlFragmentLogEntity = new HtmlFragmentLogEntity();
				htmlFragmentLogEntity.setId(rs.getInt("id"));
				htmlFragmentLogEntity.setNodeIds(rs.getString("node_ids"));
				htmlFragmentLogEntity.setCreatedAt(rs.getTimestamp("created_at"));
				htmlFragmentLogEntity.setFtlParams(rs.getString("ftl_params"));
				htmlFragmentLogEntity.setType(rs.getInt("type"));
				htmlFragmentLogEntity.setTypeId(rs.getInt("type_id"));
				htmlFragmentLogEntity.setUpdatedAt(rs.getTimestamp("updated_at"));
				htmlFragmentLogEntity.setFtlContent(rs.getString("ftl_content"));
				return htmlFragmentLogEntity;
			}
		});
	}


	
	/**
	@Override
	public List<Integer> getHtmlFragmentLogsByAppendStr(int type,
			String appendStr) throws DataBaseException {
		String sql = "select type_id from html_fragment_logs where type = ? and append_str like '%,"+appendStr+",%'";
		return this.queryForList(sql, new Object[] {type}, Integer.class);
	}
**/

	/**
	public int updateNodeReleasedNum(int nodeId, int releasedNum)throws DataBaseException {
			String sql = "update nodes set released_num = ? where id = ?";
			return this.update(sql, new Object[] {releasedNum, nodeId});
	}
	**/

	/**
	@Override
	public List<Map<String, Object>> queryNodeReleasedNumAndClick()
			throws DataBaseException {
		String sql ="select id,click,released_num from nodes";
		return this.queryForList(sql);
	}
	**/



	@Override
	public int updateArticleClick(int articleId, int click)
			throws DataBaseException {
		String sql = "update articles set click = ? where id = ?";
		return this.update(sql, new Object[] {click, articleId});
	}



	@Override
	public int updateNodeClick(int nodeId, int click) throws DataBaseException {
		String sql = "update nodes set click = ? where id = ?";
		return this.update(sql, new Object[] {click, nodeId});
	}



	@Override
	public List<Integer> queryNodeArticleIds(String nodeId, PagerDTO pagerDto)
			throws DataBaseException {
		String sql = "select id from articles where node_id = ? and released = 1 and status = 2";
		return this.queryByPage(sql, new Object[] {nodeId}, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getInt("id");
			}
		}, pagerDto);
	}



	@Override
	public int updateHtmlFragmentLogById(HtmlFragmentLogEntity htmlFragmentLogEntity) throws DataBaseException {
		String updateSql = "update html_fragment_logs set ftl_content = ?, node_ids = ?, created_at = ?, updated_at = ? where id = ?";
		return this.update(updateSql, new Object[] {htmlFragmentLogEntity.getFtlContent(), htmlFragmentLogEntity.getNodeIds(), htmlFragmentLogEntity.getCreatedAt(), htmlFragmentLogEntity.getUpdatedAt(), htmlFragmentLogEntity.getId()});
	}



	@Override
	public TagEntity getTagById(int tagId) throws DataBaseException {
		String sql = "select * from tags where id = ?";
		return this.queryForObject(sql, new Object[] { tagId }, new RowMapper<TagEntity>() {
			@Override
			public TagEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				TagEntity tagEntity = new TagEntity();
				tagEntity.setId(rs.getInt("id"));
				tagEntity.setName(rs.getString("name"));
				return tagEntity;
			}
		});
	}



	@Override
	public List<Integer> getTags(int taggableId, String taggableType) throws DataBaseException {
		String sql  = "select tag_id from taggings where taggable_id = ? and taggable_type = ?";
		return this.queryForList(sql, new Object[] {taggableId, taggableType}, Integer.class);
	}



	@Override
	public List<Integer> getTaggableIds(int tagId, String taggableType) throws DataBaseException {
		String sql = "select taggable_id from taggings where tag_id = ? and taggable_type = ?";
		return this.queryForList(sql, new Object[] {tagId, taggableType}, Integer.class);
	}



	@Override
	public List<Map<String, Object>> queryHtmlFragmentLogsByNodeId(String nodeId) throws DataBaseException {
		String sql = "select type,ftl_params from html_fragment_logs where node_ids like '%,"+nodeId+",%'";
		return this.queryForList(sql);
	}



	@Override
	public List<Integer> queryArticlesByTemplateId(int templateId) throws DataBaseException {
		String sql = "select id from nodes where temp_article_id = ? and released = 1";
		return this.queryForList(sql, new Object[] {templateId}, Integer.class);
	}



	@Override
	public List<Integer> queryNodesByTemplateId(int templateId) throws DataBaseException {
		String sql = "select id from nodes where temp_node_id = ? and released = 1 and status = 2";
		return this.queryForList(sql, new Object[] {templateId}, Integer.class);
	}
	
	
	public int getNodeClickById(int nodeId) throws DataBaseException {
		String sql = "select click from nodes where id = ?";
		return this.queryForInt(sql, new Object[] {nodeId});
	}
	
	public int getArticleClickById(int articleId) throws DataBaseException {
		String sql = "select click from articles where id = ?";
		return this.queryForInt(sql, new Object[] {articleId});
	}



	@Override
	public List<Integer> queryChildNodes(int nodeId) throws DataBaseException {
		String sql = "select id from nodes where pId = ? and released = 1 order by sortrank desc";
		return this.queryForList(sql, new Object[] {nodeId}, Integer.class);
	}



	@Override
	public List<KeywordEntity> queryAllKeywords() throws DataBaseException {
		String sql = "select id,name,addr from keywords order by level desc";
		return this.query(sql, new Object[] {}, new RowMapper<KeywordEntity>() {
			@Override
			public KeywordEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				KeywordEntity keywordEntity = new KeywordEntity();
				keywordEntity.setId(rs.getInt("id"));
				keywordEntity.setName(rs.getString("name"));
				keywordEntity.setAddr(rs.getString("addr"));
				return keywordEntity;
			}
		});
	}



	@Override
	public List<FriendLinkEntity> queryAllFriendLinks() throws DataBaseException {
		String sql = "select id,name,description,addr,type_ids,img_path from friend_links where status = 1 order by sortrank desc,id desc";
		return this.query(sql, new Object[] {}, new RowMapper<FriendLinkEntity>() {
			@Override
			public FriendLinkEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				FriendLinkEntity friendLinkEntity = new FriendLinkEntity();
				friendLinkEntity.setId(rs.getInt("id"));
				friendLinkEntity.setName(rs.getString("name"));
				friendLinkEntity.setDescription(rs.getString("description"));
				friendLinkEntity.setAddr(rs.getString("addr"));
				friendLinkEntity.setTypeIds(rs.getString("type_ids"));
				friendLinkEntity.setImgPath(rs.getString("img_path"));
				return friendLinkEntity;
			}
		});
	}

}
