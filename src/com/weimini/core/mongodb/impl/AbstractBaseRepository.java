package com.weimini.core.mongodb.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoOperations;
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
import com.weimini.core.mongodb.IBaseRepository;

/**
 * mongodb数据访问
 * @author rails
 *
 */
public class AbstractBaseRepository implements IBaseRepository {
	
	
	@Autowired
	MongoOperations mongoTemplate;
	
	public <T> List<T> queryByPage(Query query, Class<T> className,PagerDTO pagerDto) throws MongodbAccessException {
		if(query == null) {
			return Collections.EMPTY_LIST;
		}
		if(pagerDto.isArgsCanSearch() && query.getQueryObject().toMap().isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		
		long count = 0;
		if(pagerDto.isUserPageCount() && pagerDto.getPageCount() > 0) {
			count = pagerDto.getPageCount();
		} else {
			count = mongoTemplate.count(query, className);
		}
		pagerDto.init(count);
		if(StringUtils.isNotBlank(pagerDto.getOrderBy())) {
			String orderByTrim = pagerDto.getOrderBy().trim();
			String[] orderFields = orderByTrim.split(",");
			if(orderFields != null && orderFields.length > 0) {
				for(String orderField : orderFields) {
					String[] strs = orderField.split(" ");
					if(strs != null && strs.length == 2) {
						if(StringUtils.isNotBlank(strs[1]) && "desc".equals(strs[1].trim().toLowerCase())) {
							query.sort().on(strs[0], org.springframework.data.mongodb.core.query.Order.DESCENDING);
						} else if(StringUtils.isNotBlank(strs[1]) && "asc".equals(strs[1].trim().toLowerCase())) {
							query.sort().on(strs[0], org.springframework.data.mongodb.core.query.Order.ASCENDING);
						}
					}
				}
			}
		}
		return mongoTemplate.find(query.skip((int) ((pagerDto.getPageNo()-1)*pagerDto.getPerPage())).limit((int) pagerDto.getPerPage()), className);
	}
	
	public <T> List<T> findAll(Class<T> className) throws MongodbAccessException {
		return mongoTemplate.findAll(className);
	}
	
	public <T> T save(T objectToSave) throws MongodbAccessException {
		mongoTemplate.save(objectToSave);
		return objectToSave;
	}
	
	public void insert(Object objectToSave) throws MongodbAccessException{
		mongoTemplate.insert(objectToSave);
	}
	
	public void insert(Collection<? extends Object> batchToSave,Class<?> className) throws MongodbAccessException{
		mongoTemplate.insert(batchToSave, className);
	}

	public <T> T findById(Object object, Class<T> className) throws MongodbAccessException {
		return mongoTemplate.findById(object, className);
	}

	public long count(Query query, Class<?> className) throws MongodbAccessException {
		return mongoTemplate.count(query, className);
	}

	public long	count(Query query, String collectionName) throws MongodbAccessException {
		return mongoTemplate.count(query, collectionName);
	}

	public <T> T findOne(Query query, Class<T> className) throws MongodbAccessException {
		return mongoTemplate.findOne(query, className);
	}

	
	public <T> List<T> find(Query query, Class<T> className) throws MongodbAccessException {
		return mongoTemplate.find(query, className);
	}

	
	public <T> boolean collectionExists(Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.collectionExists(entityClass);
	}

	
	public <T> T execute(Class<?> entityClass, CollectionCallback<T> callback)
			throws MongodbAccessException {
		return mongoTemplate.execute(entityClass, callback);
	}

	
	public <T> T execute(DbCallback<T> action) throws MongodbAccessException {
		return mongoTemplate.execute(action);
	}

	
	public <T> T findAndModify(Query query, Update update, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.findAndModify(query, update, entityClass);
	}

	
	public <T> T findAndModify(Query query, Update update,
			FindAndModifyOptions options, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.findAndModify(query, update, options, entityClass);
	}

	
	public <T> T findAndRemove(Query query, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.findAndRemove(query, entityClass);
	}

	
	public <T> GeoResults<T> geoNear(NearQuery near, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.geoNear(near, entityClass);
	}

	
	public String getCollectionName(Class<?> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.getCollectionName(entityClass);
	}

	
	public <T> GroupByResults<T> group(Criteria criteria,
			String inputCollectionName, GroupBy groupBy, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.group(criteria, inputCollectionName, groupBy, entityClass);
	}

	
	public <T> GroupByResults<T> group(String inputCollectionName,
			GroupBy groupBy, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.group(inputCollectionName, groupBy, entityClass);
	}

	
	public IndexOperations indexOps(Class<?> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.indexOps(entityClass);
	}

	
	public void insertAll(Collection<? extends Object> objectsToSave)
			throws MongodbAccessException {
		mongoTemplate.insertAll(objectsToSave);
	}

	
	public <T> MapReduceResults<T> mapReduce(Query query,
			String inputCollectionName, String mapFunction,
			String reduceFunction, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.mapReduce(query, inputCollectionName, mapFunction, reduceFunction,entityClass);
	}

	
	public <T> MapReduceResults<T> mapReduce(Query query,
			String inputCollectionName, String mapFunction,
			String reduceFunction, MapReduceOptions mapReduceOptions,
			Class<T> entityClass) throws MongodbAccessException {
		return mongoTemplate.mapReduce(query, inputCollectionName, mapFunction, reduceFunction,mapReduceOptions,entityClass);
	}

	
	public <T> MapReduceResults<T> mapReduce(String inputCollectionName,
			String mapFunction, String reduceFunction, Class<T> entityClass)
			throws MongodbAccessException {
		return  mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction,entityClass);
	}

	
	public <T> MapReduceResults<T> mapReduce(String inputCollectionName,
			String mapFunction, String reduceFunction,
			MapReduceOptions mapReduceOptions, Class<T> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction,mapReduceOptions,entityClass);
	}

	
	public void remove(Object object) throws MongodbAccessException {
		mongoTemplate.remove(object);
	}

	public void	remove(Query query, String collectionName)  throws MongodbAccessException {
		mongoTemplate.remove(query,collectionName);
	}
	
	public <T> void remove(Query query, Class<T> entityClass)
			throws MongodbAccessException {
		mongoTemplate.remove(query,entityClass);
	}

	
	public WriteResult updateFirst(Query query, Update update,
			Class<?> entityClass) throws MongodbAccessException {
		return mongoTemplate.updateFirst(query, update, entityClass);
	}

	
	public WriteResult updateMulti(Query query, Update update,
			Class<?> entityClass) throws MongodbAccessException {
		return mongoTemplate.updateMulti(query, update, entityClass);
	}

	
	public WriteResult upsert(Query query, Update update, Class<?> entityClass)
			throws MongodbAccessException {
		return mongoTemplate.updateMulti(query, update, entityClass);
	}

	public void insert(Object objectToSave, String collectionName) throws MongodbAccessException {
		mongoTemplate.insert(objectToSave, collectionName);
	}

	

	public <T> T findOne(Query query, Class<T> entityClass,
			String collectionName) throws MongodbAccessException {
		return mongoTemplate.findOne(query, entityClass, collectionName);
	}
	

}
