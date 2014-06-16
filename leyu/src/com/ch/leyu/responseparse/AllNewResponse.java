
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/***
 * 新闻  && career为炉石传说的属性。
 * 
 * @author Administrator
 */
public class AllNewResponse {
    private ArrayList<Property> focus;

    private ArrayList<Property> newsList;
    
    private ArrayList<Property> career ;
    
    private int totalPage ;
    
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<Property> getCareer() {
        return career;
    }

    public void setCareer(ArrayList<Property> career) {
        this.career = career;
    }

    public ArrayList<Property> getFocus() {
        return focus;
    }

    public void setFocus(ArrayList<Property> focus) {
        this.focus = focus;
    }

    public ArrayList<Property> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<Property> newsList) {
        this.newsList = newsList;
    }
}
