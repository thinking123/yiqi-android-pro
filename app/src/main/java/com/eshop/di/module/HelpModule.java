package com.eshop.di.module;

import android.app.Application;

import com.eshop.mvp.contract.CityContract;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.http.entity.store.HelpBean;
import com.eshop.mvp.model.CityModel;
import com.eshop.mvp.ui.activity.set.HelpActivity;
import com.eshop.mvp.ui.adapter.CityQuickAdapter;
import com.eshop.mvp.ui.adapter.HelpQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.HelpContract;
import com.eshop.mvp.model.HelpModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/27/2019 22:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class HelpModule {

   // @Binds
   // abstract HelpContract.Model bindHelpModel(HelpModel model);

    private HelpContract.View view;

    public HelpModule(HelpContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public HelpContract.View provideBaseView() {
        return view;
    }

    @Provides
    @ActivityScope
    public HelpContract.Model provideBaseModel(IRepositoryManager repositoryManager) {
        return new HelpModel(repositoryManager);
    }

    @ActivityScope
    @Provides
    public List<HelpBean> helpBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    public HelpQuickAdapter prodvideHelpQuickAdapter(
            List<HelpBean> helpBeans, Application application) {
        HelpQuickAdapter helpQuickAdapter = new HelpQuickAdapter(helpBeans);
        return helpQuickAdapter;

    }
}