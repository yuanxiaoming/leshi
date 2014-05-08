
package com.ch.leyu.responseparse;

import java.util.ArrayList;

public class HSResponse {

    private String code;

    private ArrayList<HSDataResponse> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<HSDataResponse> getData() {
        return data;
    }

    public void setData(ArrayList<HSDataResponse> data) {
        this.data = data;
    }

}
