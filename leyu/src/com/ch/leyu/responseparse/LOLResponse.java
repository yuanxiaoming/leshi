
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/***
 * LOL视频库数据&&&炉石视频库数据
 * 
 * @author L
 */
public class LOLResponse {
    private ArrayList<Property> videoList;

    private int totalPage;

    private ArrayList<TagResponse> tags;

    public ArrayList<TagResponse> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagResponse> tags) {
        this.tags = tags;
    }

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
