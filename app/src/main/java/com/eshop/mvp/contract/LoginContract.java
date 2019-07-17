package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.JWTBean;
import com.eshop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @Author shijun
 * @Data 2019/1/12
 * @Package com.eshop.mvp.contract
 **/
public interface LoginContract {

    interface View extends IView {
        void loginResult(LoginBean msg);

        void registerSuccess(UserInfoBean userBeanBaseResponse);

        void setPasswordResult(LoginBean msg);

        void updateUserImageSuccess(String url);

        void updateUserInfoSuccess(LoginBean msg);

        void wxLoginResult(LoginBean msg);

        void checkPhoneSuccess();

        void checkCodeSuccess();


        void loginHuanxinResult();
    }

    interface Model extends IModel {
        Observable<MyBaseResponse<LoginBean>> login(String phone, String password, String deviceId);

        Observable<MyBaseResponse<String>> sendSms(String phone);

        Observable<MyBaseResponse<String>> checkCode(String phone, String smscode);

        Observable<MyBaseResponse<UserInfoBean>> register(String phone,
                                                          String password,String logo,String nickName);

        Observable<MyBaseResponse<LoginBean>> setPassword(String phone, String password, String deviceId);

        Observable<MyBaseResponse<LoginBean>> updateUserInfo(
                String id,
                String phone,
                String password,
                String logo,
                String nickName,
                int sex,
                String deviceId,
                String openId);

        Observable<MyBaseResponse<LoginBean>> wxlogin(String unionid);

        Observable<MyBaseResponse<String>> checkPhone(String phone);




    }
}
