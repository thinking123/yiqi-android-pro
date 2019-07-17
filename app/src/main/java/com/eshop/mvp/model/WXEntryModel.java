package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.LoginService;
import com.eshop.mvp.http.api.service.WxService;
import com.eshop.mvp.http.entity.WxBaseResponse;
import com.eshop.mvp.http.entity.WxUserInfoResponse;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.WXEntryContract;

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
@ActivityScope
public class WXEntryModel extends BaseModel implements WXEntryContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WXEntryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WxBaseResponse> wxGetToken(String appid, String secret, String code, String grant_type) {
        return mRepositoryManager.obtainRetrofitService(WxService.class)
                .wxGetToken(appid, secret, code, grant_type);
    }

    @Override
    public Observable<WxBaseResponse> wxRefreshToken(String appid, String grant_type, String refresh_token) {
        return mRepositoryManager.obtainRetrofitService(WxService.class)
                .wxRefreshToken(appid, grant_type, refresh_token);
    }

    @Override
    public Observable<WxBaseResponse> wxAuth(String access_token, String openid) {
        return mRepositoryManager.obtainRetrofitService(WxService.class)
                .wxAuth(access_token, openid);
    }

    @Override
    public Observable<WxUserInfoResponse> wxUserInfo(String access_token, String openid) {
        return mRepositoryManager.obtainRetrofitService(WxService.class)
                .wxUserInfo(access_token, openid);
    }
}