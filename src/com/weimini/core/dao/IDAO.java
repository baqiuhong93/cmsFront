package com.weimini.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import com.weimini.core.dto.PagerDTO;

public interface IDAO extends JdbcOperations {
	
	public <T> List<T> queryByPage(String sql, Object[] args, RowMapper<T> rowMapper, PagerDTO pagerDto) throws DataAccessException;
	
	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, PagerDTO pagerDto) throws DataAccessException;
	
	public <T> List<T> queryByPage(String sql, Object[] args, Class<T> className, PagerDTO pagerDto) throws DataAccessException;
	
	public <T> List<T> queryByPage(String sql, Class<T> className, PagerDTO pagerDto) throws DataAccessException;
	
	public List<Map<String,Object>> queryMapByPage(String sql, Object[] args, PagerDTO pagerDto) throws DataAccessException;
	
	public List<Map<String,Object>> queryMapByPage(String sql, Object[] args,int[] argTypes, PagerDTO pagerDto) throws DataAccessException;
	
	public List<Map<String,Object>> queryMapByPage(String sql, PagerDTO pagerDto) throws DataAccessException;
	
	public KeyHolder updateBackKey(final String sql, final Object[] args) throws DataAccessException;
	
	
	
	//public int update(String sql , Object[] args) throws DataAccessException;
	
	
	

}
