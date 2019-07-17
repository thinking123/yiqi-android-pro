package com.eshop.mvp.http.entity.home;

import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class MockAppOrder {
    public static List<AppOrderForm> apporderFormList = new ArrayList<>();
    public static PageUtil pageUtil;

    public static AppOrder appOrder;

    public static void init(int status){
        if(status==0) {
            MockAppOrderForm.init(0);
            apporderFormList.add(MockAppOrderForm.appOrderForm);

            MockAppOrderForm.init(1);
            apporderFormList.add(MockAppOrderForm.appOrderForm);

            MockAppOrderForm.init(2);
            apporderFormList.add(MockAppOrderForm.appOrderForm);

            MockAppOrderForm.init(3);
            apporderFormList.add(MockAppOrderForm.appOrderForm);

            MockAppOrderForm.init(4);
            apporderFormList.add(MockAppOrderForm.appOrderForm);
        }else{
            apporderFormList.clear();
            MockAppOrderForm.init(status);
            apporderFormList.add(MockAppOrderForm.appOrderForm);

        }

        appOrder = new AppOrder();
        appOrder.apporderformList = apporderFormList;
    }

    public static List<AppGoods> getData(AppOrder appOrder){
        List<AppGoods> appGoodsList = new ArrayList<>();
        AppGoods lastAppGoods = null;
        for(AppOrderForm appOrderForm : appOrder.apporderformList){
            int k=0;
            for(OrderDetail orderDetail : appOrderForm.apporderdetailsList){
                if(k==0)orderDetail.appgoods.isHead = true;
                else orderDetail.appgoods.isHead = false;
                k++;
                orderDetail.appgoods.streoName = appOrderForm.storeName;
                orderDetail.appgoods.storeId = appOrderForm.storeId;
                orderDetail.appgoods.orderStatus = appOrderForm.orderStatus;
                orderDetail.appgoods.totalPrice = appOrderForm.totalPrice;
                orderDetail.appgoods.details = "";
                orderDetail.appgoods.orderId = appOrderForm.orderId;
                orderDetail.appgoods.goodNum = orderDetail.goodsAmount;
                orderDetail.appgoods.orderType = appOrderForm.orderType;


                //用这个传id
                orderDetail.appgoods.carId = Integer.parseInt(appOrderForm.id);
                orderDetail.appgoods.reminderShipment = appOrderForm.reminderShipment;
                orderDetail.appgoods.reminderPayment = appOrderForm.reminderPayment;
                orderDetail.appgoods.remainingTime = appOrderForm.remainingTime;

                appGoodsList.add(orderDetail.appgoods);
                lastAppGoods = orderDetail.appgoods;
            }
            lastAppGoods.isFoot=true;
        }

        return appGoodsList;
    }
}
