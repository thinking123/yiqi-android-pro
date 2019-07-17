package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerProductListComponent;
import com.eshop.di.module.ProductListModule;
import com.eshop.mvp.contract.ProductListContract;
import com.eshop.mvp.http.entity.home.Const;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.presenter.ProductListPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SaleFlashActivity extends BaseSupportActivity<ProductListPresenter> implements ProductListContract.View{

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.day)
    TextView day_view;

    @BindView(R.id.tip)
    TextView tip_view;

    private String id;

    @OnClick({R.id.backimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backimage:

                Intent intent = new Intent(this, ProductListActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("title","7号特卖");
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProductListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .productListModule(new ProductListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sale_flash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("7号特卖");
        toolbarBack.setVisibility(View.VISIBLE);
        id = getIntent().getStringExtra("id");
        assert mPresenter != null;
        mPresenter.getSaleGoods(1,id);

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

    @Override
    public void getGoodsError(String msg, String type) {

    }

    @Override
    public void getGoodsResult(GoodsBean data, String type) {
        day_view.setText(data.saleDay);
    }
}
