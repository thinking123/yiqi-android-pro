package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.Const;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.ProductListContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/22/2019 15:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ProductListPresenter extends BasePresenter<ProductListContract.Model, ProductListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ProductListPresenter(ProductListContract.Model model, ProductListContract.View rootView) {
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

    public void getMonthGoods(int pageNum,String monthId) {
        mModel.getMonthGoods(pageNum, monthId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> response) {
                        if (response.isSuccess())
                            mRootView.getGoodsResult(response.getData(), Const.MONTH_ID);
                        else
                            mRootView.getGoodsError(response.getMsg(),Const.MONTH_ID);
                    }
                });
    }

    public void getSaleGoods(int pageNum,String saleId) {
        mModel.getSaleGoods(pageNum, saleId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> response) {
                        if (response.isSuccess())
                            mRootView.getGoodsResult(response.getData(), Const.SALE7_ID);
                        else
                            mRootView.getGoodsError(response.getMsg(),Const.SALE7_ID);
                    }
                });
    }

    public void getBrandGoods(int pageNum,String brandId) {
        mModel.getBrandGoods(pageNum, brandId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> response) {
                        if (response.isSuccess())
                            mRootView.getGoodsResult(response.getData(), Const.BRAND_ID);
                        else
                            mRootView.getGoodsError(response.getMsg(),Const.BRAND_ID);
                    }
                });
    }

    public void getPriceSaleGoods(int pageNum,String priceSale) {
        mModel.getPriceSaleGoods(pageNum, priceSale)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> response) {
                        if (response.isSuccess())
                            mRootView.getGoodsResult(response.getData(), Const.SALE_ID);
                        else
                            mRootView.getGoodsError(response.getMsg(),Const.SALE_ID);
                    }
                });
    }

    public void getMiaoMiaoGouGoods(int pageNum,String miaoMiaoGou) {
        mModel.getMiaoMiaoGouGoods(pageNum, miaoMiaoGou)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> response) {
                        if (response.isSuccess())
                            mRootView.getGoodsResult(response.getData(), Const.MIAOMIAOGOU_ID);
                        else
                            mRootView.getGoodsError(response.getMsg(),Const.MIAOMIAOGOU_ID);
                    }
                });
    }
}
