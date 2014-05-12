package com.ch.leyu.http.cacheinterface;

import com.ch.leyu.http.cacheservice.ServerDataCache;

public interface HttpCache {

	public boolean getHttpCache(String url);

	public void putHttpCache(ServerDataCache cache);

	public boolean isNotExpried();

	public long wifiCacheExpriedTime();

	public long sgCacheExpriedTime();

}