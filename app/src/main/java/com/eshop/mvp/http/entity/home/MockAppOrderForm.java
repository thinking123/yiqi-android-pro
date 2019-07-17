package com.eshop.mvp.http.entity.home;

import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.cart.CartStore;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class MockAppOrderForm {

    public static String address;
    public static List<OrderDetail> apporderdetailsList;
    public static String orderId;
    public static int orderStatus;
    public static String storeId;
    public static String storeName;
    public static double totalPrice;

    public static List<AppGoods> appGoodsList;

    public static AppOrderForm appOrderForm;

    public static void init(int status){

        storeId = status+1+"";
        orderId = storeId;
        orderStatus = status;
        storeName = "深圳腾讯科技有限公司";
        totalPrice = 2986;

        apporderdetailsList = new ArrayList<>();
        appGoodsList = new ArrayList<>();

        AppGoods b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/3c0878bd-2d19-4e4a-8d09-600e0637809b.jpg";
        b1.title = "飞科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "腾讯科技有限公司";
        b1.unitPrice = 169;
        b1.storeId =1;
        b1.goodNum = 2;
        b1.carId = 11;
        b1.isChecked = false;

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.appgoods = b1;
        apporderdetailsList.add(orderDetail);

        appGoodsList.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "腾讯科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =1;
        b1.carId = 12;
        b1.goodNum = 3;
        b1.isChecked = false;

        orderDetail = new OrderDetail();
        orderDetail.appgoods = b1;
        apporderdetailsList.add(orderDetail);

        appGoodsList.add(b1);

        b1 = new AppGoods();
        b1.imgUrl = "http://zack-image.oss-cn-beijing.aliyuncs.com/84f44f32-82a2-4ce0-8dcd-ad092b862710.jpg";
        b1.title = "科剃须刀电动全身水洗刮胡刀";
        b1.details = "产品描述";
        b1.streoName = "腾讯科技有限公司";
        b1.unitPrice = 129;
        b1.storeId =1;
        b1.carId = 13;
        b1.goodNum = 4;
        b1.isChecked = false;

        orderDetail = new OrderDetail();
        orderDetail.appgoods = b1;
        apporderdetailsList.add(orderDetail);

        appGoodsList.add(b1);

        ////
        appOrderForm = new AppOrderForm();
        appOrderForm.storeId = status+1;
        appOrderForm.orderId = appOrderForm.storeId+"";
        appOrderForm.orderStatus = status;
        appOrderForm.storeName = "深圳腾讯科技有限公司";
        appOrderForm.totalPrice = 2986;

        appOrderForm.appGoodsList = appGoodsList;
        appOrderForm.apporderdetailsList = apporderdetailsList;

    }



}