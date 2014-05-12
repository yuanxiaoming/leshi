
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/***
 * 炉石传说数据
 * 
 * @author L
 */
public class HSDataResponse {

    private ArrayList<Property> focus;

    private ArrayList<Property> news;

    private ArrayList<Property> recommend;

    private ArrayList<Property> bigRecommend;

    private ArrayList<Property> hot;

    public ArrayList<Property> getFocus() {
        return focus;
    }

    public void setFocus(ArrayList<Property> focus) {
        this.focus = focus;
    }

    public ArrayList<Property> getNews() {
        return news;
    }

    public void setNews(ArrayList<Property> news) {
        this.news = news;
    }

    public ArrayList<Property> getRecommend() {
        return recommend;
    }

    public void setRecommend(ArrayList<Property> recommend) {
        this.recommend = recommend;
    }

    public ArrayList<Property> getBigRecommend() {
        return bigRecommend;
    }

    public void setBigRecommend(ArrayList<Property> bigRecommend) {
        this.bigRecommend = bigRecommend;
    }

    public ArrayList<Property> getHot() {
        return hot;
    }

    public void setHot(ArrayList<Property> hot) {
        this.hot = hot;
    }

}
