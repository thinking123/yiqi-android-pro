package com.eshop.mvp.http.entity.cart;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.mvp.http.entity.cart
 **/
@Getter
@Setter
public class AppCar {
    public int goodNum;
    public int goodsId;
    public int id;
    public int storeId;
    public int userId;

}
