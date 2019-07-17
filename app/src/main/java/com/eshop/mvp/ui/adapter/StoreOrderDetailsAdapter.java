package com.eshop.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.order.OrderDetail;

/**
 * @Author shijun
 * @Data 2019/1/26
 * @Package com.eshop.mvp.ui.adapter
 **/
public class StoreOrderDetailsAdapter extends BaseQuickAdapter<OrderDetail, BaseViewHolder> {
    public StoreOrderDetailsAdapter() {
        super(R.layout.adapter_item_store_order_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetail item) {
        helper.setText(R.id.tv_shop_name, item.appgoods.streoName);

        helper.setText(R.id.tv_product_title, item.appgoods.title);
        helper.setText(R.id.tv_product_subtitle, item.appgoods.details);
        helper.setText(R.id.tv_product_price, String.format("%s", item.appgoods.unitPrice));
        helper.setText(R.id.tv_count, String.format("x %s", item.goodsAmount));
        Glide.with(mContext)
                .load(item.appgoods.imgUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                .into(((ImageView) helper.getView(R.id.iv_product)));
       helper.setText(R.id.price,item.goodsPrice+"");

        if(item.freightState==0){ //卖家承担运费
            helper.setText(R.id.fright,"快递 包邮");
            helper.setText(R.id.fright_price,"");

        }else{
            helper.setText(R.id.fright,"快递 运费");
            helper.setText(R.id.fright_price,"¥"+item.freight);
        }

        helper.setGone(R.id.store_lay, item.appgoods.isHead);

        helper.setGone(R.id.remark_lay, item.appgoods.isFoot);

        helper.setGone(R.id.month, item.appgoods.appClassId==33);

        helper.setText(R.id.remark,item.remarks);

        helper.setGone(R.id.num_lay, item.appgoods.isFoot);

        helper.setText(R.id.all_num,"共计"+item.sum+"件商品");
        helper.setText(R.id.num,"¥"+item.small_sum+"");

        helper.getView(R.id.fright).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("fright",item.appgoods);
        });



    }

    public interface OnClickChooseListener {
        void onClick(String action, AppGoods item);
    }

    private StoreOrderAdapter.OnClickChooseListener onClickChooseListener;

    public void setOnClickChooseListener(StoreOrderAdapter.OnClickChooseListener onClickChooseListener) {
        this.onClickChooseListener = onClickChooseListener;
    }
}
