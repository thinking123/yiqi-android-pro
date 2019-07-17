package com.eshop.di.module;


import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eshop.mvp.contract.HuanXinContract;
import com.eshop.mvp.model.HuanXinModel;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.di.scope.ActivityScope;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 12:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public class HuanXinModule {
    private HuanXinContract.View view;

    public HuanXinModule(HuanXinContract.View view) {
        this.view = view;
    }


    @Provides
    @ActivityScope
    public HuanXinContract.Model provideModel(IRepositoryManager repositoryManager) {
        return new HuanXinModel(repositoryManager);
    }

    @Provides
    @ActivityScope
    public HuanXinContract.View provideView() {
        return view;
    }


    

//    @Binds
//     HuanXinContract.Model bindHuanXinModel(HuanXinModel model);
}