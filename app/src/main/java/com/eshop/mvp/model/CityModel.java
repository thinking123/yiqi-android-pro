package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.AddressService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.CityContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/30/2019 00:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CityModel extends BaseModel implements CityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
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
}