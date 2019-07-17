package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class SubCatLineQuickAdapter extends BaseQuickAdapter<CatBean, BaseViewHolder> {

    public SubCatLineQuickAdapter(@Nullable List<CatBean> data) {
        super(R.layout.adapter_item_subcat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CatBean item) {

        helper.setText(R.id.tv_name, item.categoryName);
        if(item.isselected)helper.setVisible(R.id.subline,true);
        else helper.setVisible(R.id.subline,false);

    }
}
