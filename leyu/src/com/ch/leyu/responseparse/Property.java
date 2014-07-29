
package com.ch.leyu.responseparse;

import java.io.Serializable;

/***
 * 新闻详情&&炉石传说&&搜索--title&&createTime--全部新闻
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

    private String createTime;

    private String cid;

    private String detail;

    // 0新闻。1视频
    private int linkTitle;
    
    private String type ;
    
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(int linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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
