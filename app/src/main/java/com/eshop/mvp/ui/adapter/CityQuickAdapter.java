package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.ship.CityBean;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class CityQuickAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {

    public CityQuickAdapter(@Nullable List<CityBean> data) {
        super(R.layout.adapter_item_city, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean item) {

        helper.setText(R.id.tv_name, item.cityCaption);

    }
}
