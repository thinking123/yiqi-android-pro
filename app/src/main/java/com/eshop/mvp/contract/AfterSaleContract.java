package com.eshop.mvp.contract;

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
import com.eshop.mvp.http.entity.order.RefundBean;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.http.entity.order.RefundUpdateBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


/**
 * ================================================
 * 订单售后
 * ================================================
 */
public interface AfterSaleContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void applyRefundSuccess(String data);
        void applySuccess();
        void applyRefundDetailsSuccess(RefundDetail data);
        void applyRefundDelSuccess();
        void applyRefundPutSuccess();
        void applyRefundByIdSuccess(RefundInfo data);
        void beingProcessedTabSuccess(AfterSaleOrder data);
        void handlingAfterSalesSuccess(AfterSaleStore data);
        void handlingAfterSalesSuccess(String data);
        void appLogisticsSuccess(List<AppLog> data);
        void logisticsSuccess(String data);
        void logisticsAllSuccess(List<ExpressCode> list);
        void confirmTheRefundSuccess();
        void confirmTheRefundUserSuccess();
        void refundSuccess(RefundDetail2 data);
        void applyRefundDetailsUserSuccess(RefundDetailUser data);
        void getOrderMsgSuccess(AppOrderForm data);
        void updateUserImageSuccess(String url);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {


        Observable<MyBaseResponse<AppOrderForm>> getOrderMsg(
                String token,
                String id);

        Observable<MyBaseResponse<String>> applyRefund(String token,
                                                       String orderId);

        Observable<MyBaseResponse<String>> applyRefund(String token,
                                                       RefundBean refundBean);

        Observable<MyBaseResponse<RefundDetail>> applyRefundDetails(String token,
                                                                    String orderId);


        Observable<MyBaseResponse<String>> applyRefundDel(String token,
                                                          DelOrderId orderId);


        Observable<MyBaseResponse<String>> applyRefundPut(String token,
                                                          RefundUpdateBean refundUpdateBean);


        Observable<MyBaseResponse<RefundInfo>> applyRefundById(String token,
                                                               String id);


        Observable<MyBaseResponse<AfterSaleOrder>> beingProcessedTab(String token,
                                                                     String pageNum,
                                                                     String type,
                                                                     String storeId);


        Observable<MyBaseResponse<AfterSaleStore>> handlingAfterSales(String token,
                                                                      String id,
                                                                      String storeId);

        Observable<MyBaseResponse<String>> handlingAfterSales(String token,
                                                              AfterSales afterSales);


        Observable<MyBaseResponse<List<AppLog>>> appLogistics(String token);




        Observable<MyBaseResponse<String>> logistics(String token,
                                                     ExpressInfo expressInfo);

        Observable<MyBaseResponse<String>> confirmTheRefund(String token,
                                                            IdBean idBean);


        Observable<MyBaseResponse<String>> confirmTheRefundUser(String token,
                                                                IdBean idBean);


        Observable<MyBaseResponse<RefundDetail2>> refund(String token,
                                                         String orderId);


        Observable<MyBaseResponse<RefundDetailUser>> applyRefundDetailsUser(String token,
                                                                            String orderId);



    }
}
