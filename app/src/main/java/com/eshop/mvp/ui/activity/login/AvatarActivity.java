package com.eshop.mvp.ui.activity.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.eshop.mvp.http.entity.AppData;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.eshop.mvp.presenter.LoginPresenter;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.eshop.mvp.utils.SpUtils;
import com.jess.arms.di.component.AppComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Avatar;

public class AvatarActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.iv_header)
    ImageView header;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private PicChooserHelper picChooserHelper;
    private String head_url;

    private String phone;
    private String password;

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
        return R.layout.activity_avatar;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("填写昵称");
        toolbarBack.setVisibility(View.VISIBLE);

        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");

    }

    @Override
    public void loginResult(LoginBean msg) {
        BaseApp.loginBean = msg;
        LoginUtils.saveLogin(this);
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void registerSuccess(UserInfoBean userBean) {
        showMessage("注册成功！");

        mPresenter.login(phone,password,"android");
    }

    @Override
    public void setPasswordResult(LoginBean msg) {

    }

    @Override
    public void updateUserImageSuccess(String url) {
        head_url = url;
        Glide.with(this)
                .load(url)//new MultiTransformation(
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                .into(header);
    }

    @Override
    public void updateUserInfoSuccess(LoginBean msg) {
        // SpUtils.put(mContext, AppConstant.Api.TOKEN, msg.getToken());
        // Timber.e((String) SpUtils.get(mContext, AppConstant.Api.TOKEN, ""));
        BaseApp.loginBean = msg;
        LoginUtils.saveLogin(this);
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
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


    @OnClick({R.id.send_sms, R.id.btn_register, R.id.iv_header})
    public void onViewClicked(View view) {
        String name = nickname.getText().toString().trim();

        switch (view.getId()) {

            case R.id.iv_header:
                uploadImage();
                break;

            case R.id.btn_register:

                String nick_name = nickname.getText().toString().trim();
                if (canUseNickName(nick_name)) {

                    if (mPresenter != null) {
                        //String id = AppData.loginBean.getId() + "";
                        //mPresenter.updateUserInfo(id, null, null, head_url, nick_name, 0, "android");
                        mPresenter.register(phone,password,head_url,nick_name);
                    }
                }
                break;
        }
    }

    public boolean canUseNickName(String nickname) {
        if (TextUtils.isEmpty(nickname)) {
            showMessage("请输入昵称");
            return false;
        }
        return true;
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Avatar);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(AvatarActivity.this)
                .title("头像上传")
                .titleColorRes(R.color.color_3333)
                .backgroundColorRes(R.color.white)
                .itemsColorRes(R.color.color_3333)
                .positiveColorRes(R.color.color_3333)
                .negativeText(R.string.cancel_easy_photos)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                picChooserHelper.takePicFromCamera();
                                break;
                            case 1:
                                picChooserHelper.takePicFromAlbum();
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, resultCode, data);
    }

}
