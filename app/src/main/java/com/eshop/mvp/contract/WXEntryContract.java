package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.WxBaseResponse;
import com.eshop.mvp.http.entity.WxUserInfoResponse;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2019 10:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface WXEntryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getTokenResult(WxBaseResponse response);
        void refreshTokenResult(WxBaseResponse response);
        void authResult(WxBaseResponse response);
        void userInfoResult(WxUserInfoResponse response);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<WxBaseResponse> wxGetToken(String appid,String secret,String code,String grant_type);
        Observable<WxBaseResponse> wxRefreshToken(String appid, String grant_type, String refresh_token);
        Observable<WxBaseResponse> wxAuth(String access_token, String openid);
        Observable<WxUserInfoResponse> wxUserInfo(String access_token, String openid);



    }
}
