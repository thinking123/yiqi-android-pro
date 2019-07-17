package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.mvp.http.api.service.AddressService;
import com.eshop.mvp.http.api.service.LoginService;
import com.eshop.mvp.http.api.service.OrderService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.OrderContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/01/2019 23:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class OrderModel extends BaseModel implements OrderContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<Order>> addOrder(String token, String userId, String goodsId, String goodsAmount, String remarks, String addressId, int orderType) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .addOrder(token, userId, goodsId, goodsAmount, remarks, addressId, orderType);
    }

    @Override
    public Observable<MyBaseResponse<String>> updateOrder(
            String token,
            String id,
            String freightTotal,
            String receiveUserName,
            String address,
            String receivePhone) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .updateOrder(token, id, freightTotal, receiveUserName, address, receivePhone);
    }

    @Override
    public Observable<MyBaseResponse<String>> alipayPay(String token, String userId, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .alipayPay(token, userId, id);
    }

    @Override
    public Observable<MyBaseResponse<PayRet>> alipayPayNotify(String userId, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .alipayPayNotify(userId, id);
    }

    @Override
    public Observable<MyBaseResponse<AppOrder>> cancelOrder(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .cancelOrder(token, id);
    }

    @Override
    public Observable<MyBaseResponse<String>> deleteOrder(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .deleteOrder(token, id);
    }

    @Override
    public Observable<MyBaseResponse<AppOrder>> deliverGoods(String token, String id, String expressCompany, String expressNumber) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .deliverGoods(token, id, expressCompany, expressNumber);
    }

    @Override
    public Observable<MyBaseResponse<AppOrder>> finishOrder(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .finishOrder(token, id);
    }

    @Override
    public Observable<MyBaseResponse<AppOrder>> getOrder(String token, String pageNum, String userId, String orderStatus) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .getOrder(token, pageNum, userId, orderStatus);
    }

    @Override
    public Observable<MyBaseResponse<AppOrderForm>> getOrderDetails(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .getOrderDetails(token, id);
    }

    @Override
    public Observable<MyBaseResponse<AppOrder>> getStoreOrder(String token, String pageNum, String storeId, String orderStatus) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .getStoreOrder(token, pageNum, storeId, orderStatus);
    }

    @Override
    public Observable<MyBaseResponse<ExpressState>> logistics(String token, String id, String type) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .logistics(token, id, type);
    }

    @Override
    public Observable<MyBaseResponse<List<ExpressCode>>> logisticsAll(String token) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .logisticsAll(token);
    }


    @Override
    public Observable<MyBaseResponse<PayRet>> monthPay(String token, String userId, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .monthPay(token, userId, id);
    }

    @Override
    public Observable<MyBaseResponse<String>> payment(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .payment(token, id);
    }

    @Override
    public Observable<MyBaseResponse<String>> reminderShipment(String token, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .reminderShipment(token, id);
    }

    @Override
    public Observable<MyBaseResponse<PayRet>> wxpay(String token, String userId, String id) {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .wxpay(token, userId, id);
    }

    @Override
    public Observable<MyBaseResponse<String>> wxNotify() {
        return mRepositoryManager.obtainRetrofitService(OrderService.class)
                .wxNotify();
    }

    @Override
    public Observable<MyBaseResponse<List<AddressBean>>> getAddressList(String token, String userId) {
        return mRepositoryManager.obtainRetrofitService(AddressService.class)
                .get(token, userId);
    }
}