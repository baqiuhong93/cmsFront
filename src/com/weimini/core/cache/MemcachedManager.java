package com.weimini.core.cache;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

import com.weimini.core.config.SystemConfig;

public class MemcachedManager {
	
	protected static  MemcachedClient client;
	
	//memcached配置
	private static String MEMCACHED_SETTING = SystemConfig.getValue("memcached.setting");
	
	static {
		try {
			client = new MemcachedClient(new BinaryConnectionFactory(),AddrUtil.getAddresses(MEMCACHED_SETTING));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
