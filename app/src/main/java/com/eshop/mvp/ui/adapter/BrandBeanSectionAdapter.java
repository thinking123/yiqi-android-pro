package com.eshop.mvp.ui.adapter;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.home.BrandBean;
import com.eshop.mvp.http.entity.home.BrandBeanSection;
import com.eshop.mvp.ui.activity.product.ProductListActivity;
import com.eshop.mvp.ui.widget.AmountView;

import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/22
 * @Package com.eshop.mvp.ui.adapter
 **/
public class BrandBeanSectionAdapter extends BaseSectionQuickAdapter<BrandBeanSection, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public BrandBeanSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final BrandBeanSection itemSection) {

        BrandBean item = itemSection.t;
        helper.setText(R.id.head, item.brandZm);

    }


    @Override
    protected void convert(BaseViewHolder helper, BrandBeanSection itemSection) {
        BrandBean item = itemSection.t;
        helper.setText(R.id.tvName, item.brandName);
        Glide.with(mContext).load(item.brandImg).into(((ImageView) helper.getView(R.id.ivBrand)));

        helper.getView(R.id.ivBrand).setOnClickListener(v -> {
             if(onClickCartItemListener!=null){
                 onClickCartItemListener.onClick(item);
             }
        });

    }


    public interface OnClickCartItemListener {
        void onClick(BrandBean brandBean);
    }

    private BrandBeanSectionAdapter.OnClickCartItemListener onClickCartItemListener;

    public void setOnClickCartItemListener(BrandBeanSectionAdapter.OnClickCartItemListener onClickCartItemListener) {
        this.onClickCartItemListener = onClickCartItemListener;
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).t.brandZm;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
