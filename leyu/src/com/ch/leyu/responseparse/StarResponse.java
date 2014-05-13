
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/**
 * 明星解说&&明星详情
 * 
 * @author L
 */
public class StarResponse {
    private ArrayList<Info> userInfo;

    private ArrayList<Property> videoList;

    public ArrayList<Property> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<Property> videoList) {
        this.videoList = videoList;
    }

    public ArrayList<Info> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<Info> userInfo) {
        this.userInfo = userInfo;
    }
}
