
package com.ch.leyu.responseparse;

/***
 * 明星详情
 * 
 * @author L
 */
public class StarDetailResponse {
    private Info userInfo;

    private VideoListResponse videoList;
    
    private int subscribeId ;


    public int getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(int subscribeId) {
        this.subscribeId = subscribeId;
    }

    public Info getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Info userInfo) {
        this.userInfo = userInfo;
    }

    public VideoListResponse getVideoList() {
        return videoList;
    }

    public void setVideoList(VideoListResponse videoList) {
        this.videoList = videoList;
    }

}
