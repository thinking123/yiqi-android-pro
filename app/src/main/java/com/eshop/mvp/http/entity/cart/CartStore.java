package com.eshop.mvp.http.entity.cart;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.eshop.mvp.http.entity.product.Product;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.mvp.http.entity.cart
 **/

public class CartStore {
    public List<AppCar> appcarlsit;
    public List<AppGoods> appgoodslsit;
    public String background;
    public int balance;
    public String createTime;
    public String detailsAddress;
    public String errorPage;
    public int id;
    public String idCard;
    public String idCardHeadImg;
    public String idCardIMg;
    public String licenseDate;
    public String licenseImg;
    public String licenseIsLong;
    public String licenseName;
    public String licenseNumber;
    public String page1Info;
    public String page2Info;
    public String page3Info;
    public String storeAddress;
    public String storeCategory;
    public String storeCategoryParent;
    public String storeImg;
    public String storePhone;
    public String streoName;
    public String trueName;
    public int userId;
    public String withdrawalPwd;

    public boolean isChecked;

}
