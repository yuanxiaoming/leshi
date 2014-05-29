package com.ch.leyu.http.cacheservice;

public class ServerDataCache {
    private String mUrl;
    private String mServerData;
    private long mTime;

    public ServerDataCache() {
    }

    public ServerDataCache(String url, String serverData, long time) {
        this.mUrl = url;
        this.mServerData = serverData;
        this.mTime = time;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getServerData() {
        return mServerData;
    }

    public void setServerData(String serverData) {
        this.mServerData = serverData;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

}
