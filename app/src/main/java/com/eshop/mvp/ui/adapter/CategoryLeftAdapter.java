package com.eshop.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.CategoryBean;

/**
 * @Author shijun
 * @Data 2019/1/16
 * @Package com.eshop.mvp.ui.adapter
 **/
public class CategoryLeftAdapter extends BaseQuickAdapter<CatBean, BaseViewHolder> {

    private int selectedPosition = 0;

    public CategoryLeftAdapter() {
        super(R.layout.adapter_item_left_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, CatBean item) {
        TextView tvContent = helper.getView(R.id.tv_item);
        tvContent.setText(item.categoryName);
        helper.setText(R.id.tv_item, item.categoryName);
        if (helper.getAdapterPosition() == selectedPosition) {
            tvContent.getPaint().setFakeBoldText(true);
            tvContent.setTextColor(mContext.getResources().getColor(R.color.black));
            helper.getView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.normal_back_ground));
            helper.getView(R.id.iv_select).setVisibility(View.VISIBLE);
        } else {
            tvContent.getPaint().setFakeBoldText(false);
            tvContent.setTextColor(mContext.getResources().getColor(R.color.color_9999));
            helper.getView(R.id.ll_item).setBackgroundColor(mContext.getResources().getColor(R.color.divider_color));
            helper.getView(R.id.iv_select).setVisibility(View.GONE);
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }
}
