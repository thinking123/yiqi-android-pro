package com.eshop.mvp.http.api.service;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.WxBaseResponse;
import com.eshop.mvp.http.entity.WxUserInfoResponse;
import com.eshop.mvp.http.entity.login.JWTBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 微信第三方登录接口
 * @Author shijun
 * @Data 2019/1/13
 * @Package com.eshop.mvp.http.api.service
 **/
public interface WxService {
    /**
     * 通过code获取微信access_token
     * @param appid
     * @param secret
     * @param code
     * @param grant_type authorization_code
     * @return
     * 正确返回{
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE",
     * "unionid":"o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     * 错误返回
     * {"errcode":40029,"errmsg":"invalid code"}
     */
    @GET("https://api.weixin.qq.com/sns/oauth2/access_token")
    Observable<WxBaseResponse> wxGetToken(@Query("appid") String appid,
                                          @Query("secret") String secret,
                                          @Query("code") String code,
                                          @Query("grant_type") String grant_type);

    /**
     * 刷新或续期access_token使用
     * 接口说明
     * access_token是调用授权关系接口的调用凭证，由于access_token有效期（目前为2个小时）较短，当access_token超时后，可以使用refresh_token进行刷新，access_token刷新结果有两种：
     * 1.若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间；
     * 2.若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。
     * refresh_token拥有较长的有效期（30天）且无法续期，当refresh_token失效的后，需要用户重新授权后才可以继续获取用户头像昵称。
     * @param appid
     * @param grant_type refresh_token
     * @param refresh_token
     * @return
     * 正确返回
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     * 错误返回
     * {
     * "errcode":40030,"errmsg":"invalid refresh_token"
     * }
     */
    @GET("https://api.weixin.qq.com/sns/oauth2/refresh_token")
    Observable<WxBaseResponse> wxRefreshToken(@Query("appid") String appid,
                                              @Query("grant_type") String grant_type,
                                              @Query("refresh_token") String refresh_token);

    /**
     * 检验授权凭证（access_token）是否有效
     * @param access_token
     * @param openid
     * @return
     * 正确返回
     * {
     * "errcode":0,"errmsg":"ok"
     * }
     * 错误返回
     * {
     * "errcode":40003,"errmsg":"invalid openid"
     * }
     */
    @GET("https://api.weixin.qq.com/sns/auth")
    Observable<WxBaseResponse> wxAuth(@Query("access_token") String access_token,
                                      @Query("openid") String openid);

    /**
     * 获取用户个人信息（UnionID机制）
     * 接口说明
     * 此接口用于获取用户个人信息。开发者可通过OpenID来获取用户基本信息。特别需要注意的是，如果开发者拥有多个移动应用、网站应用和公众帐号，可通过获取用户基本信息中的unionid来区分用户的唯一性，因为只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号，用户的unionid是唯一的。换句话说，同一用户，对同一个微信开放平台下的不同应用，unionid是相同的。请注意，在用户修改微信头像后，旧的微信头像URL将会失效，因此开发者应该自己在获取用户信息后，将头像图片保存下来，避免微信头像URL失效后的异常情况。
     * @param access_token
     * @param openid
     * @return
     * 正确返回
     * {
     * "openid":"OPENID",
     * "nickname":"NICKNAME",
     * "sex":1,
     * "province":"PROVINCE",
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     * "privilege":[
     * "PRIVILEGE1",
     * "PRIVILEGE2"
     * ],
     * "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }错误返回
     * {
     * "errcode":40003,"errmsg":"invalid openid"
     * }
     */
    @GET("https://api.weixin.qq.com/sns/userinfo")
    Observable<WxUserInfoResponse> wxUserInfo(@Query("access_token") String access_token,
                                              @Query("openid") String openid);




}
