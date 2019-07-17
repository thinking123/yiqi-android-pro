package com.eshop.mvp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.app.base.LoginConfig;
import com.eshop.di.component.DaggerLoginComponent;
import com.eshop.di.module.LoginModule;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.eshop.mvp.presenter.LoginPresenter;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.SpUtils;
import com.jess.arms.di.component.AppComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class ForgetPasswordActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    EditText code;

    @BindView(R.id.send_sms)
    CountDownButton _codeButton;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.re_password)
    EditText re_password;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    public void loginHuanxinResult() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
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
        return R.layout.activity_setpassword;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("密码重置");
        toolbarBack.setVisibility(View.VISIBLE);

        //phone.setText(BaseApp.loginBean.getPhone());

    }

    @Override
    public void loginResult(LoginBean msg) {
        //SpUtils.put(mContext, AppConstant.Api.TOKEN, msg.getToken());
        //Timber.e((String) SpUtils.get(mContext, AppConstant.Api.TOKEN, ""));
        BaseApp.loginBean = msg;
        LoginUtils.saveLogin(this);
        if(mPresenter != null){
            mPresenter.loginHuanXin(msg.getHuanxinId() , LoginConfig.HUAMXINPASSWORD);
        }
//        startActivity(new Intent(mContext, MainActivity.class));
//        finish();
    }

    @Override
    public void registerSuccess(UserInfoBean userBean) {

    }

    @Override
    public void setPasswordResult(LoginBean msg) {
        String phones = phone.getText().toString().trim();
        String passwords = password.getText().toString().trim();
        showMessage("重置密码成功");
        if (mPresenter != null) {
            mPresenter.login(phones, passwords,"android");
        }
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



    @OnClick({R.id.send_sms, R.id.btn_register})
    public void onViewClicked(View view) {
        String phones = phone.getText().toString().trim();
        String passwords = password.getText().toString().trim();
        String re_passwords = re_password.getText().toString().trim();
        String codes = code.getText().toString().trim();

        switch (view.getId()) {


            case R.id.send_sms:

                if (validate_phone(phones)) {
                    if (mPresenter != null) {
                        mPresenter.sendSms(phones);
                    }
                } else {
                    _codeButton.removeCountDown();
                }
                break;
            case R.id.btn_register:
                if (validate_phone(phones) && canUseCode(codes) && canUsePassword(passwords,re_passwords))
                    if (mPresenter != null) {
                        mPresenter.checkCode(passwords, phones, codes,"password");
                    }
                break;
        }
    }

    public boolean canUsePhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showMessage("手机号不能为空/位数不对");
            return false;
        }
        return true;
    }

    public boolean canUsePassword(String password,String re_password) {
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            showMessage("密码不能为空/不能小于8位");
            return false;
        }else if(!password.equalsIgnoreCase(re_password)){
            showMessage("密码确认有误");
            return false;
        }
        return true;
    }

    public boolean canUseCode(String code) {
        if (TextUtils.isEmpty(code) || code.length() < 4) {
            showMessage("验证码不能为空/不可用");
            return false;
        }
        return true;
    }



    public boolean validate_phone(String phone) {
        boolean valid = true;

        if (phone.isEmpty() || !isMobileNO(phone)) {
            showMessage("手机号不能为空/手机号码格式错误!");

            valid = false;
        }

        return valid;
    }

    /**
     * 验证手机格式
     */
    public boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
        String telRegex = "[1][345789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);

    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
