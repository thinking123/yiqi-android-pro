package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.store.BankCard;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class BankSetQuickAdapter extends BaseQuickAdapter<BankCard, BaseViewHolder> {

    public BankSetQuickAdapter(@Nullable List<BankCard> data) {
        super(R.layout.adapter_item_bank, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCard item) {

        helper.setText(R.id.tv_name, item.bank);

        helper.setText(R.id.num, item.accountnumber);

        helper.getView(R.id.del).setOnClickListener(v -> {
            if (onClickDelListener != null) onClickDelListener.onClick(item);
        });

    }

    public interface OnClickDelListener {
        void onClick(BankCard item);
    }

    private OnClickDelListener onClickDelListener;

    public void setOnClickDelListener(OnClickDelListener onClickDelListener) {
        this.onClickDelListener = onClickDelListener;
    }
}
