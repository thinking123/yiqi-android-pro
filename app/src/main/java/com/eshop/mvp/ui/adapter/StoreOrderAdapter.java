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

/**
 * @Author shijun
 * @Data 2019/2/4
 * @Package com.eshop.mvp.ui.adapter
 **/
public class StoreOrderAdapter extends BaseQuickAdapter<AppGoods, BaseViewHolder> {
    public StoreOrderAdapter() {
        super(R.layout.adapter_item_store_order);
    }


    @Override
    protected void convert(BaseViewHolder helper, AppGoods item) {

        helper.setText(R.id.tv_shop_name, "订单号:"+item.orderId);
        Glide.with(mContext)
                .load(item.imgUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                .into(((ImageView) helper.getView(R.id.iv_product)));

        helper.setText(R.id.tv_product_title, item.title);
        helper.setText(R.id.tv_product_subtitle, item.details);
        helper.setText(R.id.tv_product_price, String.format("%s元", item.unitPrice));
        helper.setText(R.id.all_price, String.format("¥%s元", item.totalPrice));
        helper.setText(R.id.tv_count, String.format("x%s", item.goodNum));

        helper.getView(R.id.tv_pay).setVisibility(View.GONE);

        helper.setGone(R.id.top_bar, item.isHead);

        helper.setGone(R.id.bottom_bar,item.isFoot);

        reset(helper);
        /**
         * 订单状态 0待付款1 待发货，2待收货，3已完成，4已取消 5售后 ,
         */
        switch (item.orderStatus) {
           // case 0://已完成

          //      helper.setText(R.id.tv_status, "已关闭");
          //      break;
            case 0://未付款
                helper.getView(R.id.tv_change_price).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_pay).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "买家未付款");
                break;
            case 1://新订单
                helper.getView(R.id.tv_tip_send).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "买家已付款");
                break;
            case 2://未完成
                helper.getView(R.id.tv_confirm_receive).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "运输中");
                break;
            case 3://已完成
                helper.setText(R.id.tv_status, "已完成");
                break;
            case 4://已取消

                break;
            case 5://售后

                break;
        }




        helper.getView(R.id.tv_pay).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("pay",item);
        });

        helper.getView(R.id.tv_tip_send).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("tipsend",item);
        });

        helper.getView(R.id.tv_confirm_receive).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("express",item);
        });

        helper.getView(R.id.tv_change_price).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("price",item);
        });




    }

    private void reset(BaseViewHolder helper){
        helper.getView(R.id.tv_confirm_receive).setVisibility(View.GONE);
        helper.getView(R.id.tv_tip_send).setVisibility(View.GONE);
        helper.getView(R.id.tv_pay).setVisibility(View.GONE);
        helper.getView(R.id.tv_change_price).setVisibility(View.GONE);

    }

    public interface OnClickChooseListener {
        void onClick(String action, AppGoods item);
    }

    private OnClickChooseListener onClickChooseListener;

    public void setOnClickChooseListener(OnClickChooseListener onClickChooseListener) {
        this.onClickChooseListener = onClickChooseListener;
    }
}
