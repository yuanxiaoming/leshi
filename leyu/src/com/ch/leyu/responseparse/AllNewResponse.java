
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/***
 * 新闻
 * 
 * @author Administrator
 */
public class AllNewResponse {
    private ArrayList<Property> focus;

    private ArrayList<Property> newsList;

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
