package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.product.StoreCat;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class StoreCatQuickAdapter extends BaseQuickAdapter<StoreCat, BaseViewHolder> {

    public StoreCatQuickAdapter(@Nullable List<StoreCat> data) {
        super(R.layout.adapter_item_storecat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreCat item) {

        helper.setText(R.id.tv_name, item.categoryName);

    }
}
