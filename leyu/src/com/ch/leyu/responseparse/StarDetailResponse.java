
package com.ch.leyu.responseparse;

/***
 * 明星详情
 * 
 * @author L
 */
public class StarDetailResponse {
    private Info userInfo;

    private VideoListResponse videoList;
    
    private int totalPage ;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
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
