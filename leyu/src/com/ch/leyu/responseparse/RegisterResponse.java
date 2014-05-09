
package com.ch.leyu.responseparse;

import java.util.ArrayList;

public class RegisterResponse {
    private String count;

    private ArrayList<LoginResponse> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<LoginResponse> getList() {
        return list;
    }

    public void setList(ArrayList<LoginResponse> list) {
        this.list = list;
    }

}
