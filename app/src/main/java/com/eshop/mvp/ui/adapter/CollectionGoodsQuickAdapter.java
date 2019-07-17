package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.store.AuditGoods;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/2/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class CollectionGoodsQuickAdapter extends BaseQuickAdapter<HomeGoodBean, BaseViewHolder> {

    public CollectionGoodsQuickAdapter(@Nullable List<HomeGoodBean> data) {
        super(R.layout.adapter_item_store_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeGoodBean item) {

        Glide.with(mContext)
                .load(item.imgUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                .into(((ImageView) helper.getView(R.id.iv_product)));
        helper.setText(R.id.tv_product_name, item.title);
        helper.setText(R.id.tv_product_price, String.format("%s", item.unitPrice));
    }
}
