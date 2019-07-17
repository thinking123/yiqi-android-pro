package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.ship.AddressBean;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class AddressQuickAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {

    public AddressQuickAdapter(@Nullable List<AddressBean> data) {
        super(R.layout.adapter_item_address, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {

        helper.setText(R.id.name_phone, item.receiveUserName+","+item.receivePhone);
        helper.setText(R.id.address,item.address);

        if(item.isDefault==1){
            helper.setImageResource(R.id.set,R.drawable.wancheng2);
            helper.setText(R.id.set_txt,"已设为默认");
            helper.setTextColor(R.id.set_txt,mContext.getResources().getColor(R.color.red_66));
        }else{
            helper.setImageResource(R.id.set,R.drawable.circle);
            helper.setText(R.id.set_txt,"设为默认");
            helper.setTextColor(R.id.set_txt,mContext.getResources().getColor(R.color.color_9999));
        }

        if(item.isSelected){
            helper.setVisible(R.id.use,false);
            helper.setVisible(R.id.wancheng,true);
        }else{
            helper.setVisible(R.id.use,true);
            helper.setVisible(R.id.wancheng,false);
        }

        helper.getView(R.id.del).setOnClickListener(v -> {
           if(onClickAddressItemListener!=null){
               onClickAddressItemListener.onClickItem("del",item);
           }
        });

        helper.getView(R.id.use).setOnClickListener(v -> {
            if(onClickAddressItemListener!=null){
                onClickAddressItemListener.onClickItem("use",item);
            }
        });

        helper.getView(R.id.edit).setOnClickListener(v -> {
            if(onClickAddressItemListener!=null){
                onClickAddressItemListener.onClickItem("edit",item);
            }
        });

        helper.getView(R.id.set_txt).setOnClickListener(v -> {
            if(onClickAddressItemListener!=null){
                onClickAddressItemListener.onClickItem("set",item);
            }
        });

    }

    public interface OnClickAddressItemListener {
        void onClickItem(String type, AddressBean address);
    }

    private AddressQuickAdapter.OnClickAddressItemListener onClickAddressItemListener;

    public void setOnClickAddressItemListener(AddressQuickAdapter.OnClickAddressItemListener l) {
        this.onClickAddressItemListener = l;
    }
}
