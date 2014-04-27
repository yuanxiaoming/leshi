package com.ch.leyu.http.cacheservice;

public class ServerDataCache {
	private String url;
	private String serverData;
	private long time;

	public ServerDataCache() {
	}

	public ServerDataCache(String url, String serverData, long time) {
		this.url = url;
		this.serverData = serverData;
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerData() {
		return serverData;
	}

	public void setServerData(String serverData) {
		this.serverData = serverData;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
