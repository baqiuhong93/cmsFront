package com.weimini.core.mongodb;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import com.weimini.core.dto.PagerDTO;
import com.weimini.core.exception.MongodbAccessException;

/**
 * mongodb数据访问基类
 * @author rails
 *
 */
public interface IBaseRepository {
	

	public <T> boolean collectionExists(Class<T> entityClass) throws  MongodbAccessException;
	
	public long count(Query query, Class<?> className) throws MongodbAccessException;
	
	public long	count(Query query, String collectionName) throws MongodbAccessException;

	public <T> T execute(Class<?> entityClass, CollectionCallback<T> callback) throws MongodbAccessException;
	
	public <T> T execute(DbCallback<T> action) throws MongodbAccessException;
	
	public <T> T findAndModify(Query query, Update update, Class<T> entityClass)  throws MongodbAccessException;
	
	public <T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass) throws MongodbAccessException;
	
	public <T> T findAndRemove(Query query, Class<T> entityClass) throws MongodbAccessException;
	
	public <T> GeoResults<T> geoNear(NearQuery near, Class<T> entityClass) throws MongodbAccessException;
	
	public String getCollectionName(Class<?> entityClass) throws MongodbAccessException;
	
	public <T> GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy, Class<T> entityClass) throws MongodbAccessException;
	
	public <T> GroupByResults<T> group(String inputCollectionName, GroupBy groupBy, Class<T> entityClass) throws MongodbAccessException;
	
	public IndexOperations	indexOps(Class<?> entityClass) throws MongodbAccessException;
	
	public void insert(Collection<? extends Object> batchToSave,Class<?> className) throws MongodbAccessException;
	
	public void insert(Object objectToSave) throws MongodbAccessException;
	
	public void	insert(Object objectToSave, String collectionName) throws MongodbAccessException;
	
	public void	insertAll(Collection<? extends Object> objectsToSave) throws MongodbAccessException;

	public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass) throws MongodbAccessException;
	
	public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions, Class<T> entityClass) throws MongodbAccessException;
	
	public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, Class<T> entityClass) throws MongodbAccessException;
	
	public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions, Class<T> entityClass) throws MongodbAccessException;
	
	public void	remove(Object object) throws MongodbAccessException;
	
	public <T> void remove(Query query, Class<T> entityClass) throws MongodbAccessException;
	
	public void	remove(Query query, String collectionName)  throws MongodbAccessException;
	
	public <T> T save(T objectToSave) throws MongodbAccessException;
	
	public WriteResult	updateFirst(Query query, Update update, Class<?> entityClass) throws MongodbAccessException;
	
	public WriteResult	updateMulti(Query query, Update update, Class<?> entityClass) throws MongodbAccessException;
	
	public WriteResult	upsert(Query query, Update update, Class<?> entityClass) throws MongodbAccessException;
	
	public <T> List<T> queryByPage(Query query, Class<T> className,PagerDTO pagerDto) throws MongodbAccessException;
	
	public <T> List<T> findAll(Class<T> className) throws MongodbAccessException;
	
	public <T> List<T> find(Query query, Class<T> className) throws MongodbAccessException;
	
	public <T> T findById(Object object,Class<T> className) throws MongodbAccessException;
	
	public <T> T findOne(Query query, Class<T> className) throws MongodbAccessException;
	
	public <T> T findOne(Query query, Class<T> entityClass, String collectionName) throws MongodbAccessException;
	
	
}
