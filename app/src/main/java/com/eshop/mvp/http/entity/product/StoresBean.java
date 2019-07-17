package com.eshop.mvp.http.entity.product;

import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.PageUtil;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.http.entity.category
 **/


public class StoresBean {
    public List<Store> collectionStoreList;
    public PageUtil pageUtil;

}
