package com.eshop.mvp.http.entity.order;

import com.eshop.mvp.http.entity.cart.AppGoods;

import java.util.List;

public class OrderDetail {
    public AppGoods appgoods;
     public String createTime;
    public int  freight;
    public int freightState;
    public int goodsAmount;
    public String goodsId;
    public double goodsPrice;
    public String id;
    public String remarks;
    public String orderId;
    public String storeId;
    public double sumPrice;

    public int sum;
    public double small_sum;
}
