package com.eshop.mvp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.eshop.app.base.BaseApp;
import com.eshop.di.component.DaggerLoginComponent;
import com.eshop.di.module.LoginModule;
import com.eshop.huanxin.utils.chatUtils;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.eshop.mvp.presenter.LoginPresenter;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.SpUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import timber.log.Timber;

public class SplashActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {

    Disposable disposable;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void loginHuanxinResult() {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 判断是否是第一次开启应用
       // boolean isFirstOpen = (boolean) SpUtils.get(this, AppConstant.FIRST_OPEN, false);
        // 如果是第一次启动，则先进入功能引导页
       // if (isFirstOpen) {
       //     startActivity(new Intent(this, WelcomeActivity.class));
        //    finish();
       //     return;
       // }

         if(LoginUtils.isLogin(this)) {

           //  String id = BaseApp.loginBean.getId() + "";
           //  mPresenter.updateUserInfo(id, null, null, null, null, 0, "android");

             // 如果不是第一次启动app，则正常显示启动屏
             //new Handler().postDelayed(this::enterHomeActivity, 200);
         }else{
           // startActivity(new Intent(mContext, LoginActivity.class));
            //finish();
        }


        disposable = chatUtils.loadAll().subscribe(new Action() {
            @Override
            public void run() throws Exception {
                Timber.e("callback loadAll onSuccess");
                runOnUiThread(()->{
                    new Handler().postDelayed(SplashActivity.this::enterHomeActivity, 200);
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
            disposable = null;
        }
    }

    private void enterHomeActivity() {
       // String token = (String) SpUtils.get(mContext, AppConstant.Api.TOKEN, "");
       // if (!TextUtils.isEmpty(token)) {
            startActivity(new Intent(mContext, MainActivity.class));
       //     finish();
        //} else {
          //  startActivity(new Intent(mContext, LoginActivity.class));
            finish();
        //}
    }

    @Override
    public void loginResult(LoginBean msg) {

    }

    @Override
    public void registerSuccess(UserInfoBean userBeanBaseResponse) {

    }

    @Override
    public void setPasswordResult(LoginBean msg) {

    }

    @Override
    public void updateUserImageSuccess(String url) {

    }

    @Override
    public void updateUserInfoSuccess(LoginBean msg) {
            BaseApp.loginBean = msg;

       // enterHomeActivity();

       // new Handler().postDelayed(this::enterHomeActivity, 200);
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

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
