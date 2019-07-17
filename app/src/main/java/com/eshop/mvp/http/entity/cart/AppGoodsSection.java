package com.eshop.mvp.http.entity.cart;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 购物车商品
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.mvp.http.entity.cart
 **/
public class AppGoodsSection extends SectionEntity<AppGoods> {
    private boolean isMore;
    public AppGoodsSection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public AppGoodsSection(AppGoods t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
