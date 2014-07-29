
package com.ch.leyu.responseparse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 登录
 * 
 * @author L
 */

public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 38842270038323886L;

    private UserInfo userInfo;

    private ArrayList<Property> message;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public ArrayList<Property> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<Property> message) {
        this.message = message;
    }

}
