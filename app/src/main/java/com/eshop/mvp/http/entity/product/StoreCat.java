package com.eshop.mvp.http.entity.product;

/**
 * 店内分类
 * @Author shijun
 * @Data 2019/1/24
 * @Package com.eshop.mvp.http.entity.cart
 *    "id": 25,
 *         "categoryName": "测试",
 *         "categoryOrder": 0,
 *         "categoryState": 2,
 *         "auditingInfo": "信息填写有误！"
 **/

public class StoreCat {
    public int id;
    public int categoryOrder;
    public String categoryName;
    public int categoryState;
    public String auditingInfo;
}
