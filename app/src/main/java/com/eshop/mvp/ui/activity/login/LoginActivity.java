package com.eshop.mvp.ui.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.app.base.BaseApp;
import com.eshop.app.base.LoginConfig;
import com.eshop.mvp.http.entity.AppData;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerLoginComponent;
import com.eshop.di.module.LoginModule;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.login.JWTBean;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.presenter.LoginPresenter;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.ProgressDialogUtils;
import com.eshop.mvp.utils.SpUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.phone)
    EditText mPhoneView;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.btn_login_forget)
    TextView btnLoginForget;

    private ProgressDialogUtils progressDialogUtils;

    private static LoginActivity sLogin;

    private String unionid;

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
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        wxApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), AppData.WECHAT_APPID, true);
        wxApi.registerApp(AppData.WECHAT_APPID);
        sLogin = this;

        //mPhoneView.setText("15135303297");
       // mPhoneView.setText("15935727763");
      // mPasswordView.setText("123456789a");

    }

    @OnClick({R.id.btn_login, R.id.btn_register,R.id.btn_login_forget,R.id.btn_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.btn_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            case R.id.btn_login_forget:
                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
                break;
            case R.id.btn_weixin:
                showProgress(true);
                sendAuthReq();
                //startActivity(new Intent(mContext, BindActivity.class));
                break;

        }
    }

    private void attemptLogin() {
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();
        //if (mPresenter != null) mPresenter.login("15135303297", "123456789","android");
       // if (mPresenter != null) mPresenter.login("13928439909", "12345678","android");

          if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
              showMessage("密码不能为空/不可用");
              return;
          }

          if (TextUtils.isEmpty(phone)) {
              showMessage("账号不能为空");
              return;
          }

          showProgress(true);
          if (mPresenter != null) mPresenter.login(phone, password,"android");

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(mContext);
            progressDialogUtils.setMessage("登录中");
        } else {
            if (show) {
                progressDialogUtils.show();
            } else {
                progressDialogUtils.dismiss();
            }
        }


    }

    public static LoginActivity getInstance() {
        return sLogin;
    }

    @Override
    public void loginHuanxinResult() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void loginResult(LoginBean msg) {
        BaseApp.loginBean = msg;
        LoginUtils.saveLogin(this);

        if(mPresenter != null){
            mPresenter.loginHuanXin(msg.getHuanxinId() , LoginConfig.HUAMXINPASSWORD);
        }
//        startActivity(new Intent(mContext, MainActivity.class));
//        finish();
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

    }

    public void wxLogin(String unionid){
        this.unionid = unionid;
        if (mPresenter != null)
            mPresenter.wxLogin(unionid);

    }

    @Override
    public void wxLoginResult(LoginBean msg) {
        AppData.loginBean = msg;
        AppData.LoginType = "wx";

        BaseApp.loginBean = msg;
        LoginUtils.saveLogin(this);

        if(msg.getPhone()==null) {
            Intent intent = new Intent(mContext, BindActivity.class);
            intent.putExtra("openId", unionid);
            startActivity(intent);
        }else{
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }

        finish();
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
        showProgress(false);
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


    public static IWXAPI wxApi;

    public static boolean isWXLogin = false;

    private void sendAuthReq() {
        isWXLogin = true;

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        wxApi.sendReq(req);
    }
}

