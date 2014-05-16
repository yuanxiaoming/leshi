
package com.ch.leyu.responseparse;

import java.io.Serializable;

/***
 * 新闻详情&&炉石传说&&搜索--title
 * 
 * @author Administrator
 */
public class Property implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7501646692922101207L;

    private String id;

    private String title;

    private String imageSrc;

    private String linkUrl;

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

}
