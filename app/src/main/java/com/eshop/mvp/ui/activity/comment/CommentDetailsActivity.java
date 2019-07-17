package com.eshop.mvp.ui.activity.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;

public class CommentDetailsActivity extends BaseSupportActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_comment_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
