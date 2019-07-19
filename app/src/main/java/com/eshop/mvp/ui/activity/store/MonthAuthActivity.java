package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bin.david.dialoglib.BaseDialog;
import com.bumptech.glide.Glide;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.module.MonthAuthModule;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.http.entity.auth.MonthData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.ui.widget.ExampleDialog;
import com.eshop.mvp.utils.PicChooserHelper;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerMonthAuthComponent;
import com.eshop.mvp.contract.MonthAuthContract;
import com.eshop.mvp.presenter.MonthAuthPresenter;

import com.eshop.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MonthAuthActivity extends BaseSupportActivity<MonthAuthPresenter> implements MonthAuthContract.View,ExampleDialog.OnChangeListener {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.name)
    EditText edit_name;
    @BindView(R.id.phone)
    EditText edit_phone;
    @BindView(R.id.cardid)
    EditText edit_cardid;

    @BindView(R.id.tip)
    TextView tip;

    @BindView(R.id.close1)
    ImageView close1;
    @BindView(R.id.close2)
    ImageView close2;
    @BindView(R.id.close3)
    ImageView close3;

    @BindView(R.id.add1)
    ImageView add1;
    @BindView(R.id.add2)
    ImageView add2;

    @BindView(R.id.photo_card1)
    TextView photo_card1;
    @BindView(R.id.photo_card2)
    TextView photo_card2;

    @BindView(R.id.card1_img)
    ImageView card1_img;
    @BindView(R.id.card2_img)
    ImageView card2_img;

    private PicChooserHelper picChooserHelper;

    private int card_index = 0;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMonthAuthComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                //.view(this)
                .monthAuthModule(new MonthAuthModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_month_auth; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("月结认证");
        toolbarBack.setVisibility(View.VISIBLE);

        mPresenter.getMonthMsg(BaseApp.loginBean.getToken());

        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0)close1.setVisibility(View.VISIBLE);
                else close1.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0)close2.setVisibility(View.VISIBLE);
                else close2.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_cardid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0)close3.setVisibility(View.VISIBLE);
                else close3.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.card1_img,R.id.card2_img,R.id.btn_pay,R.id.look,R.id.close1, R.id.close2, R.id.close3,R.id.add1, R.id.add2,R.id.photo_card1,R.id.photo_card2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                if(veryfy()){
                    Intent intent = new Intent(this,MonthCompanyAuthActivity.class);
                    startActivity(intent);
                  //  finish();
                }

                break;
            case R.id.look:
                BaseDialog.Builder builder =  new  BaseDialog.Builder(this);
                builder.setGravity(Gravity.CENTER);
                builder.setFillWidth(true).
                        setFillHeight(true).
                        setContentViewBackground(android.R.drawable.screen_background_dark_transparent)
                        .setMargin(0,0,0,0); //设置margin;
                ExampleDialog adDialog = new ExampleDialog(this,builder);
                adDialog.show(this,"","");
                break;
            case R.id.close1:
                edit_name.setText("");
                break;
            case R.id.close2:
                edit_phone.setText("");
                break;
            case R.id.close3:
                edit_cardid.setText("");
                break;

            case R.id.add1:
            case R.id.card1_img:
                card_index = 1;
                uploadImage();
                break;
            case R.id.add2:
            case R.id.card2_img:
                card_index = 2;
                uploadImage();
                break;
            case R.id.photo_card1:
                card_index = 1;
                uploadImage();
                break;
            case R.id.photo_card2:
                card_index = 2;
                uploadImage();
                break;

        }
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Cover);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(MonthAuthActivity.this)
                .title("上传身份证照片")
                .backgroundColorRes(R.color.white)
                .titleColorRes(R.color.color_3333)
                .itemsColorRes(R.color.color_3333)
                .negativeColorRes(R.color.color_3333)
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

    private boolean veryfy(){

        if(edit_name.getText().toString().isEmpty()){
            showMessage("请输入法人姓名.");
            return false;
        }

        if(edit_phone.getText().toString().isEmpty()){
            showMessage("请输入手机号.");
            return false;
        }

        if(edit_cardid.getText().toString().isEmpty()){
            showMessage("请输入法人身份证号.");
            return false;
        }
        if(BaseApp.monthData==null || BaseApp.monthData.getIdCardHeadImg()==null || BaseApp.monthData.getIdCardIMg()==null){
            showMessage("请上传身份证照片.");
            return false;
        }

        BaseApp.monthData.setLegalPerson(edit_name.getText().toString());
        BaseApp.monthData.setMonthPhone(edit_phone.getText().toString());
        BaseApp.monthData.setIdCard(edit_cardid.getText().toString());

        return true;
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
    public void getAuthSuccess(Auth auth) {

    }

    @Override
    public void getMonthMsgSuccess(MonthMsg monthMsg) {
           BaseApp.monthMsg = monthMsg;
           tip.setVisibility(View.GONE);
            setData(monthMsg);
    }

    @Override
    public void getMonthMsgStatus(String status, String msg,MonthMsg monthMsg) {

        if(monthMsg != null){
            tip.setVisibility(View.VISIBLE);
            tip.setText(monthMsg.getPage1Info());
            setData(monthMsg);
        }

    }

    private void setData(MonthMsg monthMsg){
        if(monthMsg!=null){

            edit_name.setText(monthMsg.getLegalPerson());
            edit_phone.setText(monthMsg.getMonthPhone());
            edit_cardid.setText(monthMsg.getIdCard());

            add1.setVisibility(View.GONE);
            photo_card1.setVisibility(View.GONE);
            add2.setVisibility(View.GONE);
            photo_card2.setVisibility(View.GONE);

            Glide.with(this)
                    .load(monthMsg.getIdCardHeadImg())
                    .into(card1_img);

            Glide.with(this)
                    .load(monthMsg.getIdCardIMg())
                    .into(card2_img);

            if(BaseApp.monthData==null)BaseApp.monthData = new MonthData();
            BaseApp.monthData.setIdCardIMg(monthMsg.getIdCardIMg());
            BaseApp.monthData.setIdCardHeadImg(monthMsg.getIdCardHeadImg());
            BaseApp.monthData.setLegalPerson(edit_name.getText().toString());
            BaseApp.monthData.setMonthPhone(edit_phone.getText().toString());
            BaseApp.monthData.setIdCard(edit_cardid.getText().toString());

        }
    }

    @Override
    public void monthAddSuccess() {

    }

    @Override
    public void monthAddSuccess(String msg) {

    }

    @Override
    public void monthEditSuccess() {

    }

    @Override
    public void monthPaySuccess() {

    }

    @Override
    public void updateUserImageSuccess(String url) {
        if(BaseApp.monthData==null)BaseApp.monthData = new MonthData();
        if(card_index==1) {
            add1.setVisibility(View.GONE);
            photo_card1.setVisibility(View.GONE);
            if (url != null) {
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .into(card1_img);
                BaseApp.monthData.setIdCardHeadImg(url);

            }
        }

        if(card_index==2) {
            add2.setVisibility(View.GONE);
            photo_card2.setVisibility(View.GONE);
            if (url != null) {
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .into(card2_img);
                BaseApp.monthData.setIdCardIMg(url);
            }
        }
    }

    @Override
    public void onItemClick(String id, boolean isclose) {

    }
}
