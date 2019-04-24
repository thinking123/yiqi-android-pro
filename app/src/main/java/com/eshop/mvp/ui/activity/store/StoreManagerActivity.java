package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerLoginComponent;
import com.eshop.di.module.LoginModule;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.ui.activity.order.AfterSaleActivity;
import com.eshop.mvp.ui.activity.order.OrderActivity;
import com.eshop.mvp.ui.activity.order.StoreOrderActivity;
import com.eshop.mvp.ui.activity.product.StoreActivity;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.presenter.StoreManagerPresenter;

import com.eshop.R;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 店铺管理
 * ================================================
 */
public class StoreManagerActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.guan)
    TextView total;
    @BindView(R.id.shou)
    TextView orderNum;
    @BindView(R.id.iv_header)
    ImageView iv_header;

    private String storeId;

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
        return R.layout.activity_store_manager; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("店铺管理");
        toolbarBack.setVisibility(View.VISIBLE);
        if(!LoginUtils.isLogin(this)) {
            return;
        }
        storeId = BaseApp.loginBean.getStoreId() + "";
        mPresenter.idStore(BaseApp.loginBean.getToken(),storeId);
    }

    @OnClick({R.id.iv_header, R.id.all, R.id.daifu, R.id.daifa,
            R.id.daishou, R.id.wancheng, R.id.tuikuan,
            R.id.goods_manager, R.id.cat_set,R.id.publish,
            R.id.mystore,R.id.mywallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wancheng:
                Intent intent7 = new Intent(this, AfterSaleActivity.class);
                intent7.putExtra("type","store");
                startActivity(intent7);
                break;
            case R.id.mystore:
                if (BaseApp.loginBean.getStoreId() != 0) {

                    Intent intent = new Intent(this,StoreActivity.class);
                    intent.putExtra("id",storeId);
                    intent.putExtra("isstore",true);
                    startActivity(intent);
                }else{
                    Intent intent4 = new Intent(this, StoreStateActivity.class);
                    startActivity(intent4);
                }
                break;
            case R.id.all:
                Intent intent2 = new Intent(this,StoreOrderActivity.class);
                intent2.putExtra("id",storeId);
                startActivity(intent2);
                break;

            case R.id.daifu:
                Intent intent_d = new Intent(this, StoreOrderActivity.class);
                intent_d.putExtra("position",1);
                intent_d.putExtra("id",storeId);
                startActivity(intent_d);
                break;

            case R.id.daifa:
                Intent intent_a = new Intent(this, StoreOrderActivity.class);
                intent_a.putExtra("position",2);
                intent_a.putExtra("id",storeId);
                startActivity(intent_a);
                break;

            case R.id.daishou:
                Intent intent_s = new Intent(this, StoreOrderActivity.class);
                intent_s.putExtra("position",3);
                intent_s.putExtra("id",storeId);
                startActivity(intent_s);
                break;

            case R.id.tuikuan:
                Intent intent_w = new Intent(this, StoreOrderActivity.class);
                intent_w.putExtra("position",4);
                intent_w.putExtra("id",storeId);
                startActivity(intent_w);
                break;

            case R.id.publish:
                Intent intent3 = new Intent(this,PublishGoodsActivity.class);
                intent3.putExtra("id",storeId);
                startActivity(intent3);

                break;



            case R.id.cat_set:
                Intent intent = new Intent(this,CatSetActivity.class);
                startActivity(intent);

                break;

            case R.id.goods_manager:
                Intent intent1 = new Intent(this,GoodsMgrActivity.class);
                startActivity(intent1);

                break;

            case R.id.mywallet:
                Intent intent6 = new Intent(this,WalletActivity.class);
                startActivity(intent6);
                break;
        }
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

    }

    @Override
    public void getCatBeanList(List<CatBean> data) {

    }

    @Override
    public void idStoreSuccess(StoreInfomation storeInfomation) {
        if(storeInfomation==null)return;
        tv_name.setText(storeInfomation.getStoreName());
        orderNum.setText(storeInfomation.getStoreOrderNum());
        total.setText(storeInfomation.getStoreMoney());

        storeId = storeInfomation.getStoreId();

        Glide.with(this)
                .load(storeInfomation.getStoreImg())//new MultiTransformation(
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                .into(iv_header);
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
}
