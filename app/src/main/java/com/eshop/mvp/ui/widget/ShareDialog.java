package com.eshop.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bin.david.dialoglib.BaseDialog;
import com.eshop.R;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.ui.adapter.SubCatLineQuickAdapter;
import com.eshop.mvp.utils.DensityUtil;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */

public class ShareDialog {

    private View popView;
    private BaseDialog dialog;

    private boolean isFillHeight;
    private BaseDialog.Builder builder;

    private View.OnClickListener listener;

    private View friend;
    private View share;
    private View commit;

    public ShareDialog(View.OnClickListener listener, BaseDialog.Builder builder) {
        this.listener = listener;
        this.builder = builder;
    }


    public void show(Context context) {

        if (popView == null) {
            popView = View.inflate(context, R.layout.pop_base_wx, null);

        }
        friend = popView.findViewById(R.id.wxfriend);
        share = popView.findViewById(R.id.wxshare);
        commit = popView.findViewById(R.id.btnCommit);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)listener.onClick(v);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)listener.onClick(v);
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)listener.onClick(v);
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

        window.setAttributes(params);
        dialog.show();
    }


    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
