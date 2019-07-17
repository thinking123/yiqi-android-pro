package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerStoreComponent;
import com.eshop.di.component.DaggerStoreIntroduceComponent;
import com.eshop.di.module.ProductDetailsModule;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.presenter.ProductDetailsPresenter;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.SpUtils;
import com.eshop.mvp.utils.ViewUtils;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class StoreIntroduceActivity extends BaseSupportActivity<ProductDetailsPresenter> implements ProductDetailsContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.main_product)
    TextView main_product;
    @BindView(R.id.product_num)
    TextView product_num;
    @BindView(R.id.person)
    TextView person;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.adress)
    TextView adress;

    /**
     * 店铺id
     */
    private String storeId;
    private String token;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreIntroduceComponent.builder()
                .appComponent(appComponent)
                .productDetailsModule(new ProductDetailsModule(this))
                .build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_store_introduce;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("公司介绍");
        toolbarBack.setVisibility(View.VISIBLE);
        storeId = getIntent().getStringExtra("id");
        request(token,storeId);
    }

    private void request(String token,String id){
        assert mPresenter != null;

            mPresenter.storeId(id);

    }



    private void updateData(StoreInfo data){
        if(data==null) {
            StoreInfo info = new StoreInfo();
            info.streoName = "深圳腾讯科技有限公司";
            info.storeArea = "深圳市福田区";
            info.mainGoods = "电子产品";
            info.goodsCountNum = "200";
            info.trueName = "王先生";
            info.storePhone = "138888888";
            info.detailsAddress = "深圳技术学院";

            name.setText(info.streoName);
            location.setText(info.storeArea);
            main_product.setText(info.mainGoods);
            product_num.setText(info.goodsCountNum);
            person.setText(info.trueName);
            phone.setText(info.storePhone);
            adress.setText(info.detailsAddress);
        }else{
            name.setText(data.streoName);
            location.setText(data.storeArea);
            main_product.setText(data.mainGoods);
            product_num.setText(data.goodsCountNum);
            person.setText(data.trueName);
            phone.setText(data.storePhone);
            adress.setText(data.detailsAddress);
        }

    }


    @Override
    public void storeGoodsResult(GoodsBean data) {

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
    public void storeIdResult(StoreInfo data) {
        updateData(data);
    }

    @Override
    public void collectionAddGoodsSuccess() {

    }

    @Override
    public void collectionAddStoreSuccess() {
            showMessage("收藏店铺成功.");
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
