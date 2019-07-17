package com.eshop.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.order.OrderBean;
import com.eshop.mvp.http.entity.product.StoreCat;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.ui.adapter
 **/


public class CatSetQuickAdapter extends BaseQuickAdapter<StoreCat, BaseViewHolder> {

    public CatSetQuickAdapter(@Nullable List<StoreCat> data) {
        super(R.layout.adapter_item_catset, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreCat item) {

        helper.setText(R.id.tv_name, item.categoryName);

        helper.getView(R.id.btn_del).setOnClickListener(v -> {
            if (onClickDelListener != null) onClickDelListener.onClick(item);
        });

        if(item.categoryState!=1){

            helper.setVisible(R.id.tip,true);
            helper.setText(R.id.tip,item.auditingInfo);
            helper.setVisible(R.id.btn_del,true);
            if(item.categoryState==2){
                helper.setText(R.id.btn_del,"审核不通过");
            }else{
                helper.setText(R.id.btn_del,"待审核");
            }
        }else{
            helper.setGone(R.id.btn_del,false);

        }

    }

    public interface OnClickDelListener {
        void onClick(StoreCat item);
    }

    private OnClickDelListener onClickDelListener;

    public void setOnClickDelListener(OnClickDelListener onClickDelListener) {
        this.onClickDelListener = onClickDelListener;
    }
}
