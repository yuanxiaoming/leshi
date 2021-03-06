
package com.ch.leyu.utils;

public interface Constant {

    public static final int EMAIL_ADDRESS = 0;

    public static final int PHONE_NUMBER = 1;

    public static final int NICK_NAME = 2;

    public static final int ID_NUMBER = 3;

    public static final String CID = "cid";

    public static final String PAGE = "page";

    public static final String UID = "uid";
    
    public static final String USER = "user";

    public static final String NICKNAME = "nickname";
    
    public static final String PASSWORD = "password";
    
    public static final String TAG = "tag";
    
    public static final String STAR_TAG = "star_tag";

    public static final String SORT = "sort";

    public static final String KEYWORD = "keyword";

    public static final String POSITION = "position";

    public static final String IMG = "img";

    public static final String TITLE = "title";

    public static final String ID = "id";
    
    public static final String LOGIN_UID = "loginUid";
    
    public static final String AUTH = "auth";
    
    public static final String PASS_STR = "passStr";

    /** 实体类传输key */
    public static final String DATA = "data";

    /** 游戏id */
    public static final String GMAE_ID = "gameId";

    /** url */
    public static final String URL = "http://www.legames.cn/app/api/";

    /** 炉石传说接口 */
    public static final String HS_URL = "game.php?gameId=23";

    /** 明星解说接口 */
    public static final String STAR_URL = "user.php";

    /** 英雄联盟接口 */
    public static final String LOL_URL = "game.php?gameId=21";

    /** 视频接口 */
    public static final String VIDEO_URL = "video.php?";

    /** 明星详情 */
    public static final String STAR_DETAIL = "user.php?";

    /** 视频搜索 */
    public static final String SEARCH = "search.php?";

    /** 热词搜索 */
    public static final String HOT_SEARCH = "search.php?action=hot";

    /** 全部新闻 */
    public static final String ALL_NEWS = "news.php?";  

    /** 其他新闻 */
    public static final String RESTS_NEWS = "action=list";

    /** LOL最热 */
    public static final String LOL_HOT = "http://www.legames.cn/app/api/video.php?action=rank&day=7";

    /** 视频详情 */
    public static final String VIDEO_DETAIL = "action=info";

    /** 评论列表 */
    public static final String COMMENT_LIST = "http://www.legames.cn/apps/comment.php?action=appGet&type=2";

    /** 发表评论接口 */
    public static final String COMMENT_PUBLISH = "http://www.legames.cn/apps/comment.php";

    /** 反馈接口 */
    public static final String FEED_BACK = "http://www.legames.cn/app/api/feedback.php";
    
    /** 登录接口 */
    public static final String LOGIN = "http://www.legames.cn/app/api/user.php?action=login";
    
    /** 我的视频接口 */
    public static final String MY_VIDEOS = "http://www.legames.cn/app/api/user.php?action=myVideos";
    
    /**前缀*/
    public static final String _URL = URL+STAR_DETAIL;
    
}
