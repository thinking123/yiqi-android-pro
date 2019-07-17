package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.api.service.AddressService;
import com.eshop.mvp.http.api.service.CategoryService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.AddressContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/28/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddressModel extends BaseModel implements AddressContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    private String token;

    @Inject
    public AddressModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        token = BaseApp.loginBean.getToken();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<List<CityBean>>> selectPro() {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .selectPro();
    }

    @Override
    public Observable<MyBaseResponse<List<CityBean>>> selectCity(String cityCode) {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .selectCity(cityCode);
    }

    @Override
    public Observable<MyBaseResponse<List<AddressBean>>> get(String token,String userId) {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .get(token,userId);
    }

    @Override
    public Observable<MyBaseResponse<List<AddressBean>>> getById(String token, String userId) {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .get(token,userId);
    }

    @Override
    public Observable<MyBaseResponse<String>> add(String token,String userId, String address, String receiveUserName, String receivePhone) {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .add(token,userId, address, receiveUserName, receivePhone);
    }

    @Override
    public Observable<MyBaseResponse<String>> del(String token,String id) {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .del(token,id);
    }

    @Override
    public Observable<MyBaseResponse<String>> edit(String token, String id, String address, String receiveUserName, String receivePhone, String isDefault) {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .edit(token, id, address, receiveUserName, receivePhone, isDefault);
    }

    @Override
    public Observable<MyBaseResponse<String>> setDefault(String token,String userId, String id) {
        return mRepositoryManager
                .obtainRetrofitService(AddressService.class)
                .setDefault(token,userId, id);
    }
}