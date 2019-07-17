package com.eshop.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bin.david.dialoglib.BaseDialog;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.ui.activity.product.CatProductActivity;
import com.eshop.mvp.ui.adapter.SubCatLineQuickAdapter;
import com.eshop.mvp.utils.DensityUtil;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */

public class SubCatDialog {

    private View popView;
    private BaseDialog dialog;

    @Inject
    SubCatLineQuickAdapter subCatQuickAdapter;

    private int selectPosition =-1;
    private boolean isFillHeight;
    private BaseDialog.Builder builder;

    private OnCheckChangeListener listener;

    public SubCatDialog(OnCheckChangeListener listener,BaseDialog.Builder builder) {
        this.listener = listener;
        this.builder = builder;
    }

    /**
     * 显示
     * @param context 上下文
     * @param isNewData 是否是新数据
     * @param list 数据源
     */
    public void show(Context context, boolean isNewData, List<CatBean> list){
        show(context,isNewData?-1:selectPosition,list);
    }


    /**
     * 显示
     * @param context 上下文
     * @param defaultPosition 默认选中的位置
     * @param list 数据源
     */
    public void show(Context context, int defaultPosition, List<CatBean> list){
        selectPosition = defaultPosition;
        if(popView == null) {
            popView = View.inflate(context, R.layout.pop_base_subcat, null);

            final RecyclerView recyclerView = (RecyclerView) popView.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            recyclerView.addItemDecoration(new RecommendItemDecoration(10));

            subCatQuickAdapter = new SubCatLineQuickAdapter(list);

            subCatQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
                selectPosition = position;
                subCatQuickAdapter.notifyDataSetChanged();
                listener.onItemClick(subCatQuickAdapter.getData().get(position),position);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },30);
                    }
            );
            recyclerView.setAdapter(subCatQuickAdapter);

        }else{
            subCatQuickAdapter.setNewData(list);
            subCatQuickAdapter.notifyDataSetChanged();
        }
        boolean isFillHeight = BaseDialog.getScreen((Activity) context).getHeight()
                - list.size()* DensityUtil.dip2px(context,50) <= 0;
        if(dialog == null) {
            if(builder != null){
                dialog = builder.setContentView(popView).create();
            }else {
                dialog = new BaseDialog.Builder(context)
                        .setFillWidth(true)
                        .setFillHeight(isFillHeight)
                        .setContentView(popView)
                        .create();
            }
        }else if (isFillHeight != this.isFillHeight){
            dialog.notifyHeight(isFillHeight);
        }
        this.isFillHeight = isFillHeight;
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 10;
        params.y = 100;

        window.setAttributes(params);
        dialog.show();
    }



    public void  dismiss(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public  interface OnCheckChangeListener{
        void onItemClick(CatBean catBean, int position);
    }

}
