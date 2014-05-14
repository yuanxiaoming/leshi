
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/***
 * 视频列表
 * 
 * @author L
 */
public class VideoListResponse {
    private ArrayList<Property> data;

    private int totalPage;

    public ArrayList<Property> getData() {
        return data;
    }

    public void setData(ArrayList<Property> data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

}
