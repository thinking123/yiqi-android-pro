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
import com.eshop.mvp.http.entity.store.AuditGoods;
import com.eshop.mvp.http.entity.store.Transaction;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/2/23
 * @Package com.eshop.mvp.ui.adapter
 **/


public class TransactionQuickAdapter extends BaseQuickAdapter<Transaction, BaseViewHolder> {

    public TransactionQuickAdapter(@Nullable List<Transaction> data) {
        super(R.layout.adapter_item_transaction, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Transaction item) {


        helper.setText(R.id.tv_name, item.goodsName);
        helper.setText(R.id.time, item.time);
        helper.setText(R.id.money, String.format("%s", item.price));
    }
}
