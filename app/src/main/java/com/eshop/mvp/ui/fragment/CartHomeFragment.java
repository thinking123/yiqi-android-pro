package com.eshop.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerCartComponent;
import com.eshop.di.component.DaggerHomeComponent;
import com.eshop.di.module.CartModule;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.contract.HomeContract;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.CompanyBean;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.presenter.CartPresenter;
import com.eshop.mvp.presenter.HomePresenter;
import com.eshop.mvp.ui.fragments.CartFragment;
import com.eshop.mvp.ui.fragments.RecommendFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;



public class CartHomeFragment extends BaseSupportFragment<CartPresenter> implements CartContract.View {

    private  Adapter adapter;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;


    public static CartHomeFragment newInstance() {
        CartHomeFragment fragment = new CartHomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCartComponent.builder()
                .appComponent(appComponent)
                .cartModule(new CartModule(this))
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cart_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        tabLayout.setupWithViewPager(viewpager);

       tabLayout.setTabMode(TabLayout.MODE_FIXED);


        if (viewpager != null) {
            setupViewPager(viewpager);
        }

    }


    @Override
    public void setData(@Nullable Object data) {

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

    }

    @Override
    public void getCartListSuccess(AppCartStore data) {

    }

    @Override
    public void addGoodSuccess() {

    }

    @Override
    public void addOrderSuccess(List<Order> data) {

    }



    @Override
    public void countPriceSuccess(String price) {

    }

    @Override
    public void delCartSuccess() {

    }

    @Override
    public void updateNumSuccess(int count) {

    }

    @Override
    public void getAddressList(List<AddressBean> list) {

    }

    /**  @Override
    public void getCatBeanList(List<CatBean> data) {

        setupViewPager(viewpager,data);
    }*/


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
        adapter = new Adapter(getChildFragmentManager());

        CartFragment cart1 = new CartFragment();
        CartFragment cart2 = new CartFragment();
        cart1.setType(0);
        cart2.setType(1);

        adapter.addFragment(cart1, "普通");
        adapter.addFragment(cart2, "月结");


        //不加这句滑动报错ViewHolder views must not be attached when created
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);

        viewPager.setAdapter(adapter);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;
      //  mPresenter.getCats();
    }
}
