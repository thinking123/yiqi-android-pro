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
import com.eshop.di.component.DaggerProductDetailsComponent;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.ProductDetailsModule;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.ProductDetailsPresenter;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.fragment.GoodsCollectionFragment;
import com.eshop.mvp.ui.fragment.StoreCollectionFragment;
import com.eshop.mvp.ui.fragment.StoreManagerFragment;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * ================================================
 * 收藏
 * ================================================
 */
public class CollectionActivity extends BaseSupportActivity<ProductDetailsPresenter> implements ProductDetailsContract.View {

    private Adapter adapter;

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    int position = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProductDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .productDetailsModule(new ProductDetailsModule(this))
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_goods_mgr; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("收藏");
        toolbarBack.setVisibility(View.VISIBLE);

        tabLayout.setupWithViewPager(viewpager);

        position = getIntent().getIntExtra("position",0);



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
        adapter.addFragment(GoodsCollectionFragment.newInstance(), "商品");
        adapter.addFragment(StoreCollectionFragment.newInstance(), "店铺");

        //不加这句滑动报错ViewHolder views must not be attached when created
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);

        viewPager.setAdapter(adapter);

        viewpager.setCurrentItem(position);
    }


    @Override
    public void addGoodSuccess() {

    }

    @Override
    public void getGoodDetailSuccess(ProductDetail good) {

    }

    @Override
    public void collectioGoodsFindResult(GoodsBean data) {

    }

    @Override
    public void collectionStoreFindResult(StoresBean data) {

    }

    @Override
    public void storeColumnResult(StoreCatBean data) {

    }

    @Override
    public void storeGoodsResult(GoodsBean data) {

    }

    @Override
    public void storeIdResult(StoreInfo data) {

    }

    @Override
    public void collectionAddGoodsSuccess() {

    }

    @Override
    public void collectionAddStoreSuccess() {

    }

    @Override
    public void collectionDelSuccess() {

    }

    @Override
    public void collectionDelStoreSuccess() {

    }

    @Override
    public void updateUserImageSuccess(String data) {

    }

    @Override
    public void getCatBeanList(List<CatBean> data) {

    }

    @Override
    public void loginHuanxinResult() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }




}
