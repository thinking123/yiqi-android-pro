package com.eshop.mvp.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/11 下午7:30
 * @Package com.eshop.mvp.ui.widget
 **/


public class RecommendItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public RecommendItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if (position!=0){
            if (position % 2 == 1) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;
            } else {
                outRect.right = space;
                outRect.bottom = space;
            }
        }
    }
}
