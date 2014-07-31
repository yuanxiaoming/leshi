
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/**
 * 我的订阅
 * 
 * @author Administrator
 */
public class SubscriptionResponse {
    private int totalPage;

    private ArrayList<Info> userInfo;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<Info> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<Info> userInfo) {
        this.userInfo = userInfo;
    }
}
