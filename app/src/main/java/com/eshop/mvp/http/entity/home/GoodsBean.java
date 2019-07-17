package com.eshop.mvp.http.entity.home;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.http.entity.category
 **/

@Getter
@Setter
public class GoodsBean {
    public List<HomeGoodBean> goodsList;
    public String vadioUrl;
    public PageUtil pageUtil;

    //7号特卖
    public List<HomeGoodBean> homePageGoodsList;
    public int saleState;
    public String saleTips;
    public String saleDay;
    public String saleUrl;
}
