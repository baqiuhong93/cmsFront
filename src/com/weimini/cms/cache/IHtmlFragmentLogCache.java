package com.weimini.cms.cache;


import com.weimini.cms.engity.HtmlFragmentLogEntity;
import com.weimini.core.exception.DataBaseException;

public interface IHtmlFragmentLogCache {
	
	public HtmlFragmentLogEntity getHtmlFragmentLogById(int htmlFragmentLogId) throws DataBaseException;
}
