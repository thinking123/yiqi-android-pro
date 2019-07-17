package com.eshop.wxapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.entity.AppData;
import com.eshop.mvp.http.entity.WxBaseResponse;
import com.eshop.mvp.http.entity.WxUserInfoResponse;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.ui.activity.store.AuthActivity;
import com.eshop.mvp.utils.ProgressDialogUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerWXEntryComponent;
import com.eshop.mvp.contract.WXEntryContract;
import com.eshop.mvp.presenter.WXEntryPresenter;

import com.eshop.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import static com.jess.arms.utils.Preconditions.checkNotNull;



public class WXEntryActivity extends BaseActivity<WXEntryPresenter> implements WXEntryContract.View,IWXAPIEventHandler {

    public static final String TAG = "WXEntryActivity";

    private IWXAPI api;

    private String code;

    private String openid;        //微信用户openid
    private String access_token;    //请求令牌
    private String scope;            //作用域
    private String unionid;        //用户唯一id
    private String refresh_token;    //刷新令牌
    private String expires_in;        //过期时间

    private String nickname;

    private ProgressDialogUtils progressDialogUtils;

    private void showProgress(final boolean show) {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(this);
            progressDialogUtils.setMessage("登录中");
        } else {
            if (show) {
                progressDialogUtils.show();
            } else {
                progressDialogUtils.dismiss();
            }
        }


    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWXEntryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wxentry; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Log.i(TAG, "enter WxEntryActivity!");

        api = WXAPIFactory.createWXAPI(this, AppData.WECHAT_APPID);

        api.handleIntent(getIntent(), this);

        showProgress(true);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        api.handleIntent(intent, this);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        Log.i(TAG, "resp.errorCode:" + baseResp.errCode + " , resp:" + baseResp.toString());
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK: //用户同意

                try {

                    SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                    code = sendResp.code;

                    if (mPresenter != null) mPresenter.wxGetToken(code);



                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "取消!", Toast.LENGTH_LONG).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, "被拒绝", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "失败!", Toast.LENGTH_LONG).show();
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        finish();

    }

    @Override
    public void getTokenResult(WxBaseResponse response) {
            AppData.wxBaseResponse = response;
        if (mPresenter != null) mPresenter.wxRefreshToken(response.refresh_token);

    }

    @Override
    public void refreshTokenResult(WxBaseResponse response) {
        AppData.wxBaseResponse = response;
        if (mPresenter != null) mPresenter.wxUserInfo(response.access_token,response.openid);


    }

    @Override
    public void authResult(WxBaseResponse response) {

    }

    @Override
    public void userInfoResult(WxUserInfoResponse response) {
        AppData.wxUserInfoResponse = response;

        if(BaseApp.wxAuth){
            AuthActivity.getInstance().wxAuth(response.unionid);
            BaseApp.wxAuth = false;
        }else {

            LoginActivity.getInstance().wxLogin(response.unionid);
        }
        showProgress(false);
        finish();
    }
}
