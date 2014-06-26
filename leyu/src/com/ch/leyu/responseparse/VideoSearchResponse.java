
package com.ch.leyu.responseparse;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * 视频搜索结果
 * 
 * @author L
 */
public class VideoSearchResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8850552836020849886L;

    private ArrayList<Property> videoList;

    private int totalPage;
    
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
