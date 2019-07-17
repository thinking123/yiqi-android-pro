package com.eshop.mvp.ui.widget;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bin.david.dialoglib.BaseDialog;
import com.eshop.R;

/**
 *
 */

public class ExampleDialog {

    private View popView;
    private BaseDialog dialog;

    private boolean isFillHeight = true;
    private BaseDialog.Builder builder;

    private OnChangeListener listener;

    private View content;
    private View btn_close;

    public ExampleDialog(OnChangeListener listener, BaseDialog.Builder builder) {
        this.listener = listener;
        this.builder = builder;
    }


    public void show(Context context, String imgurl, String id) {
        if (popView == null) {
            popView = View.inflate(context, R.layout.pop_base_example, null);

            content = popView.findViewById(R.id.content);
            btn_close = popView.findViewById(R.id.close);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(listener!=null){
                        listener.onItemClick(id,true);
                    }
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


