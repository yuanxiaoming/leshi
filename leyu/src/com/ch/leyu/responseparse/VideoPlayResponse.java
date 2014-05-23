
package com.ch.leyu.responseparse;

import java.io.Serializable;

public class VideoPlayResponse implements Serializable {
    private static final long serialVersionUID = -7134101089518925408L;

    private VideoDetailResponse videoInfo;

    public VideoDetailResponse getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoDetailResponse videoInfo) {
        this.videoInfo = videoInfo;
    }
}
