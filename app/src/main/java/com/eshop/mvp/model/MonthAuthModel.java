package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.AuthService;
import com.eshop.mvp.http.api.service.HomeService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.auth.MonthData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.store.Auth;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.MonthAuthContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2019 22:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MonthAuthModel extends BaseModel implements MonthAuthContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MonthAuthModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<Auth>> getAuth(String token, String userId) {
        return mRepositoryManager
                .obtainRetrofitService(AuthService.class)
                .getAuth(token,userId);
    }

    @Override
    public Observable<MyBaseResponse<MonthMsg>> getMonthMsg(String token) {
        return mRepositoryManager
                .obtainRetrofitService(AuthService.class)
                .getMonthMsg(token);
    }

    @Override
    public Observable<MyBaseResponse<String>> monthAdd(String token, MonthData monthData) {
        return mRepositoryManager
                .obtainRetrofitService(AuthService.class)
                .monthAdd(token,monthData);
    }

    @Override
    public Observable<MyBaseResponse<String>> monthEdit(String token, MonthData monthData) {
        return mRepositoryManager
                .obtainRetrofitService(AuthService.class)
                .monthEdit(token,monthData);
    }

    @Override
    public Observable<MyBaseResponse<String>> monthPay(String token, String userId, String id) {
        return mRepositoryManager
                .obtainRetrofitService(AuthService.class)
                .monthPay(token, userId, id);
    }
}