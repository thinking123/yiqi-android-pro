package com.eshop.mvp.ui.widget;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bin.david.dialoglib.BaseDialog;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.ui.adapter.SubCatLineQuickAdapter;
import com.eshop.mvp.utils.DensityUtil;

import java.util.List;

import javax.inject.Inject;

import per.goweii.anylayer.Alignment;
import per.goweii.anylayer.AnimHelper;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.LayerManager;

/**
 *
 */

public class SubCatMenu {

    private View popView;

    @Inject
    SubCatLineQuickAdapter subCatQuickAdapter;

    private int selectPosition =-1;
    private boolean isFillHeight;

    private OnCheckChangeListener listener;

    public SubCatMenu(OnCheckChangeListener listener) {
        this.listener = listener;

    }

    /**
     * 显示
     * @param context 上下文
     * @param isNewData 是否是新数据
     * @param list 数据源
     */
    public void show(Context context, boolean isNewData, List<CatBean> list,View target){
        show(context,isNewData?-1:selectPosition,list,target);
    }


    /**
     * 显示
     * @param context 上下文
     * @param defaultPosition 默认选中的位置
     * @param list 数据源
     */
    public void show(Context context, int defaultPosition, List<CatBean> list,View target){
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
                       // dismiss();
                        View contain = popView.findViewById(R.id.container);
                        contain.callOnClick();
                    }
                },30);
                    }
            );
            recyclerView.setAdapter(subCatQuickAdapter);

        }else{
            subCatQuickAdapter.setNewData(list);
            subCatQuickAdapter.notifyDataSetChanged();
        }

        AnyLayer.target(target)
                .contentView(popView)
                .alignment(Alignment.Direction.VERTICAL, Alignment.Horizontal.CENTER, Alignment.Vertical.BELOW, true)
                .backgroundColorRes(R.color.dialog_bg)
                .gravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
                .onClickToDismiss(R.id.container)
                .onClick(R.id.container, new LayerManager.OnLayerClickListener() {
                    @Override
                    public void onClick(AnyLayer anyLayer, View v) {
                        anyLayer.dismiss();
                    }
                })
                .onLayerDismissListener(new LayerManager.OnLayerDismissListener() {
                    @Override
                    public void onDismissing(AnyLayer anyLayer) {

                    }

                    @Override
                    public void onDismissed(AnyLayer anyLayer) {
                         listener.onItemClick(subCatQuickAdapter.getData().get(0),-1);
                    }
                })
                .contentAnim(new LayerManager.IAnim() {
                    @Override
                    public Animator inAnim(View content) {
                        return AnimHelper.createTopInAnim(content);
                    }

                    @Override
                    public Animator outAnim(View content) {
                        return AnimHelper.createTopOutAnim(content);
                    }
                })
                .show();


    }



    public  interface OnCheckChangeListener{
        void onItemClick(CatBean catBean, int position);
    }

}
