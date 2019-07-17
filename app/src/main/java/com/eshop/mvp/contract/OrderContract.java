package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.Query;


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
public interface OrderContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void addOrderSuccess(Order data);
        void addOrderSuccess(List<Order> data);
        void alipayPaySuccess(String data);
        void alipayPayNotifySuccess(PayRet data);
        void cancelOrderSuccess(AppOrder data);
        void deleteOrderSuccess();
        void deliverGoodsSuccess(AppOrder data);
        void finishOrderSuccess(AppOrder data);
        void getOrderSuccess(AppOrder data);
        void getOrderDetailsSuccess(AppOrderForm data);
        void getStoreOrderSuccess(AppOrder data);
        void logisticsSuccess(ExpressState data);
        void logisticsAllSuccess(List<ExpressCode> list);
        void monthPaySuccess(PayRet data);
        void monthPayStatus(String status, String msg);
        void paymentSuccess(String data);
        void reminderShipmentSuccess(String data);
        void wxpaySuccess(PayRet data);
        void getAddressList(List<AddressBean> list);
        void updateOrderSuccess();


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<MyBaseResponse<Order>> addOrder(String token,String userId,String goodsId,String goodsAmount,String remarks,String addressId,int orderType);
        Observable<MyBaseResponse<String>> alipayPay(String token,String userId, String id);
        Observable<MyBaseResponse<PayRet>> alipayPayNotify(String userId, String id);
        Observable<MyBaseResponse<AppOrder>> cancelOrder(String token,String id);
        Observable<MyBaseResponse<String>> deleteOrder(String token,String id);
        Observable<MyBaseResponse<AppOrder>> deliverGoods(String token,String id,String expressCompany,String expressNumber);
        Observable<MyBaseResponse<AppOrder>> finishOrder(String token,String id);
        Observable<MyBaseResponse<AppOrder>> getOrder(String token,String pageNum,String userId,String orderStatus);
        Observable<MyBaseResponse<AppOrderForm>> getOrderDetails(String token,String id);
        Observable<MyBaseResponse<AppOrder>> getStoreOrder(String token,String pageNum,String storeId,String orderStatus);
        Observable<MyBaseResponse<ExpressState>> logistics(String token, String id, String type);
        Observable<MyBaseResponse<List<ExpressCode>>> logisticsAll(String token);
        Observable<MyBaseResponse<PayRet>> monthPay(String token,String userId, String id);
        Observable<MyBaseResponse<String>> payment(String token,String id);
        Observable<MyBaseResponse<String>> reminderShipment(String token,String id);
        Observable<MyBaseResponse<PayRet>> wxpay(String token,String userId, String id);
        Observable<MyBaseResponse<String>> wxNotify();
        Observable<MyBaseResponse<List<AddressBean>>> getAddressList(String token,String userId);
        Observable<MyBaseResponse<String>> updateOrder(
                String token,
                String id,
                String freightTotal,
                String receiveUserName,
                String address,
                String receivePhone);

    }
}
