package com.eshop.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bin.david.dialoglib.BaseDialog;
import com.bumptech.glide.Glide;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.ui.adapter.SubCatLineQuickAdapter;
import com.eshop.mvp.utils.DensityUtil;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */

public class AdDialog {

    private View popView;
    private BaseDialog dialog;

    private boolean isFillHeight = true;
    private BaseDialog.Builder builder;

    private OnChangeListener listener;

    private ImageView content;
    private View btn_close;

    public AdDialog(OnChangeListener listener, BaseDialog.Builder builder) {
        this.listener = listener;
        this.builder = builder;
    }


    public void show(Context context, String imgurl, String id) {
        if (popView == null) {
            popView = View.inflate(context, R.layout.pop_base_ad, null);

            content = (ImageView)popView.findViewById(R.id.content);

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick("",true);
                        dialog.dismiss();
                    }
                }
            });

            if(imgurl!=null){
                Glide.with(context).load(imgurl).into(content);
            }

            btn_close = popView.findViewById(R.id.close);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();

                }
            });

            if (dialog == null) {
                if (builder != null) {
                    dialog = builder.setContentView(popView).create();
                } else {
                    dialog = new BaseDialog.Builder(context)
                            .setFillWidth(true)
                            .setFillHeight(isFillHeight)
                            .setContentView(popView)
                            .create();
                }
            } else if (isFillHeight != this.isFillHeight) {
                dialog.notifyHeight(isFillHeight);
            }
            this.isFillHeight = isFillHeight;
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.x = 10;
            params.y = 10;

            window.setAttributes(params);
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public interface OnChangeListener {
        void onItemClick(String id, boolean isclose);
    }

}


