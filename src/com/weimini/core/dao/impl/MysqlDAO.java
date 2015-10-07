package com.weimini.core.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.weimini.core.dao.AbstractBaseDAO;
import com.weimini.core.dto.PagerDTO;


public class MysqlDAO extends AbstractBaseDAO {
	
	private static Log log = LogFactory.getLog(MysqlDAO.class);

	public <T> List<T> queryByPage(String sql, Object[] args, Class<T> className, PagerDTO pagerDto) throws DataAccessException {
		if(sql == null || "".equals(sql)) {
			return null;
		}
		if(pagerDto.isArgsCanSearch()) {
			if(args == null || args.length == 0) {
				return Collections.EMPTY_LIST;
			}
		}
		long count = 0;
		if(pagerDto.isUserPageCount() && pagerDto.getPageCount() > 0) {
			count = pagerDto.getPageCount();
		} else {
			count = this.getJdbcTemplate().queryForLong("select count(*) " + sql.substring(sql.toLowerCase().indexOf(" from ")), args);
		}
		pagerDto.init(count);
		String pageSql = sql;
		if(pagerDto.getOrderBy() != null && !"".equals(pagerDto.getOrderBy())) {
			pageSql = pageSql + " order by " + pagerDto.getOrderBy();
		}
		pageSql = pageSql + " limit " + ((pagerDto.getPageNo()-1)*pagerDto.getPerPage()) + "," + pagerDto.getPerPage();
		return this.getJdbcTemplate().query(pageSql, args, ParameterizedBeanPropertyRowMapper.newInstance(className));
	}

	
	public <T> List<T> queryByPage(String sql, Object[] args, RowMapper<T> rowMapper, PagerDTO pagerDto) throws DataAccessException {
		if(sql == null || "".equals(sql)) {
			return null;
		}
		if(pagerDto.isArgsCanSearch()) {
			if(args == null || args.length == 0) {
				return Collections.EMPTY_LIST;
			}
		}
		long count = 0;
		if(pagerDto.isUserPageCount() && pagerDto.getPageCount() > 0) {
			count = pagerDto.getPageCount();
		} else {
			count = this.getJdbcTemplate().queryForLong("select count(*) "  + sql.substring(sql.toLowerCase().indexOf(" from ")), args);
		}
		pagerDto.init(count);
		String pageSql = sql;
		if(pagerDto.getOrderBy() != null && !"".equals(pagerDto.getOrderBy())) {
			pageSql = pageSql + " order by " + pagerDto.getOrderBy();
		}
		pageSql = pageSql  + " limit " + ((pagerDto.getPageNo()-1)*pagerDto.getPerPage()) + "," + pagerDto.getPerPage();
		return this.getJdbcTemplate().query(pageSql, args, rowMapper);
	}



	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper,
			PagerDTO pagerDto) throws DataAccessException {
		return this.queryByPage(sql, new Object[]{}, rowMapper, pagerDto);
	}



	public <T> List<T> queryByPage(String sql, Class<T> className,
			PagerDTO pagerDto) throws DataAccessException {
		return this.queryByPage(sql, new Object[]{}, className, pagerDto);
	}


	public List<Map<String, Object>> queryMapByPage(String sql, Object[] args, PagerDTO pagerDto) throws DataAccessException {
		if(sql == null || "".equals(sql)) {
			return null;
		}
		if(pagerDto.isArgsCanSearch()) {
			if(args == null || args.length == 0) {
				return Collections.EMPTY_LIST;
			}
		}
		long count = 0;
		if(pagerDto.isUserPageCount() && pagerDto.getPageCount() > 0) {
			count = pagerDto.getPageCount();
		} else {
			count = this.getJdbcTemplate().queryForLong("select count(*) "  + sql.substring(sql.toLowerCase().indexOf(" from ")), args);
		}
		pagerDto.init(count);
		String pageSql = sql;
		if(pagerDto.getOrderBy() != null && !"".equals(pagerDto.getOrderBy())) {
			pageSql = pageSql + " order by " + pagerDto.getOrderBy();
		}
		pageSql = pageSql + " limit " + ((pagerDto.getPageNo()-1)*pagerDto.getPerPage()) + "," + pagerDto.getPerPage();
		return this.getJdbcTemplate().queryForList(pageSql, args);
	}


	public List<Map<String, Object>> queryMapByPage(String sql, Object[] args,int[] argTypes, PagerDTO pagerDto) throws DataAccessException {
		if(sql == null || "".equals(sql)) {
			return null;
		}
		if(pagerDto.isArgsCanSearch()) {
			if(args == null || args.length == 0) {
				return Collections.EMPTY_LIST;
			}
		}
		long count = 0;
		if(pagerDto.isUserPageCount() && pagerDto.getPageCount() > 0) {
			count = pagerDto.getPageCount();
		} else {
			count = this.getJdbcTemplate().queryForLong("select count(*) " + sql.substring(sql.toLowerCase().indexOf(" from ")), args);
		}
		pagerDto.init(count);
		String pageSql = sql;
		if(pagerDto.getOrderBy() != null && !"".equals(pagerDto.getOrderBy())) {
			pageSql = pageSql + " order by " + pagerDto.getOrderBy();
		}
		pageSql = pageSql + " limit " + ((pagerDto.getPageNo()-1)*pagerDto.getPerPage()) + "," + pagerDto.getPerPage();
		return this.getJdbcTemplate().queryForList(pageSql, args, argTypes);
	}


	public List<Map<String, Object>> queryMapByPage(String sql, PagerDTO pagerDto) throws DataAccessException {
		return this.queryMapByPage(sql, new Object[] {}, pagerDto);
	}


	public KeyHolder updateBackKey(final String sql, final Object[] args)
			throws DataAccessException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				if(args != null && args.length > 0) {
					for(int i=0,len=args.length;i<len;i++) {
						Object obj = args[i];
						if(obj != null && Date.class.equals(obj.getClass())) {
							Date date = (Date) obj;
							if(date != null) {
								ps.setTimestamp(i+1, new java.sql.Timestamp(date.getTime()));
							} else {
								ps.setTimestamp(i+1, null);
							}
						} else {
							ps.setObject(i+1, obj);
						}
					}
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder;
	}

}
