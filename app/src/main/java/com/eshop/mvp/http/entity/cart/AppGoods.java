package com.eshop.mvp.http.entity.cart;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 购物车商品
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.mvp.http.entity.cart
 **/

public class AppGoods {
    public int appClassId;
    public String auditingInfo;
    public int brandId;
    public int carId;
    public int categoryOne;
    public int categoryThree;
    public int categoryTwo;
    public String createTime;
    public String details;
    public String detailsUrl;
    public String dianPuFenLei;
    public double freight;
    public int freightState;
    public int goodNum;
    public double halfPrice;
    public int id;
    public String imgUrl;
    public int isDel;
    public int isHalf;
    public int isSeven;
    public String isUse;
    public String licenseNumber;
    public String marketingType;
    public int state;
    public int stock;
    public int storeId;
    public int storeType;
    public String streoName;
    public String title;
    public String unit;
    public double unitPrice;
    public String updateTime;
    public String zhuLeiMu;
    public String zhuTuUrl;

    public boolean isChecked;
    public boolean isMonth;
    public boolean isHead;//是否店铺的第一个商品，在结算页显示店铺名
    public boolean isFoot;//是否店铺的最后一个商品，显示买家留言
    public String remark;//买家留言


    public int orderStatus;
    public double totalPrice;
    public String orderId;
    public int orderType;
    public String remainingTime;

    public String reminderPayment;
    public int reminderShipment;

}
