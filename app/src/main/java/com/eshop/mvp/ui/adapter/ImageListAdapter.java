package com.eshop.mvp.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.ui.activity.PhotoViewActivity;
import com.eshop.mvp.utils.AppConstant;

import java.util.List;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/5 下午3:08
 * @Package com.eshop.mvp.ui.adapter
 **/
public class ImageListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageListAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_item_image_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView itemView = helper.getView(R.id.iv_item);
        itemView.setOnClickListener(v -> {
            if(!item.equalsIgnoreCase("+")) {
                Intent intent = new Intent(mContext, PhotoViewActivity.class);
                intent.putExtra(AppConstant.ActivityIntent.IMAGE_URL, item);
                mContext.startActivity(intent);
            }else{
                if(onClickItemListener!=null){
                    onClickItemListener.onClickItem("add",helper.getAdapterPosition());
                }
            }
        });
        if(!item.equalsIgnoreCase("+")) {
            Glide.with(mContext).load(item)
                    .into(itemView);
            helper.setGone(R.id.add,false);
            helper.setVisible(R.id.del,true);
        }else{
            Glide.with(mContext).load(R.drawable.bt_shape_edit)
                    .into(itemView);
            helper.setVisible(R.id.add,true);
            helper.setVisible(R.id.del,false);
        }

        helper.getView(R.id.del).setOnClickListener(v -> {
            if(onClickItemListener!=null){
                onClickItemListener.onClickItem("del",helper.getAdapterPosition());
            }
        });

    }

    public interface OnClickItemListener {
        void onClickItem(String type,int position);
    }

    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener l) {
        this.onClickItemListener = l;
    }

}
