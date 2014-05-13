
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/***
 * LOL数据
 * 
 * @author L
 */
public class LOLResponse {
    private ArrayList<Property> videoList;

    private int totalPage;

    public ArrayList<Property> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<Property> videoList) {
        this.videoList = videoList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

}
