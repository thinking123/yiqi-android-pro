package com.eshop.mvp.presenter;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.product.CollectNum;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.model.StoreManagerModel;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.eshop.mvp.contract.SelfContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.utils.RxUtils;

import java.io.File;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author shijun
 * @Data 2019/3/11
 * @Package com.eshop.mvp.presenter
 **/
@FragmentScope
public class SelfPresenter extends BasePresenter<IModel, SelfContract.View> {

    @Inject
    UserModel userModel;

    @Inject
    StoreManagerModel storeManagerModel;

    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    public SelfPresenter(SelfContract.View rootView) {
        super(rootView);
    }

    public void getCollectionNum(String token) {
        userModel.getCollectionNum(token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<CollectNum>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<CollectNum> baseResponse) {
                        //if(baseResponse.isSuccess())
                        mRootView.getCollectionNumSuccess(baseResponse.getData());


                    }
                });
    }


    /**
     * 作用: 店铺开通状态查询；接口号; - Y1
     *
     * @param token
     */
    public void state(String token) {
        storeManagerModel.state(token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreState>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreState> response) {

                            mRootView.stateResult(response.getStatus(),response.getMsg(),response.getData());

                    }
                });
    }
}
