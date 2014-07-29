
package com.ch.leyu.responseparse;

import java.io.Serializable;

/**
 * 登录返回--用户信息
 * 
 * @author L
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1442735734173079555L;

    private String loginUid;

    private String nickname;

    private String passStr;

    private String auth;

    private String thumb;

    public String getLoginUid() {
        return loginUid;
    }

    public void setLoginUid(String loginUid) {
        this.loginUid = loginUid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassStr() {
        return passStr;
    }

    public void setPassStr(String passStr) {
        this.passStr = passStr;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

}
