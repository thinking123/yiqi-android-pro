package com.eshop.mvp.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.eshop.mvp.ui.widget.AmountView;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/26
 * @Package com.eshop.mvp.ui.adapter
 **/
public class CreateOrderAdapter extends BaseQuickAdapter<AppGoods, BaseViewHolder> {
    public CreateOrderAdapter() {
        super(R.layout.adapter_item_create_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppGoods item) {
        helper.setText(R.id.tv_shop_name, item.streoName);

        helper.setText(R.id.tv_product_title, item.title);
        helper.setText(R.id.tv_product_subtitle, item.details);
        helper.setText(R.id.tv_product_price, String.format("%s", item.unitPrice));
        helper.setText(R.id.tv_count, String.format("x %s", item.goodNum));
        Glide.with(mContext)
                .load(item.imgUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                .into(((ImageView) helper.getView(R.id.iv_product)));
        {
            AmountView amountView = helper.getView(R.id.amount_view);
            amountView.setMaxValue(100);
            amountView.setCurrentAmount(item.goodNum);
            amountView.setOnAmountChangeListener((view, amount) -> {
                item.goodNum = amount;
                notifyDataSetChanged();
                if (onClickCartItemListener != null)
                    onClickCartItemListener.onClickAmountCount(view, item, amount);
            });

        }

        if(item.freightState==0){ //卖家承担运费
            helper.setText(R.id.fright,"快递 包邮");
            helper.setText(R.id.fright_price,"");

        }else{
            helper.setText(R.id.fright,"快递 运费");
            helper.setText(R.id.fright_price,"¥"+item.freight);
        }

        helper.setGone(R.id.store_lay, item.isHead);

        helper.setGone(R.id.remark_lay, item.isFoot);

        helper.setGone(R.id.month, item.isMonth);

        ((EditText) helper.getView(R.id.remark)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                item.remark = "";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.remark = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public interface OnClickCartItemListener {
        void onClickAmountCount(View view, AppGoods appGoods, int count);

    }

    private CreateOrderAdapter.OnClickCartItemListener onClickCartItemListener;

    public void setOnClickCartItemListener(CreateOrderAdapter.OnClickCartItemListener onClickCartItemListener) {
        this.onClickCartItemListener = onClickCartItemListener;
    }
}
