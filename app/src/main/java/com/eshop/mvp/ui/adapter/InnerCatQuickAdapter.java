package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.product.StoreCat;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class InnerCatQuickAdapter extends BaseQuickAdapter<StoreCat, BaseViewHolder> {

    public InnerCatQuickAdapter(@Nullable List<StoreCat> data) {
        super(R.layout.adapter_item_inner_cat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreCat item) {

        helper.setText(R.id.tv_name, item.categoryName);

        helper.getView(R.id.btn_del).setOnClickListener(v -> {
            if (onClickDelListener != null) onClickDelListener.onClick(item);
        });

    }

    public interface OnClickDelListener {
        void onClick(StoreCat item);
    }

    private OnClickDelListener onClickDelListener;

    public void setOnClickDelListener(OnClickDelListener onClickDelListener) {
        this.onClickDelListener = onClickDelListener;
    }
}
