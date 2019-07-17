package com.eshop.mvp.http.entity;

import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;

public class AppData {

    public static UserInfoBean userBean;
    public static LoginBean loginBean;

    public static final String WECHAT_APPID = "wxfec44f1912f39901";
    //public static final String WECHAT_API_KEY = "CEA191100945EE81135347A52F7BB292";
    public static final String WECHAT_APP_SECRET = "64561f94e4a3e9eb7449a318395091ba";

    public static WxBaseResponse wxBaseResponse;
    public static WxUserInfoResponse wxUserInfoResponse;

    public static String LoginType = "e";//e

}
