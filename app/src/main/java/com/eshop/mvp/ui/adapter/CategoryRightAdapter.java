package com.eshop.mvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.mvp.http.entity.category.CatBean;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CategoryBean;

import java.util.List;


/**
 * @Author shijun
 * @Data 2019/1/16
 * @Package com.eshop.mvp.ui.adapter
 **/
public class CategoryRightAdapter extends BaseQuickAdapter<CatBean, BaseViewHolder> {

    public CategoryRightAdapter() {
        super(R.layout.adapter_item_right_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, CatBean item) {

                helper.setText(R.id.tv_item, item.categoryName);
                if (!ArmsUtils.isEmpty(item.categoryIcon)) {
                    Glide.with(mContext)
                            .load(item.categoryIcon)
                            .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                            .into(((ImageView) helper.getView(R.id.iv_item)));
                }

    }

}
