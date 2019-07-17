package com.eshop.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.order.ExpressTime;
import com.eshop.mvp.http.entity.order.OrderDetail;

/**
 * @Author shijun
 * @Data 2019/1/26
 * @Package com.eshop.mvp.ui.adapter
 **/
public class ExpressAdapter extends BaseQuickAdapter<ExpressTime, BaseViewHolder> {
    public ExpressAdapter() {
        super(R.layout.adapter_item_express);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpressTime item) {
        helper.setText(R.id.msg,item.getContext());
        helper.setText(R.id.ftime,item.getFtime());
        if(item.position==0){
            helper.setVisible(R.id.top_progress,true);
            helper.setGone(R.id.sub_progress,false);
        }else{
            helper.setVisible(R.id.sub_progress,true);
            helper.setGone(R.id.top_progress,false);
        }

    }

}
