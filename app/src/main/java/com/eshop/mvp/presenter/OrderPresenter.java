package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.model.CartModel;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.eshop.mvp.contract.OrderContract;

import java.util.List;


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
public class OrderPresenter extends BasePresenter<OrderContract.Model, OrderContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    String token;

    @Inject
    public OrderPresenter(OrderContract.Model model, OrderContract.View rootView) {
        super(model, rootView);
        if (BaseApp.loginBean != null)
            token = BaseApp.loginBean.getToken();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 下订单
     *
     * @param userId      用户id
     * @param goodsId     商品id
     * @param goodsAmount 数量
     * @param remarks     留言
     * @param addressId   地址Id
     * @param orderType   订单类型0普通1月结
     */
    public void addOrder(String userId, String goodsId, String goodsAmount, String remarks, String addressId, int orderType) {
        mModel.addOrder(token, userId, goodsId, goodsAmount, remarks, addressId, orderType)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Order>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Order> response) {
                        if (response.isSuccess()) {
                            mRootView.addOrderSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 支付宝支付
     *
     * @param userId 用户id
     * @param id     订单id
     */
    public void alipayPay(String userId, String id) {
        mModel.alipayPay(token, userId, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.alipayPaySuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }



    /**
     * 取消订单
     *
     * @param id
     */
    public void cancelOrder(String id) {
        mModel.cancelOrder(token, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppOrder>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppOrder> response) {
                        if (response.isSuccess()) {
                            mRootView.cancelOrderSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 取消订单
     *
     * @param id
     */
    public void deleteOrder(String id) {
        mModel.deleteOrder(token, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.deleteOrderSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 发货
     *
     * @param id             订单id
     * @param expressCompany 物流公司
     * @param expressNumber  物流单号
     */
    public void deliverGoods(String id, String expressCompany, String expressNumber) {
        mModel.deliverGoods(token, id, expressCompany, expressNumber)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppOrder>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppOrder> response) {
                        if (response.isSuccess()) {
                            mRootView.deliverGoodsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 确认收货
     *
     * @param id
     */
    public void finishOrder(String id) {
        mModel.finishOrder(token, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppOrder>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppOrder> response) {
                        if (response.isSuccess()) {
                            mRootView.finishOrderSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取我的订单（用户）
     *
     * @param pageNum     第几页
     * @param userId      用户ID
     * @param orderStatus 状态
     */
    public void getOrder(String pageNum, String userId, String orderStatus) {
        mModel.getOrder(token, pageNum, userId, orderStatus)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppOrder>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppOrder> response) {
                        if (response.isSuccess()) {
                            mRootView.getOrderSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 通过id获取订单详细
     *
     * @param id
     */
    public void getOrderDetails(String id) {
        mModel.getOrderDetails(token, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppOrderForm>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppOrderForm> response) {
                        if (response.isSuccess()) {
                            mRootView.getOrderDetailsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取我的订单（商户）
     *
     * @param pageNum
     * @param storeId
     * @param orderStatus
     */
    public void getStoreOrder(String pageNum, String storeId, String orderStatus) {
        mModel.getStoreOrder(token, pageNum, storeId, orderStatus)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppOrder>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppOrder> response) {
                        if (response.isSuccess()) {
                            mRootView.getStoreOrderSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 查询物流
     *
     * @param expressCompany
     * @param expressNumber
     */
    public void logistics(String id, String type) {
        mModel.logistics(token, id, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<ExpressState>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<ExpressState> response) {
                        if (response.isSuccess()) {
                            mRootView.logisticsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void logisticsAll() {
        mModel.logisticsAll(token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<ExpressCode>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<ExpressCode>> response) {
                        if (response.isSuccess()) {
                            mRootView.logisticsAllSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }


    /**
     * 月结支付
     *
     * @param userId
     * @param id
     */
    public void monthPay(String userId, String id) {
        mModel.monthPay(token, userId, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<PayRet>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<PayRet> response) {
                        if (response.isSuccess()) {
                            mRootView.monthPaySuccess(response.getData());
                        } else {
                            mRootView.monthPayStatus(response.getStatus(), response.getMsg());
                        }
                    }
                });
    }

    /**
     * 提醒付款
     *
     * @param id
     */
    public void payment(String id) {
        mModel.payment(token, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.paymentSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 提醒发货
     *
     * @param id
     */
    public void reminderShipment(String id) {
        mModel.reminderShipment(token, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.reminderShipmentSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 微信支付
     *
     * @param userId
     * @param id
     */
    public void wxpay(String userId, String id) {
        mModel.wxpay(token, userId, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<PayRet>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<PayRet> response) {
                        if (response.isSuccess()) {
                            mRootView.wxpaySuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 微信支付通知
     */
    public void wxNotify() {
        mModel.wxNotify()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            // mRootView.wxNotifySuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void getAddressList(String token, String userId) {
        mModel.getAddressList(token, userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<AddressBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<AddressBean>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.getAddressList(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }

    /**
     * 更新订单 地址和运费
     * @param token
     * @param id
     * @param freightTotal
     * @param receiveUserName
     * @param address
     * @param receivePhone
     */
    public void updateOrder(String token,
                            String id,
                            String freightTotal,
                            String receiveUserName,
                            String address,
                            String receivePhone) {
        mModel.updateOrder(token, id, freightTotal, receiveUserName, address, receivePhone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.updateOrderSuccess();
                        } else {
                            // mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }


}
