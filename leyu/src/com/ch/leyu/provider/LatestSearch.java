
package com.ch.leyu.provider;

import com.ch.leyu.responseparse.VideoSearchResponse;

import org.codehaus.jackson.map.ObjectMapper;

public class LatestSearch {

    private static final String TAG = "LatestSearch";

    private String keyword ;

    private static ObjectMapper sObjectMapper;

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

        if (sObjectMapper == null) {
            sObjectMapper = new ObjectMapper();
            sObjectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
        }

        try {
            return this.sObjectMapper.readValue(mVideoSearchResponseString, VideoSearchResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getmVideoSearchResponseString() {

        return mVideoSearchResponseString;
    }


    public void setmVideoSearchResponse(VideoSearchResponse mVideoSearchResponse) {
        if (sObjectMapper == null) {
            sObjectMapper = new ObjectMapper();
            sObjectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            sObjectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
        }
        try {
            this.mVideoSearchResponseString = sObjectMapper.writeValueAsString(mVideoSearchResponse);
        } catch (Exception e) {
           this.mVideoSearchResponseString="{}";
        };

    }


}

