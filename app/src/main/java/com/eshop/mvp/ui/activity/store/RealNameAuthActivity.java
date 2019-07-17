package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bin.david.dialoglib.BaseDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.ui.widget.AdDialog;
import com.eshop.mvp.ui.widget.ExampleDialog;
import com.eshop.mvp.utils.PicChooserHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Avatar;
import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 认证中心
 * ================================================
 */
public class RealNameAuthActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View,ExampleDialog.OnChangeListener {

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

    @BindView(R.id.tip)
    TextView tip;

    private PicChooserHelper picChooserHelper;

    private int card_index = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .storeManagerModule(new StoreManagerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_realname_auth; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("认证中心");
        toolbarBack.setVisibility(View.VISIBLE);

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

        if(BaseApp.storeState!=null && BaseApp.storeState.page1State!=0){
            tip.setVisibility(View.VISIBLE);
            tip.setText(BaseApp.storeState.page1Info);
        }else{
            tip.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.btn_pay,R.id.look,R.id.close1, R.id.close2, R.id.close3,R.id.add1, R.id.add2,R.id.photo_card1,R.id.photo_card2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                if(veryfy()){
                    Intent intent = new Intent(this,CompanyAuthActivity.class);
                    startActivity(intent);
                    finish();
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
                card_index = 1;
                uploadImage();
                break;
            case R.id.add2:
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
        new MaterialDialog.Builder(RealNameAuthActivity.this)
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
            showMessage("请输入真实姓名.");
            return false;
        }

        if(edit_phone.getText().toString().isEmpty()){
            showMessage("请输入手机号.");
            return false;
        }

        if(edit_cardid.getText().toString().isEmpty()){
            showMessage("请输入身份证号.");
            return false;
        }
        if(BaseApp.authInfo==null || BaseApp.authInfo.getIdCardHeadImg()==null || BaseApp.authInfo.getIdCardIMg()==null){
            showMessage("请上传身份证照片.");
            return false;
        }

        BaseApp.authInfo.setTrueName(edit_name.getText().toString());
        BaseApp.authInfo.setStorePhone(edit_phone.getText().toString());
        BaseApp.authInfo.setIdCard(edit_cardid.getText().toString());

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
    public void accountCreatSuccess() {

    }

    @Override
    public void banCarAllSuccess(BankCards bankCard) {

    }

    @Override
    public void bankIdDelSuccess() {

    }

    @Override
    public void cashTypeSuccess(CashType cashType) {

    }

    @Override
    public void checkPendingGoodsSuccess(Audit audit) {

    }

    @Override
    public void drawingSuccess() {

    }

    @Override
    public void goodsSuccess() {

    }

    @Override
    public void goodsDelSuccess() {

    }

    @Override
    public void goodsPutSuccess() {

    }

    @Override
    public void inSalesGoodsSuccess(Audit audit) {

    }

    @Override
    public void opinionSuccess() {

    }

    @Override
    public void pwdCreatSuccess() {

    }

    @Override
    public void recordSuccess(WithDrawRecord withDrawRecord) {

    }

    @Override
    public void sellingGoodsSuccess() {

    }

    @Override
    public void stateSuccess(StoreState storeState) {

    }

    @Override
    public void stateResult(String status, String msg, StoreState storeState) {

    }


    @Override
    public void stayOnTheShelfGoodsSuccess(Audit audit) {

    }

    @Override
    public void stopSellingGoodsSuccess() {

    }

    @Override
    public void storeSuccess() {

    }

    @Override
    public void storeColumnResult(StoreCatBean data) {

    }

    @Override
    public void storeColumnSuccess() {

    }

    @Override
    public void storeColumnCreatSuccess() {

    }

    @Override
    public void storeColumnDelSuccess() {

    }

    @Override
    public void storeLogoPutSuccess() {

    }

    @Override
    public void transactionSuccess(TransList transList) {

    }

    @Override
    public void walletSuccess(Wallet wallet) {

    }

    @Override
    public void getAuthSuccess(Auth auth) {

    }

    @Override
    public void updateUserImageSuccess(String url) {
        if(BaseApp.authInfo==null)BaseApp.authInfo = new AuthInfo();
        if(card_index==1) {
            add1.setVisibility(View.GONE);
            photo_card1.setVisibility(View.GONE);
            if (url != null) {
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .into(card1_img);
                BaseApp.authInfo.setIdCardHeadImg(url);

            }
        }

        if(card_index==2) {
            add2.setVisibility(View.GONE);
            photo_card2.setVisibility(View.GONE);
            if (url != null) {
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .into(card2_img);
                BaseApp.authInfo.setIdCardIMg(url);
            }
        }
    }

    @Override
    public void getCatBeanList(List<CatBean> data) {

    }

    @Override
    public void idStoreSuccess(StoreInfomation storeInfomation) {

    }

    @Override
    public void updateUserInfoSuccess(LoginBean msg) {

    }

    @Override
    public void getGoodDetailSuccess(ProductDetail good) {

    }

    @Override
    public void getMonthMsgSuccess(MonthMsg monthMsg) {

    }

    @Override
    public void getMonthMsgStatus(String status, String msg) {

    }

    @Override
    public void getIdMyQRCodeSuccess(QRCode qrCode) {

    }

    @Override
    public void onItemClick(String id, boolean isclose) {

    }
}
