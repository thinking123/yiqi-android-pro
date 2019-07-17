package com.eshop.mvp.http.entity.order;

import com.eshop.mvp.http.entity.cart.AppGoods;

import java.util.List;

public class AppOrderForm {
    public String address;
    public List<OrderDetail> apporderdetailsList;
    public String createTime;
    public String expressCompany;
    public String expressNumber;
    public int goodsAmount;
    public String id;
    public String orderId;
    public int orderStatus;
    public int orderType;
    public String payNumber;
    public int payStatus;
    public int payType;
    public String receivePhone;
    public String receiveUserName;
    public String remarks;
    public String reminderPayment;
    public int reminderShipment;
    public int storeId;
    public String storeName;
    public double totalPrice;
    public String userId;
    public String remainingTime;

    public List<AppGoods> appGoodsList;

}
