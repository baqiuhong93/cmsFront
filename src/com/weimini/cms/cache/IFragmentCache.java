package com.weimini.cms.cache;


import com.weimini.cms.engity.FragmentEntity;
import com.weimini.core.exception.DataBaseException;

public interface IFragmentCache {
	
	public FragmentEntity getFragmentById(int fragmentId) throws DataBaseException;
	
	public FragmentEntity getFragmentByUniqueCode(String uniqueCode) throws DataBaseException;
}
