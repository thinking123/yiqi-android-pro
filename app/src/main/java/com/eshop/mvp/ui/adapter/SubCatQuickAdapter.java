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
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class SubCatQuickAdapter extends BaseQuickAdapter<CatBean, BaseViewHolder> {

    public SubCatQuickAdapter(@Nullable List<CatBean> data) {
        super(R.layout.adapter_item_cat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CatBean item) {
        if(item.categoryIcon.equalsIgnoreCase(R.drawable.c8+"")){
            Glide.with(mContext)
                    .load(R.drawable.c8)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                    .into(((ImageView) helper.getView(R.id.iv_cat)));

        }else {
            Glide.with(mContext)
                    .load(item.categoryIcon)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                    .into(((ImageView) helper.getView(R.id.iv_cat)));
        }
        helper.setText(R.id.tv_name, item.categoryName);

    }
}
