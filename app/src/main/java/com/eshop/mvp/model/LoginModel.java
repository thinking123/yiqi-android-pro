package com.eshop.mvp.model;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.api.service.LoginService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.JWTBean;
import com.eshop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;


/**
 * @Author shijun
 * @Data 2019/1/11
 * @Package com.eshop.mvp.model
 **/
public class LoginModel extends BaseModel implements LoginContract.Model {
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<MyBaseResponse<LoginBean>> login(String phone, String password, String deviceId) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .login(phone, password,deviceId);
    }

    public Observable<MyBaseResponse<String>> sendSms(String phone) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .sendSms(phone);
    }

    @Override
    public Observable<MyBaseResponse<String>> checkCode(String phone, String smscode) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .checkCode(phone,smscode);
    }

    public Observable<MyBaseResponse<UserInfoBean>> register(String phone,String password,String logo,String nickName) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .register(phone, password,logo,nickName);
    }

    @Override
    public Observable<MyBaseResponse<LoginBean>> setPassword(String phone, String password, String deviceId) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .setPassword(phone, password,deviceId);
    }

    @Override
    public Observable<MyBaseResponse<LoginBean>> updateUserInfo(String id, String phone, String password, String logo, String nickName, int sex, String deviceId,String openId) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .updateUserInfo(id,phone, password,logo,nickName,sex,deviceId,openId);
    }

    @Override
    public Observable<MyBaseResponse<LoginBean>> wxlogin(String unionid) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .wxlogin(unionid);
    }

    @Override
    public Observable<MyBaseResponse<String>> checkPhone(String phone) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .checkPhone(phone);
    }

}
