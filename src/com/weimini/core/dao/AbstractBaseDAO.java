package com.weimini.core.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public abstract class AbstractBaseDAO implements IDAO {
	
	private static Log log = LogFactory.getLog(AbstractBaseDAO.class);
	
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private SimpleJdbcInsert jdbcInsert;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}



	public void setJdbcInsert(SimpleJdbcInsert jdbcInsert) {
		this.jdbcInsert = jdbcInsert;
	}



	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	public SimpleJdbcInsert getJdbcInsert() {
		return jdbcInsert;
	}
	
	public int[] batchUpdate(String[] sql) throws DataAccessException {
		if(log.isDebugEnabled()) {
			if(sql != null) {
				for(String s : sql) {
					log.debug(s);
				}
			}
		}
		return this.getJdbcTemplate().batchUpdate(sql);
	}

	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().batchUpdate(sql, pss);
	}

	public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	public int[] batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes) {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().batchUpdate(sql, batchArgs, argTypes);
	}

	public <T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize,
			ParameterizedPreparedStatementSetter<T> pss) {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().batchUpdate(sql, batchArgs, batchSize, pss);
	}

	public Map<String, Object> call(CallableStatementCreator csc,
			List<SqlParameter> declaredParameters) throws DataAccessException {
		return this.getJdbcTemplate().call(csc, declaredParameters);
	}

	public <T> T execute(ConnectionCallback<T> action) throws DataAccessException {
		return this.getJdbcTemplate().execute(action);
	}

	public <T> T execute(StatementCallback<T> action) throws DataAccessException {
		return this.getJdbcTemplate().execute(action);
	}

	public void execute(String sql) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		this.getJdbcTemplate().execute(sql);
	}

	public <T> T execute(PreparedStatementCreator psc,
			PreparedStatementCallback<T> action) throws DataAccessException {
		return this.getJdbcTemplate().execute(psc, action);
	}

	public <T> T execute(String sql, PreparedStatementCallback<T> action)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().execute(sql, action);
	}

	public <T> T execute(CallableStatementCreator csc,
			CallableStatementCallback<T> action) throws DataAccessException {
		return this.getJdbcTemplate().execute(csc, action);
	}

	public <T> T execute(String callString, CallableStatementCallback<T> action)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(callString);
		}
		return this.getJdbcTemplate().execute(callString, action);
	}

	public <T> T query(String sql, ResultSetExtractor<T> rse)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, rse);
	}

	public void query(String sql, RowCallbackHandler rch)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		this.getJdbcTemplate().query(sql, rch);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, rowMapper);
	}

	public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse)
			throws DataAccessException {
		return this.getJdbcTemplate().query(psc, rse);
	}

	public void query(PreparedStatementCreator psc, RowCallbackHandler rch)
			throws DataAccessException {
		this.getJdbcTemplate().query(psc, rch);
	}

	public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper)
			throws DataAccessException {
		return this.getJdbcTemplate().query(psc, rowMapper);
	}

	public <T> T query(String sql, PreparedStatementSetter pss,
			ResultSetExtractor<T> rse) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, pss, rse);
	}

	public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, args, rse);
	}

	public <T> T query(String sql, ResultSetExtractor<T> rse, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, rse, args);
	}

	public void query(String sql, PreparedStatementSetter pss,
			RowCallbackHandler rch) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		this.getJdbcTemplate().query(sql, pss, rch);
	}

	public void query(String sql, Object[] args, RowCallbackHandler rch)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		this.getJdbcTemplate().query(sql, args, rch);
	}

	public void query(String sql, RowCallbackHandler rch, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		this.getJdbcTemplate().query(sql, rch, args);
	}

	public <T> List<T> query(String sql, PreparedStatementSetter pss,
			RowMapper<T> rowMapper) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, pss, rowMapper);
	}

	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, args, rowMapper);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, rowMapper, args);
	}

	public <T> T query(String sql, Object[] args, int[] argTypes,
			ResultSetExtractor<T> rse) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, args, argTypes, rse);
	}

	public void query(String sql, Object[] args, int[] argTypes,
			RowCallbackHandler rch) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		this.getJdbcTemplate().query(sql, args, argTypes, rch);
	}

	public <T> List<T> query(String sql, Object[] args, int[] argTypes,
			RowMapper<T> rowMapper) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().query(sql, args, argTypes, rowMapper);
	}

	public int queryForInt(String sql) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForInt(sql);
	}

	public int queryForInt(String sql, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForInt(sql, args);
	}

	public int queryForInt(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForInt(sql, args, argTypes);
	}

	public List<Map<String, Object>> queryForList(String sql)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForList(sql, elementType);
	}

	public List<Map<String, Object>> queryForList(String sql, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForList(sql, args);
	}

	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForList(sql, args, elementType);
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForList(sql, elementType, args);
	}

	public List<Map<String, Object>> queryForList(String sql, Object[] args,
			int[] argTypes) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForList(sql, args, argTypes);
	}

	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes,
			Class<T> elementType) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForList(sql, args, argTypes, elementType);
	}

	public long queryForLong(String sql) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForLong(sql);
	}

	public long queryForLong(String sql, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForLong(sql, args);
	}

	public long queryForLong(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForLong(sql, args, argTypes);
	}

	public Map<String, Object> queryForMap(String sql)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForMap(sql);
	}

	public Map<String, Object> queryForMap(String sql, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForMap(sql, args);
	}

	public Map<String, Object> queryForMap(String sql, Object[] args,
			int[] argTypes) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForMap(sql, args, argTypes);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, rowMapper);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Class<T> requiredType)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, requiredType);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, args, rowMapper);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, rowMapper, args);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType)
	throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, args, requiredType);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, requiredType, args);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Object[] args, int[] argTypes,
			RowMapper<T> rowMapper) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, args, argTypes, rowMapper);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public <T> T queryForObject(String sql, Object[] args, int[] argTypes,
			Class<T> requiredType) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		try {
			return this.getJdbcTemplate().queryForObject(sql, args, argTypes, requiredType);
		} catch(EmptyResultDataAccessException e) {
		}
		return null;
	}

	public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForRowSet(sql);
	}

	public SqlRowSet queryForRowSet(String sql, Object... args)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForRowSet(sql, args);
	}

	public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().queryForRowSet(sql, args, argTypes);
	}

	public int update(String sql) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().update(sql);
	}

	public int update(PreparedStatementCreator psc) throws DataAccessException {
		return this.getJdbcTemplate().update(psc);
	}

	public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder)
			throws DataAccessException {
		return this.getJdbcTemplate().update(psc, generatedKeyHolder);
	}

	public int update(String sql, PreparedStatementSetter pss)
			throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().update(sql, pss);
	}

	public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().update(sql, args, argTypes);
	}

	public int update(String sql, Object... args) throws DataAccessException {
		if(log.isDebugEnabled()) {
			log.debug(sql);
		}
		return this.getJdbcTemplate().update(sql, args);
	}

}
