package com.eshop.mvp.ui.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerLoginComponent;
import com.eshop.di.module.LoginModule;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.eshop.mvp.presenter.LoginPresenter;
import com.eshop.mvp.ui.activity.login.ForgetPasswordActivity;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.ui.activity.order.OrderActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.eshop.mvp.utils.SpUtils;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Avatar;

public class SetActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    @BindView(R.id.about_bar)
    TextView about_bar;
    @BindView(R.id.tip_bar)
    TextView tip_bar;
    @BindView(R.id.borth_bar)
    TextView password_bar;

    @BindView(R.id.logout)
    Button logout;

    @Override
    public void loginHuanxinResult() {

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_set;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("设置");
        toolbarBack.setVisibility(View.VISIBLE);

        if(LoginUtils.isLogin(this)){
            logout.setText("退出登录");
        }else{
            logout.setText("登录");
            //finish();
        }

    }

    @Override
    public void loginResult(LoginBean msg) {

    }

    @Override
    public void registerSuccess(UserInfoBean userBean) {

    }

    @Override
    public void setPasswordResult(LoginBean msg) {

    }

    @Override
    public void updateUserImageSuccess(String url) {

    }

    @Override
    public void updateUserInfoSuccess(LoginBean msg) {

    }

    @Override
    public void wxLoginResult(LoginBean msg) {

    }

    @Override
    public void checkPhoneSuccess() {

    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }



    @OnClick({R.id.about_bar, R.id.tip_bar,R.id.borth_bar,R.id.logout})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.about_bar:
                Intent intent2 = new Intent(this,AboutActivity.class);
                startActivity(intent2);
                break;

            case R.id.tip_bar:
                if (LoginUtils.isLogin(this)) {
                    Intent intent1 = new Intent(this,OpinionActivity.class);
                    startActivity(intent1);

                } else {
                    LoginUtils.login(this);
                }


                break;

            case R.id.borth_bar:
                if (LoginUtils.isLogin(this)) {
                    Intent intent = new Intent(this,ForgetPasswordActivity.class);
                    startActivity(intent);

                } else {
                    LoginUtils.login(this);
                }

                break;

            case R.id.logout:

                if(logout.getText().toString().equalsIgnoreCase("退出登录")) {
                    BaseApp.loginBean = null;
                    BaseApp.reset();
                    SpUtils.clear(this);
                    logout.setText("登录");
                    showMessage("已退出登录.");
                }else{
                    Intent intent3 = new Intent(this,LoginActivity.class);
                    startActivity(intent3);
                }

                break;

        }
    }



}
