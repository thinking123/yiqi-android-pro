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
import com.eshop.mvp.http.entity.product.Store;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/2/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class CollectionStoreQuickAdapter extends BaseQuickAdapter<Store, BaseViewHolder> {

    public CollectionStoreQuickAdapter(@Nullable List<Store> data) {
        super(R.layout.adapter_item_collection_store, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Store item) {

        Glide.with(mContext)
                .load(item.storeImg)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                .into(((ImageView) helper.getView(R.id.iv_product)));
        helper.setText(R.id.tv_product_name, item.streoName);
    }
}
