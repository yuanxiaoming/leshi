
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/**
 * 明星解说
 * 
 * @author L
 */
public class StarResponse {
    private ArrayList<Info> userInfo;

    private int totalPage;

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public ArrayList<Info> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<Info> userInfo) {
        this.userInfo = userInfo;
    }
}
