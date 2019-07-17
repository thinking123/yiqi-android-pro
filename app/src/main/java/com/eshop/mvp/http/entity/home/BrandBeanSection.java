package com.eshop.mvp.http.entity.home;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.eshop.mvp.http.entity.cart.AppGoods;

/**
 * 品牌
 * @Author shijun
 * @Data 2019/1/22
 * @Package com.eshop.mvp.http.entity.home
 **/
public class BrandBeanSection extends SectionEntity<BrandBean> {
    private boolean isMore;
    public BrandBeanSection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public BrandBeanSection(BrandBean t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
