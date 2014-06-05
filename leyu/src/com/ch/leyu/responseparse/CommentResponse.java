
package com.ch.leyu.responseparse;

import java.util.ArrayList;

public class CommentResponse {

    private ArrayList<CommentDetail> comment;

    private int totalPage;

    public ArrayList<CommentDetail> getComment() {
        return comment;
    }

    public void setComment(ArrayList<CommentDetail> comment) {
        this.comment = comment;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

}
