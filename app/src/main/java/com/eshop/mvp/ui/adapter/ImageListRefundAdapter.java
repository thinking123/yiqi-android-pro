package com.eshop.mvp.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.ui.activity.PhotoViewActivity;
import com.eshop.mvp.utils.AppConstant;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/3/27 下午3:08
 * @Package com.eshop.mvp.ui.adapter
 **/
public class ImageListRefundAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageListRefundAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_item_image_list_refund, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView itemView = helper.getView(R.id.iv_item);
        itemView.setOnClickListener(v -> {

            Intent intent = new Intent(mContext, PhotoViewActivity.class);
            intent.putExtra(AppConstant.ActivityIntent.IMAGE_URL, item);
            mContext.startActivity(intent);

        });

        Glide.with(mContext).load(item)
                .into(itemView);


    }

}
