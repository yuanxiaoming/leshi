package com.ch.leyu.http.cacheimpl;

import android.content.Context;

import com.ch.leyu.http.cacheinterface.HttpCache;
import com.ch.leyu.http.cacheservice.DBOpenHelperManager;
import com.ch.leyu.http.cacheservice.ServerDataCache;
import com.ch.leyu.http.utils.NetUtil;

public class DefaultHttpCache implements HttpCache {
	/**
	 * 缓存过期时间
	 */
	private static final long SG_CACHE_TIME_OUT = 60 * 1000 * 60 * 24;
	private static final long WIFI_CACHE_TIME_OUT = 60 * 1000 * 60 * 2;
	private DBOpenHelperManager manager;
	private Context context;

	public DefaultHttpCache(Context context) {
		this.context = context;
		manager = DBOpenHelperManager.getInstance(context);
	}

	@Override
	public boolean getHttpCache(String url) {
		ServerDataCache cache = manager.findCacheByUrl(url);
		if (cache != null) {

			if (isExpried()) {
				return true;
			}

			if (!NetUtil.isNetworkAvalible(context)) {
				return true;
			}

			if (NetUtil.isWifi(context)) {
				long cacheTimeMillis = cache.getTime();
				long currentTimeMillis = System.currentTimeMillis();
				if ((currentTimeMillis - cacheTimeMillis) < wifiCacheExpriedTime()) {
					return true;
				}
			}

			if (NetUtil.is3G(context)) {
				long cacheTimeMillis = cache.getTime();
				long currentTimeMillis = System.currentTimeMillis();
				if ((currentTimeMillis - cacheTimeMillis) < sgCacheExpriedTime()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void putHttpCache(ServerDataCache cache) {
		manager.insertOrUpdateCache(cache);
	}

	@Override
	public long wifiCacheExpriedTime() {
		return WIFI_CACHE_TIME_OUT;
	}

	@Override
	public long sgCacheExpriedTime() {
		return SG_CACHE_TIME_OUT;
	}

	@Override
	public boolean isExpried() {
		return false;
	}

}
