package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.eshop.mvp.http.entity.store.AccountInfo;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.StoreManagerPresenter;
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
 微信支付宝账户绑定
 * ================================================
 */
public class WeixinZhifubaoActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.barcode)
    ImageView barcode;
    @BindView(R.id.plus)
    ImageView plus;

    private String storeId;
    private String url;

    /**
     * 账户类型  0 支付宝 1 微信 2 银行转账
     */
    private String type = "1";

    private AccountInfo accountInfo;

    private PicChooserHelper picChooserHelper;


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
        return R.layout.activity_wx_zifubao_bind; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("微信账户");
        toolbarBack.setVisibility(View.VISIBLE);
        storeId = BaseApp.loginBean.getStoreId() + "";
        type = getIntent().getStringExtra("type");

        if(type.equalsIgnoreCase("1")){
            toolbarTitle.setText("微信账户");
            tip.setText("请上传微信收款二维码");
        }else{
            toolbarTitle.setText("支付宝账户");
            tip.setText("请上传支付宝收款二维码");
        }

        mPresenter.getIdMyQRCode(BaseApp.loginBean.getToken(),storeId,type);


    }

    @OnClick({R.id.plus,R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.plus:
                uploadImage();
                break;




            case R.id.btn_commit:
                if(validate()){
                    mPresenter.accountCreat(BaseApp.loginBean.getToken(),accountInfo);
                }
                break;


        }
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Avatar);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(WeixinZhifubaoActivity.this)
                .title("图片上传")
                .backgroundColorRes(R.color.white)
                .titleColorRes(R.color.color_3333)
                .itemsColorRes(R.color.color_3333)
                .negativeColorRes(R.color.color_3333)
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
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, responseCode, data);
    }

    public boolean validate() {
        boolean valid = true;


        if (url==null ) {
            showMessage("请上传收款二维码");

            valid = false;
        }

        if(accountInfo==null)accountInfo = new AccountInfo();

        String storeId = BaseApp.loginBean.getStoreId()+"";

        accountInfo.setAccountType(type);
        accountInfo.setAccountNumber(url);
        accountInfo.setStoreId(storeId);


        accountInfo.setBank("");
        accountInfo.setCardholder("");
        accountInfo.setVcode("");
        accountInfo.setPhone("");

        return valid;
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
        showMessage("账户绑定成功.");
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
        this.url = url;
        plus.setVisibility(View.GONE);
        Glide.with(this)
                .load(url)//new MultiTransformation(
                // .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                .into(barcode);
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
        if(qrCode!=null){
            plus.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(qrCode.getAccountnumber())
                   // .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(5)))
                    .into(barcode);

        }
    }
}
