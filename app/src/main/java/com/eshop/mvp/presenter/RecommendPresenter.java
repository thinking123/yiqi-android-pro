package com.eshop.mvp.presenter;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.AdBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.eshop.mvp.model.HomeModel;
import com.eshop.mvp.model.UserModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.eshop.mvp.contract.RecommendContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.product.RecommendBean;
import com.eshop.mvp.utils.RxUtils;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.presenter
 **/


@FragmentScope
public class RecommendPresenter extends BasePresenter<RecommendContract.Model, RecommendContract.View> {
    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    HomeModel homeModel;

    @Inject
    public RecommendPresenter(RecommendContract.Model model, RecommendContract.View rootView) {
        super(model, rootView);
    }



    public void getHomeData() {
        mModel.getHomeData()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<HomeBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<HomeBean> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.getHomeDataResult(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }

    public void getGoodsData(int pageNum) {
        homeModel.getGoodsData(pageNum)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.getGoodsDataResult(baseResponse.getData());
                        } else {
                            mRootView.getGoodsDataError(baseResponse.getMsg());
                        }
                    }
                });
    }

    public void getAdData() {
        homeModel.getAdData()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AdBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AdBean> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.getAdDataResult(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }
}
