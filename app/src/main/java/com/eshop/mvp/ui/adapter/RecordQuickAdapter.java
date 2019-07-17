package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.store.Transaction;
import com.eshop.mvp.http.entity.store.WithDraw;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/2/23
 * @Package com.eshop.mvp.ui.adapter
 **/


public class RecordQuickAdapter extends BaseQuickAdapter<WithDraw, BaseViewHolder> {

    public RecordQuickAdapter(@Nullable List<WithDraw> data) {
        super(R.layout.adapter_item_transaction, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithDraw item) {


        helper.setText(R.id.tv_name, item.quota);
        helper.setText(R.id.time, item.withdrawalTime);
        helper.setText(R.id.money, String.format("%s", item.tips));
    }
}
