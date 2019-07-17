package com.eshop.mvp.ui.adapter;

import android.view.ViewGroup;

import com.eshop.mvp.http.entity.category.Category;
import com.zkteam.discover.base.ExRvAdapterBase;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.util.CollectionUtil;
import com.zkteam.discover.vh.DiscoverIndexLevel1ViewHolder;

import java.util.List;

/**
 * 左边分类
 * ===========================================================
 */
public class Leve1Adapter extends ExRvAdapterBase<Category, IndexLevel1ViewHolder> {

    // 选中
    public static final String STATUS_SELECT = "SELECT";
    // 默认
    public static final String STATUS_NOMAL = "NOMAL";
    // 当前索引
    private int mSelectPosition;

    public void setSelectPos(int position) {

        if (position >= 0 && position < getDataItemCount() && mSelectPosition != position) {

            notifyItemRangeChanged(mSelectPosition, 1, STATUS_NOMAL);
            notifyItemRangeChanged(position, 1, STATUS_SELECT);
            mSelectPosition = position;
        }
    }

    @Override
    public IndexLevel1ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {

        return new IndexLevel1ViewHolder(parent);
    }

    @Override
    public void onBindDataViewHolder(IndexLevel1ViewHolder holder, int dataPos) {

        holder.invalidateView(getDataItem(dataPos), dataPos == mSelectPosition);
    }

    @Override
    public void onBindDataViewHolder(IndexLevel1ViewHolder holder, int dataPos, List<Object> payLoads) {

        if (CollectionUtil.isEmpty(payLoads))
            return;

        String payload = (String) payLoads.get(0);
        if (STATUS_SELECT.equalsIgnoreCase(payload))
            holder.setSelectedStyle();  //选中状态
        else if (STATUS_NOMAL.equalsIgnoreCase(payload))
            holder.setNormalStyle();   // 默认状态
    }
}
