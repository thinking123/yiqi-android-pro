package com.eshop.mvp.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Author shijun
 * @Data 2019/3/21
 * @Package com.eshop.mvp.ui.widget
 **/


public class GoodsItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public GoodsItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);

                outRect.bottom = space;

    }
}
