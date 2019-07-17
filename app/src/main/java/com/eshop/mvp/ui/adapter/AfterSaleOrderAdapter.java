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
import com.eshop.mvp.http.entity.order.AfterSaleOrder;

/**
 * @Author shijun
 * @Data 2019/2/4
 * @Package com.eshop.mvp.ui.adapter
 **/
public class AfterSaleOrderAdapter extends BaseQuickAdapter<AfterSaleOrder.AfterSaleTabsBean.GoodsListBean, BaseViewHolder> {

    private String type = "client";
    private String state;
    public AfterSaleOrderAdapter(String type) {
        super(R.layout.adapter_item_aftersale_order);
        this.type = type;
    }


    @Override
    protected void convert(BaseViewHolder helper, AfterSaleOrder.AfterSaleTabsBean.GoodsListBean item) {
        if(type.equalsIgnoreCase("store")){
            helper.setText(R.id.tv_shop_name, "订单号:" +item.afterSaleTabsBean.getOrederId());
        }else {
            helper.setText(R.id.tv_shop_name, item.afterSaleTabsBean.getStreoName());
        }
        if(item.getImgUrl()!=null) {
            Glide.with(mContext)
                    .load(item.getImgUrl())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                    .into(((ImageView) helper.getView(R.id.iv_product)));
        }else{
            Glide.with(mContext)
                    .load(R.drawable.bt_shape_edit)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                    .into(((ImageView) helper.getView(R.id.iv_product)));
        }

        helper.setText(R.id.tv_product_title, item.getTitle());
      //  helper.setText(R.id.tv_product_subtitle, item.details);
        helper.setText(R.id.tv_product_price, String.format("%s元", item.getGoodsPrice()));
        helper.setText(R.id.all_price, String.format("¥%s元", item.afterSaleTabsBean.getTotalPrice()));
        helper.setText(R.id.tv_count, String.format("x%s", item.getGoodsAmount()));

        helper.setGone(R.id.top_bar, item.isHead);

        helper.setGone(R.id.bottom_bar,item.isFoot);

        reset(helper);

        state = item.afterSaleTabsBean.getState();

        switch (item.afterSaleTabsBean.getState()) {
            case "0"://
                if(!type.equalsIgnoreCase("store"))
                    helper.setText(R.id.tv_status, "售后完成");
                else
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                break;
            case "1"://等待商家处理
                if(!type.equalsIgnoreCase("store")) {
                    helper.setText(R.id.tv_status, "等待商家处理");
                }else{
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                    helper.getView(R.id.tv_process).setVisibility(View.VISIBLE);
                }
                break;
            case "2"://商家已处理
                if(!type.equalsIgnoreCase("store")) {
                    helper.getView(R.id.tv_back_goods).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_status, "商家已处理");
                }else{
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                    helper.getView(R.id.tv_process).setVisibility(View.VISIBLE);
                }
                break;
            case "3"://商家未同意
                if(!type.equalsIgnoreCase("store")) {
                    helper.getView(R.id.tv_phone).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_refund).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_status, "商家未同意");
                }else{
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                    helper.getView(R.id.tv_process).setVisibility(View.GONE);
                    helper.setVisible(R.id.tv_detail,true);
                }
                break;
            case "4"://物流中
                if(!type.equalsIgnoreCase("store")) {
                    helper.setText(R.id.tv_status, "物流中");
                    helper.setVisible(R.id.tv_look,true);
                   // helper.setVisible(R.id.tv_detail,true);
                }else{
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                }
                break;

            case "5"://退款中
                if(!type.equalsIgnoreCase("store")) {
                    helper.setText(R.id.tv_status, "退款中");
                    helper.setVisible(R.id.tv_confirm_receive,true);
                    helper.setVisible(R.id.tv_detail,true);

                }else{
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                }
                break;

            case "6"://退款完成
                if(!type.equalsIgnoreCase("store")) {
                    helper.setText(R.id.tv_status, "退款完成");
                }else{
                    helper.getView(R.id.tv_status).setVisibility(View.GONE);
                }
                break;

        }


        helper.getView(R.id.tv_back_goods).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("back_goods",item);
        });

        helper.getView(R.id.tv_refund).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("refund",item);
        });

        helper.getView(R.id.tv_phone).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("phone",item);
        });

        helper.getView(R.id.tv_process).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("precess",item);
        });

        helper.getView(R.id.tv_look).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("look",item);
        });

        helper.getView(R.id.tv_detail).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("detail",item);
        });

        helper.getView(R.id.tv_confirm_receive).setOnClickListener(v -> {
            if (onClickChooseListener != null) onClickChooseListener.onClick("confirm",item);
        });





    }

    private void reset(BaseViewHolder helper){
        helper.getView(R.id.tv_process).setVisibility(View.GONE);
        helper.getView(R.id.tv_back_goods).setVisibility(View.GONE);
        helper.getView(R.id.tv_refund).setVisibility(View.GONE);
        helper.getView(R.id.tv_phone).setVisibility(View.GONE);
        helper.setGone(R.id.tv_look,false);
        helper.setGone(R.id.tv_detail,false);
        helper.setGone(R.id.tv_confirm_receive,false);


    }

    public interface OnClickChooseListener {
        void onClick(String action, AfterSaleOrder.AfterSaleTabsBean.GoodsListBean item);
    }

    private OnClickChooseListener onClickChooseListener;

    public void setOnClickChooseListener(OnClickChooseListener onClickChooseListener) {
        this.onClickChooseListener = onClickChooseListener;
    }
}
