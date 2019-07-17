package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.AfterSaleService;
import com.eshop.mvp.http.api.service.UploadService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AfterSales;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.DelOrderId;
import com.eshop.mvp.http.entity.order.ExpressInfo;
import com.eshop.mvp.http.entity.order.IdBean;
import com.eshop.mvp.http.entity.order.RefundBean;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.http.entity.order.RefundUpdateBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.AfterSaleContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/16/2019 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AfterSaleModel extends BaseModel implements AfterSaleContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AfterSaleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<AppOrderForm>> getOrderMsg(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .getOrderMsg(token, id);
    }

    @Override
    public Observable<MyBaseResponse<String>> applyRefund(String token, String orderId) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .applyRefund(token, orderId);
    }

    @Override
    public Observable<MyBaseResponse<String>> applyRefund(String token, RefundBean refundBean) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .applyRefund(token, refundBean);
    }

    @Override
    public Observable<MyBaseResponse<RefundDetail>> applyRefundDetails(String token, String orderId) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .applyRefundDetails(token, orderId);
    }

    @Override
    public Observable<MyBaseResponse<String>> applyRefundDel(String token, DelOrderId orderId) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .applyRefundDel(token, orderId);
    }

    @Override
    public Observable<MyBaseResponse<String>> applyRefundPut(String token, RefundUpdateBean refundUpdateBean) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .applyRefundPut(token, refundUpdateBean);
    }

    @Override
    public Observable<MyBaseResponse<RefundInfo>> applyRefundById(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .applyRefundById(token, id);
    }

    @Override
    public Observable<MyBaseResponse<AfterSaleOrder>> beingProcessedTab(String token, String pageNum, String type, String storeId) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .beingProcessedTab(token, pageNum, type, storeId);
    }

    @Override
    public Observable<MyBaseResponse<AfterSaleStore>> handlingAfterSales(String token, String id, String storeId) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .handlingAfterSales(token, id, storeId);
    }

    @Override
    public Observable<MyBaseResponse<String>> handlingAfterSales(String token, AfterSales afterSales) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .handlingAfterSales(token, afterSales);
    }

    @Override
    public Observable<MyBaseResponse<List<AppLog>>> appLogistics(String token) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .appLogistics(token);
    }

    @Override
    public Observable<MyBaseResponse<String>> logistics(String token, ExpressInfo expressInfo) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .logistics(token, expressInfo);
    }

    @Override
    public Observable<MyBaseResponse<String>> confirmTheRefund(String token, IdBean idBean) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .confirmTheRefund(token, idBean);
    }

    @Override
    public Observable<MyBaseResponse<String>> confirmTheRefundUser(String token, IdBean idBean) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .confirmTheRefundUser(token, idBean);
    }

    @Override
    public Observable<MyBaseResponse<RefundDetail2>> refund(String token, String orderId) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .refund(token, orderId);
    }

    @Override
    public Observable<MyBaseResponse<RefundDetailUser>> applyRefundDetailsUser(String token, String orderId) {
        return mRepositoryManager.obtainRetrofitService(AfterSaleService.class)
                .applyRefundDetailsUser(token, orderId);
    }
}