package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AfterSales;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.DelOrderId;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressInfo;
import com.eshop.mvp.http.entity.order.IdBean;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.order.RefundBean;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.http.entity.order.RefundUpdateBean;
import com.eshop.mvp.model.OrderModel;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.eshop.mvp.contract.AfterSaleContract;

import java.io.File;
import java.util.List;


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
public class AfterSalePresenter extends BasePresenter<AfterSaleContract.Model, AfterSaleContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    UserModel userModel;

    @Inject
    OrderModel orderModel;

    @Inject
    public AfterSalePresenter(AfterSaleContract.Model model, AfterSaleContract.View rootView) {
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

    /**
     * 获取退款金额
     * @param token
     * @param orderId
     */
    public void applyRefund(String token,
                            String orderId) {
        mModel.applyRefund(token, orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.applyRefundSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 提交退款申请
     * @param token
     * @param refundBean
     */
    public void applyRefund(String token,
                            RefundBean refundBean) {
        mModel.applyRefund(token, refundBean)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.applySuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 通过订单id获取退款申请详情
     * @param token
     * @param orderId
     */
    public void applyRefundDetails(String token,
                                   String orderId) {
        mModel.applyRefundDetails(token, orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<RefundDetail>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<RefundDetail> response) {
                        if (response.isSuccess()) {
                            mRootView.applyRefundDetailsSuccess(response.getData());
                        } else {
                           // mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 通过订单id撤销申请
     * @param token
     * @param orderId
     */
    public void applyRefundDel(String token,
                               DelOrderId orderId) {
        mModel.applyRefundDel(token, orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.applyRefundDelSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 更新退款申请
     * @param token
     * @param refundUpdateBean
     */
    public void applyRefundPut(String token,
                               RefundUpdateBean refundUpdateBean) {
        mModel.applyRefundPut(token, refundUpdateBean)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.applyRefundPutSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 通过id获取退款申请信息
     * @param token
     * @param id
     */
    public void applyRefundById(String token,
                                String id) {
        mModel.applyRefundById(token, id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<RefundInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<RefundInfo> response) {
                        if (response.isSuccess()) {
                            mRootView.applyRefundByIdSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 订单列表
     * @param token
     * @param pageNum
     * @param type
     * @param storeId
     */
    public void beingProcessedTab(String token,
                                  String pageNum,
                                  String type,
                                  String storeId) {
        mModel.beingProcessedTab(token, pageNum, type, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AfterSaleOrder>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AfterSaleOrder> response) {
                        if (response.isSuccess()) {
                            mRootView.beingProcessedTabSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 商家处理售后（查看）
     * @param token
     * @param id
     * @param storeId
     */
    public void handlingAfterSales(String token,
                                   String id,
                                   String storeId) {
        mModel.handlingAfterSales(token, id, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AfterSaleStore>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AfterSaleStore> response) {
                        if (response.isSuccess()) {
                            mRootView.handlingAfterSalesSuccess(response.getData());
                        } else {
                          //  mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 商家处理售后（提交）
     * @param token
     * @param afterSales
     */
    public void handlingAfterSales(String token,
                                   AfterSales afterSales) {
        mModel.handlingAfterSales(token, afterSales)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.handlingAfterSalesSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }


    /**
     * 物流公司
     */
    public void appLogistics(String token) {
        mModel.appLogistics(token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<AppLog>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<AppLog>> response) {
                        if (response.isSuccess()) {
                            mRootView.appLogisticsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void logisticsAll(String token) {
        orderModel.logisticsAll(token)
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
     * 用户提交物流信息
     * @param token
     * @param expressInfo
     */
    public void logistics(String token,
                          ExpressInfo expressInfo) {
        mModel.logistics(token, expressInfo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.logisticsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 商家确认退款
     * @param token
     * @param idBean
     */
    public void confirmTheRefund(String token,
                                 IdBean idBean) {
        mModel.confirmTheRefund(token, idBean)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.confirmTheRefundSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 用户确认退款到账
     * @param token
     * @param idBean
     */
    public void confirmTheRefundUser(String token,
                                     IdBean idBean) {
        mModel.confirmTheRefundUser(token, idBean)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.confirmTheRefundUserSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }


    /**
     * 通过订单id查看退款中 详情
     * @param token
     * @param orderId
     */
    public void refund(String token,
                       String orderId) {
        mModel.refund(token, orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<RefundDetail2>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<RefundDetail2> response) {
                        if (response.isSuccess()) {
                            mRootView.refundSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void getOrderMsg(String token,
                       String orderId) {
        mModel.getOrderMsg(token, orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppOrderForm>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppOrderForm> response) {
                        if (response.isSuccess()) {
                            mRootView.getOrderMsgSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 退货给商家
     * @param token
     * @param orderId
     */
    public void applyRefundDetailsUser(String token,
                                       String orderId) {
        mModel.applyRefundDetailsUser(token, orderId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<RefundDetailUser>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<RefundDetailUser> response) {
                        if (response.isSuccess()) {
                            mRootView.applyRefundDetailsUserSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void updateUserImage(String upload_file) {
        MultipartBody.Part face = MultipartBody.Part.createFormData("file", "header_image.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(upload_file)));
        userModel.upLoadImage(face)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()){
                            mRootView.updateUserImageSuccess(stringBaseResponse.getData());
                        } else {
                            mRootView.showMessage(stringBaseResponse.getMsg());
                        }
                    }
                });
    }
}
