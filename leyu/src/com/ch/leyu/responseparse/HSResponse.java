
package com.ch.leyu.responseparse;

import java.util.ArrayList;

/***
 * 炉石传说数据
 *
 * @author L
 */
public class HSResponse {

    private ArrayList<Property> focus;

    private ArrayList<Property> news;

    private ArrayList<Property> recommend;

    private ArrayList<Property> bigRecommend;

    private ArrayList<Property> hot;

    private ArrayList<Property> saishi ;

    public ArrayList<Property> getSaishi() {
		return saishi;
	}

	public void setSaishi(ArrayList<Property> saishi) {
		this.saishi = saishi;
	}

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
