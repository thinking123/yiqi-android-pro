package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.eshop.mvp.http.entity.home.HotLineBean;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.HotLineContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 12:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HotLinePresenter extends BasePresenter<HotLineContract.Model, HotLineContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HotLinePresenter(HotLineContract.Model model, HotLineContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getHotLine() {
        mModel.getHotLine()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<HotLineBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<HotLineBean>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.getHotLineResult(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }
}
