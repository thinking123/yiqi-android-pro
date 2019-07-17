package com.eshop.mvp.ui.activity.set;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerAppSetComponent;
import com.eshop.di.module.AppSetModule;
import com.eshop.mvp.contract.AppSetContract;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.presenter.AppSetPresenter;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.SpUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
//import io.rong.imkit.RongIM;

public class AppSetActivity extends BaseSupportActivity<AppSetPresenter> implements AppSetContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    @Inject
    AppManager appManager;
    private UserBean userbean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAppSetComponent.builder().appComponent(appComponent)
                .appSetModule(new AppSetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_app_set;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("个人设置");
        toolbarBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.userInfo();
        }
    }

    @OnClick({R.id.btn_logout, R.id.ll_name, R.id.ll_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                //SpUtils.put(mContext, AppConstant.Api.TOKEN, "");

                SpUtils.clear(mContext);

                startActivity(new Intent(this, LoginActivity.class));
                appManager.killAll(LoginActivity.class);
                break;
            case R.id.ll_name:
                View baseView = LayoutInflater.from(this).inflate(R.layout.dialog_view_edit, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("更改姓名");
                builder.setView(baseView);
                builder.setPositiveButton("修改", (dialog, which) -> {
                    String text = ((EditText) baseView.findViewById(R.id.et_content)).getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        showMessage("姓名不能为空!");
                        return;
                    }
                    if (mPresenter != null) {
                        mPresenter.updateInfo(text, null);
                    }
                });
                builder.create().show();
                break;
            case R.id.ll_sex:
                AlertDialog.Builder sexDialog = new AlertDialog.Builder(mContext);
                sexDialog.setTitle("选择性别");
                sexDialog.setItems(new String[]{"男", "女"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            if (userbean != null && userbean.getSex() == 0) return;
                            if (mPresenter != null) {
                                mPresenter.updateInfo(null, 0);
                            }
                            break;
                        case 1:
                            if (userbean != null && userbean.getSex() == 1) return;
                            if (mPresenter != null) {
                                mPresenter.updateInfo(null, 1);
                            }
                            break;
                    }
                });
                sexDialog.create().show();
                break;
        }

    }

    @Override
    public void updatePasswordSuccess() {
        if (mPresenter != null) {
            mPresenter.userInfo();
        }
    }

    @Override
    public void updateUsernameSuccess() {
        if (mPresenter != null) {
            mPresenter.userInfo();
        }
    }

    @Override
    public void userInfoSuccess(UserBean data) {
        this.userbean = data;
        tvName.setText(data.getUsername());
        tvPhone.setText(data.getPhone());
        tvSex.setText(data.getSex() == 0 ? "男" : "女");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
