
package com.ch.leyu.provider;

import com.ch.leyu.http.work.JacksonParser;
import com.ch.leyu.responseparse.VideoSearchResponse;
/**
 * @ClassName: LatestSearch
 * @author xiaoming.yuan
 * @date 2014-5-22 上午10:40:33
 */
public class LatestSearch {

    private String keyword ;
    private String mVideoSearchResponseString;



    public LatestSearch(String keyword, String videoSearchResponseString) {
        super();
        this.keyword = keyword;
        this.mVideoSearchResponseString = videoSearchResponseString;
    }

    public LatestSearch(String keyword, VideoSearchResponse videoSearchResponse) {
        super();
        this.keyword = keyword;
        setmVideoSearchResponse(videoSearchResponse);
    }

    public String getKeyword() {

        return this.keyword;
    }

    public void setKeyword(String keyword) {

        this.keyword = keyword;
    }

    public VideoSearchResponse getmVideoSearchResponse() {
        try {
            return  JacksonParser.getInstance().readValue(mVideoSearchResponseString, VideoSearchResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getmVideoSearchResponseString() {

        return mVideoSearchResponseString;
    }


    public void setmVideoSearchResponse(VideoSearchResponse mVideoSearchResponse) {
        try {
            this.mVideoSearchResponseString = JacksonParser.getInstance().writeValueAsString(mVideoSearchResponse);
        } catch (Exception e) {
           this.mVideoSearchResponseString="{}";
        };

    }


}

