package com.eshop.mvp.ui.adapter;

import android.view.ViewGroup;

import com.eshop.mvp.http.entity.category.Category;
import com.zkteam.discover.base.ExRvAdapterBase;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.bean.Oper;
import com.zkteam.discover.util.CollectionUtil;
import com.zkteam.discover.util.DensityUtil;
import com.zkteam.discover.util.DimenConstant;
import com.zkteam.discover.vh.DiscoverIndexLevel2BannerViewHolder;
import com.zkteam.discover.vh.DiscoverIndexLevel2MiniViewHolder;
import com.zkteam.discover.vh.DiscoverIndexLevel2TitleViewHolder;
import com.zkteam.discover.vh.ExRvItemViewHolderEmpty;

/**
 右边二级分类
 */
public class Level2Adapter extends ExRvAdapterBase {


    public static final int TYPE_ITEM_TITLE = 0;     // Title 运营位
    public static final int TYPE_ITEM_WEBVIEW = 1;  // webView 运营位
    public static final int TYPE_ITEM_BANNER = 2;   // Banner 运营位
    private static final int TYPE_ITEM_NONE = 3;           // NONE

    /**
     * 查询指定elementId的运营位元素pos
     *
     * @param elementId
     * @return 未找到返回-1
     */
    public int getSelectPosition(int elementId) {

        for (int i = 0; i < CollectionUtil.size(getData()); i++) {

            Category category = (Category) CollectionUtil.getItem(getData(), i);
            if (category.getId().equalsIgnoreCase(elementId+""))
                return i;
        }
        return -1;
    }

    @Override
    public int getDataItemViewType(int dataPos) {

        Object obj = getDataItem(dataPos);
        if (obj instanceof Category) {

            Category oper = (Category) obj;

            if (oper.isTypeWebView())
                return TYPE_ITEM_WEBVIEW;
            else if (oper.isTypeTitle())
                return TYPE_ITEM_TITLE;
        }

        return TYPE_ITEM_NONE;
    }

    @Override
    public ExRvItemViewHolderBase onCreateDataViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_ITEM_TITLE:
                return new IndexLevel2TitleViewHolder(parent);
            case TYPE_ITEM_WEBVIEW:
                return new IndexLevel2MiniViewHolder(parent);
            default:
            case TYPE_ITEM_NONE:
                return ExRvItemViewHolderEmpty.newVertInstance(parent);
        }
    }

    @Override
    public void onBindDataViewHolder(ExRvItemViewHolderBase holder, int dataPos) {

        if (holder instanceof IndexLevel2TitleViewHolder)
            ((IndexLevel2TitleViewHolder) holder).invalidateView((Category) getDataItem(dataPos));
        else if (holder instanceof IndexLevel2MiniViewHolder)
            ((IndexLevel2MiniViewHolder) holder).invalidateView((Category) getDataItem(dataPos));
    }
}
