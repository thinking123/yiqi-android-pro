package com.eshop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.order.OrderItemBean;

/**
 * @Author shijun
 * @Data 2019/1/26
 * @Package com.eshop.mvp.ui.adapter
 **/
public class CreateOrderItemAdapter extends BaseQuickAdapter<AppGoods, BaseViewHolder> {
    public CreateOrderItemAdapter() {
        super(R.layout.adapter_item_create_order_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppGoods item) {
        helper.setText(R.id.tv_product_title, item.title);
        helper.setText(R.id.tv_product_subtitle, item.details);
        helper.setText(R.id.tv_product_price, String.format("%s", item.unitPrice));
        helper.setText(R.id.tv_count, String.format("* %s", item.goodNum));
        Glide.with(mContext)
                .load(item.imgUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                .into(((ImageView) helper.getView(R.id.iv_product)));
    }
}
