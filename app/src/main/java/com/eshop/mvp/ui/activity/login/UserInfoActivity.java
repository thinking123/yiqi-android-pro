package com.eshop.mvp.ui.activity.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.eshop.mvp.ui.activity.order.CreateOrderActivity;
import com.eshop.mvp.ui.activity.set.AddressActivity;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.eshop.mvp.utils.SpUtils;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Avatar;

public class UserInfoActivity extends BaseSupportActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.iv_header)
    ImageView iv_header;

    @BindView(R.id.nick)
    TextView nick;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.sex)
    TextView sex;


    private PicChooserHelper picChooserHelper;
    private String head_url;

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
        return R.layout.activity_userinfo;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("个人信息");
        toolbarBack.setVisibility(View.VISIBLE);

        setData();

    }

    private void setData(){
        if(LoginUtils.isLogin(this)){
            nick.setText(BaseApp.loginBean.getNickNmae());
            sex.setText(BaseApp.loginBean.getSex()==1?"男":"女");

            head_url = BaseApp.loginBean.getLogo();
            if(head_url!=null)
                Glide.with(this)
                        .load(head_url)//new MultiTransformation(
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                        .into(iv_header);
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
        if(LoginUtils.isLogin(this)) {
            if (mPresenter != null) {
                head_url = url;
                String id = BaseApp.loginBean.getId() + "";
                mPresenter.updateUserInfo(id, null, null, head_url, null, BaseApp.loginBean.getSex(), "android", null);

            }
        }
    }

    @Override
    public void updateUserInfoSuccess(LoginBean msg) {
       // SpUtils.put(mContext, AppConstant.Api.TOKEN, msg.getToken());
       // Timber.e((String) SpUtils.get(mContext, AppConstant.Api.TOKEN, ""));
        BaseApp.loginBean = msg;
        LoginUtils.saveLogin(this);
        setData();

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



    @OnClick({R.id.head_bar, R.id.nick_bar,R.id.adress_bar,R.id.sex_bar})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.adress_bar:

                Intent intent = new Intent(this, AddressActivity.class);
                startActivity(intent);
                break;

            case R.id.head_bar:
                uploadImage();
                break;

            case R.id.nick_bar:

                new MaterialDialog.Builder(UserInfoActivity.this)
                        .title("修改昵称")
                        .negativeText(R.string.cancel_easy_photos)
                        .inputType(InputType.TYPE_CLASS_TEXT )
                        .inputRangeRes(0, 10, R.color.gray)

                        .input("最多10个字", nick.getText(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if(input.length()>0) {
                                    String nick_name = input.toString();
                                    if(LoginUtils.isLogin(UserInfoActivity.this)) {
                                        if (mPresenter != null) {
                                            String id = BaseApp.loginBean.getId() + "";
                                            mPresenter.updateUserInfo(id, null, null, head_url, nick_name, 0, "android",null);

                                        }
                                    }
                                }
                            }
                        }).show();

                break;

            case R.id.sex_bar:
                final String items[] = {"女","男"};
                new MaterialDialog.Builder(UserInfoActivity.this)
                        .title("性别")
                        .negativeText(R.string.cancel_easy_photos)
                        .inputType(InputType.TYPE_CLASS_TEXT )
                        .items(items)
                        // itemsCallbackSingleChoice 方法中的第一个参数代表预选项的索引值，没有预选项这个值就设置为 -1，有预选项就传入一个预选项的索引值即可。
                        // 如果不使用自定义适配器，则可以使用 MaterialDialog 实例上的 setSelectedIndex(int) 更新选定的索引值。
                        .itemsCallbackSingleChoice(BaseApp.loginBean.getSex(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                if(LoginUtils.isLogin(UserInfoActivity.this)) {
                                    if (mPresenter != null) {
                                        String id = BaseApp.loginBean.getId() + "";
                                        mPresenter.updateUserInfo(id, null, null, null, null, which, "android",null);

                                    }
                                }
                                return true;
                            }
                        })
                       .show();
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
        new MaterialDialog.Builder(UserInfoActivity.this)
                .title("头像上传")
                .negativeText(R.string.cancel_easy_photos)
                .inputType(InputType.TYPE_CLASS_TEXT )
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
                        }}
                }).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, resultCode, data);
    }

}
