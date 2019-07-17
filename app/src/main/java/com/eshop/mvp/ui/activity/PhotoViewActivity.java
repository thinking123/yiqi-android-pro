package com.eshop.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.di.component.AppComponent;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.mvp.utils.AppConstant;

import butterknife.BindView;

public class PhotoViewActivity extends BaseSupportActivity {


    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_photo_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(AppConstant.ActivityIntent.IMAGE_URL);
        Glide.with(mContext).load(url).into(photoView);
    }
}
