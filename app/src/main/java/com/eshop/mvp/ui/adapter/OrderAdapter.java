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
import com.eshop.mvp.http.entity.order.OrderBean;
import com.eshop.mvp.http.entity.order.OrderItemBean;
import com.eshop.mvp.http.entity.order.OrderShopBean;

/**
 * @Author shijun
 * @Data 2019/2/4
 * @Package com.eshop.mvp.ui.adapter
 **/
public class OrderAdapter extends BaseQuickAdapter<AppGoods, BaseViewHolder> {
    public OrderAdapter() {
        super(R.layout.adapter_item_order);
    }


    @Override
    protected void convert(BaseViewHolder helper, AppGoods item) {

        //test
        //item.orderStatus =3;

        helper.setText(R.id.tv_shop_name, item.streoName);
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
        helper.getView(R.id.tv_cancel_order).setVisibility(View.GONE);
//        helper.getView(R.id.tv_friend_pay).setVisibility(View.GONE);
        helper.getView(R.id.tv_delete).setVisibility(View.GONE);

        helper.setGone(R.id.top_bar, item.isHead);

        helper.setGone(R.id.bottom_bar,item.isFoot);

        if(item.appClassId==33)helper.setVisible(R.id.month,true);
        else helper.setGone(R.id.month,false);

        reset(helper);

        /**
         * 订单状态 0待付款1 待发货，2待收货，3已完成，4已取消 5售后 ,
         */
        switch (item.orderStatus) {

            case 0://待付款
                helper.getView(R.id.tv_pay).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_cancel_order).setVisibility(View.VISIBLE);
               // helper.getView(R.id.tv_friend_pay).setVisibility(View.VISIBLE);
                helper.setGone(R.id.tv_express,false);
                helper.setText(R.id.tv_status, "待付款");
                break;
            case 1://待发货
                if(item.appClassId!=33) {
                    helper.getView(R.id.tv_back_money).setVisibility(View.VISIBLE);
                }else{
                    helper.setGone(R.id.tv_back_money,false);
                }
                helper.setGone(R.id.tv_express,false);
                helper.getView(R.id.tv_tip_send).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "已付款");

                if(item.reminderShipment==0) helper.setText(R.id.tv_tip_send, "已提醒");
                else helper.setText(R.id.tv_tip_send, "提醒发货");

                break;
            case 2://待收货
                if(item.appClassId!=33) {
                    helper.getView(R.id.tv_back_money).setVisibility(View.VISIBLE);
                }else{
                    helper.setGone(R.id.tv_back_money,false);
                }
                helper.setVisible(R.id.tv_express,true);
                helper.getView(R.id.tv_confirm_receive).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "待收货");
                break;
            case 3://已完成
                helper.setGone(R.id.tv_express,false);
                helper.getView(R.id.tv_re_buy).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_status, "已完成");
                break;
            case 4://已取消
                helper.setGone(R.id.tv_express,false);
                helper.setText(R.id.tv_status, "已取消");
                helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
                break;
            case 5://售后

                break;
        }




        helper.getView(R.id.tv_cancel_order).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("cancel",item);
        });

        helper.getView(R.id.tv_delete).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("delete",item);
        });

        helper.getView(R.id.tv_pay).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("pay",item);
        });

        helper.getView(R.id.tv_back_money).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("refund",item);
        });

        helper.getView(R.id.tv_tip_send).setOnClickListener(v -> {
            if(item.reminderShipment==1)
            if (onClickChooseListener != null) onClickChooseListener.onClick("tipsend",item);
        });

        helper.getView(R.id.tv_confirm_receive).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("receive",item);
        });

        helper.getView(R.id.tv_re_buy).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("rebuy",item);
        });

        helper.getView(R.id.tv_express).setOnClickListener(v -> {

                if (onClickChooseListener != null) onClickChooseListener.onClick("express",item);
        });





    }

    private void reset(BaseViewHolder helper){
        helper.getView(R.id.tv_re_buy).setVisibility(View.GONE);
        helper.getView(R.id.tv_back_money).setVisibility(View.GONE);
        helper.getView(R.id.tv_confirm_receive).setVisibility(View.GONE);
        helper.getView(R.id.tv_tip_send).setVisibility(View.GONE);
        helper.getView(R.id.tv_pay).setVisibility(View.GONE);
        helper.getView(R.id.tv_cancel_order).setVisibility(View.GONE);
        helper.getView(R.id.tv_delete).setVisibility(View.GONE);

    }

    public interface OnClickChooseListener {
        void onClick(String action,AppGoods item);
    }

    private OnClickChooseListener onClickChooseListener;

    public void setOnClickChooseListener(OnClickChooseListener onClickChooseListener) {
        this.onClickChooseListener = onClickChooseListener;
    }
}
