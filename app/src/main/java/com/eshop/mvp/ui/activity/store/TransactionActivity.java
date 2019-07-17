package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.PayRet;
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
import com.eshop.mvp.presenter.OrderPresenter;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.fragment.StoreOrderFragment;
import com.eshop.mvp.ui.fragment.TransactionFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 交易明细
 * ================================================
 */
public class TransactionActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View {

    private Adapter adapter;

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

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
        return R.layout.activity_transaction; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("交易明细");
        toolbarBack.setVisibility(View.VISIBLE);

        tabLayout.setupWithViewPager(viewpager);

        storeId = getIntent().getStringExtra("id");


        if (viewpager != null) {
            setupViewPager(viewpager);
        }

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(TransactionFragment.newInstance(storeId,0), "未完成");
        adapter.addFragment(TransactionFragment.newInstance(storeId,1), "已完成");
        adapter.addFragment(TransactionFragment.newInstance(storeId,3), "退货");

        //不加这句滑动报错ViewHolder views must not be attached when created
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);

        viewPager.setAdapter(adapter);


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


}
