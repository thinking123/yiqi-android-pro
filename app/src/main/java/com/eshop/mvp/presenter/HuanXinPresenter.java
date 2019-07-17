package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.HuanXinContract;


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
@ActivityScope
public class HuanXinPresenter extends BasePresenter<HuanXinContract.Model, HuanXinContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HuanXinPresenter(HuanXinContract.Model model, HuanXinContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.mErrorHandler = rxErrorHandler;
    }



    public void addChatRoom(String description,// "string",
                            String maxusers,// 0,
                            String name,// "string",
                            String owner){
        mModel.addChatRoom(description , maxusers ,name , owner)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> jwtBeanBaseResponse) {
                        if (jwtBeanBaseResponse.isSuccess()) {
                            mRootView.addChatRoomResult();
                        } else {
                            mRootView.showMessage(jwtBeanBaseResponse.getMsg());
                        }
                    }
                });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
