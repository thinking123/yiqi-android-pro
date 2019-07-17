package com.eshop.mvp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseSupportActivity {

    @BindView(R.id.cb_agree)
    CheckBox cbAgree;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        if (cbAgree.isChecked()) {
            SpUtils.put(this, AppConstant.FIRST_OPEN, true);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            ArmsUtils.snackbarText("请先同意权限声明");
        }
    }
}
