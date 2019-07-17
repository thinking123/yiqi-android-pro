package com.eshop.mvp.ui.adapter;


import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.cart.CartBean;
import com.eshop.mvp.http.entity.cart.CartStore;
import com.eshop.mvp.http.entity.cart.StoreBean;
import com.eshop.mvp.ui.widget.AmountView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.mvp.ui.adapter
 **/
public class AppCartSectionAdapter extends BaseSectionQuickAdapter<AppGoodsSection, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public AppCartSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final AppGoodsSection itemSection) {
       // helper.setText(R.id.header, item.header);
       // helper.setVisible(R.id.more, item.isMore());
       // helper.addOnClickListener(R.id.more);
        AppGoods item = itemSection.t;
        helper.setText(R.id.tv_product_title, item.streoName);
       // helper.setChecked(R.id.btn_checked,item.isChecked);



        if(item.isMonth)helper.setVisible(R.id.month,true);
        else helper.setVisible(R.id.month,false);

        CheckBox checkBox1 = helper.getView(R.id.btn_checked);
        checkBox1.setOnCheckedChangeListener(null);
        checkBox1.setChecked(item.isChecked);

        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.isChecked=isChecked;
            if (onClickCartItemListener != null) {
                onClickCartItemListener.onAllChecked(isAllChecked(), isAllChecked(item),item,true);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新操作
                        notifyDataSetChanged();
                    }
                });

            }
        });
    }


    @Override
    protected void convert(BaseViewHolder helper, AppGoodsSection itemSection) {
        AppGoods item = itemSection.t;
        helper.setText(R.id.tv_product_title, item.title);
        helper.setText(R.id.tv_product_subtitle, item.details);
        helper.setText(R.id.tv_product_price, String.format("%s", item.unitPrice));
        Glide.with(mContext)
                .load(item.imgUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                .into(((ImageView) helper.getView(R.id.iv_product)));
        {
            AmountView amountView = helper.getView(R.id.amount_view);
            amountView.setMaxValue(100);
            amountView.setCurrentAmount(item.goodNum);
            amountView.setOnAmountChangeListener((view, amount) -> {
                if (onClickCartItemListener != null)
                    onClickCartItemListener.onClickAmountCount(view, item, amount);
            });
        }
        {

            CheckBox checkBox = helper.getView(R.id.btn_checked);

            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(item.isChecked);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.isChecked=isChecked;
                if (onClickCartItemListener != null) {
                    onClickCartItemListener.onAllChecked(isAllChecked(), isAllChecked(item),item,false);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            // 刷新操作
                            notifyDataSetChanged();
                        }
                    });

                }
            });
        }

        helper.getView(R.id.iv_product).setOnClickListener(v -> {
            /**  Intent intent = new Intent(mContext, ProductDetailsActivity.class);
             Bundle extras = new Bundle();
             extras.putSerializable(AppConstant.ActivityIntent.BEAN, item.getProductVo());
             intent.putExtras(extras);
             mContext.startActivity(intent);*/
        });

    }

    /**是否所有商品都选了*/
    public boolean isAllChecked() {
        boolean isAllChecked = true;
        for (AppGoodsSection cb : getData()) {
            if (!cb.t.isChecked ) {
                isAllChecked = false;
                break;
            }
        }
        return isAllChecked;
    }

    /**是否某个店铺商品都选了*/
    public boolean isAllChecked(AppGoods appGoods) {
        boolean isAllChecked = true;
        for (AppGoodsSection cb : getData()) {
            if (!cb.t.isChecked && cb.isHeader == false && appGoods.storeId == cb.t.storeId) {
                isAllChecked = false;
                break;
            }
        }
        return isAllChecked;
    }

    public void setAllChecked(AppGoods appGoods,boolean isChecked) {

        for (AppGoodsSection cb : getData()) {
            if(cb.t.storeId==appGoods.storeId)
                cb.t.isChecked=isChecked;
        }

        notifyDataSetChanged();
    }

    public void setAllChecked(boolean isChecked) {

        for (AppGoodsSection cb : getData()) {
                cb.t.isChecked=isChecked;
        }

        notifyDataSetChanged();
    }

    public List<AppGoods> getAllCheckedAppGoods() {
        List<AppGoods> appGoods = new ArrayList<>();
        for (AppGoodsSection cb : getData()) {
            if(cb.t.isChecked)appGoods.add(cb.t);
        }
        return appGoods;
    }

    public interface OnClickCartItemListener {
        void onClickAmountCount(View view, AppGoods appGoods, int count);

        void onAllChecked(boolean isAllChecked, boolean isStoreAllChecked,AppGoods appGoods, boolean isHead);
    }

    private AppCartSectionAdapter.OnClickCartItemListener onClickCartItemListener;

    public void setOnClickCartItemListener(AppCartSectionAdapter.OnClickCartItemListener onClickCartItemListener) {
        this.onClickCartItemListener = onClickCartItemListener;
    }
}
