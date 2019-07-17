package com.eshop.di.module;

import android.app.Application;

import com.eshop.mvp.contract.AddressContract;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.model.CartModel;
import com.eshop.mvp.ui.adapter.CityQuickAdapter;
import com.eshop.mvp.ui.adapter.SubCatQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.CityContract;
import com.eshop.mvp.model.CityModel;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


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
@Module
public class CityModule {

    private CityContract.View view;

    public CityModule(CityContract.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    public CityContract.View provideBaseView() {
        return view;
    }

    @Provides
    @FragmentScope
    public CityContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new CityModel(repositoryManager);
    }

    @FragmentScope
    @Provides
    public List<CityBean> provideCityBeans() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    public CityQuickAdapter prodvideCityQuickAdapter(
            List<CityBean> cityBeans, Application application) {
        CityQuickAdapter cityQuickAdapter = new CityQuickAdapter(cityBeans);
        return cityQuickAdapter;

    }

}