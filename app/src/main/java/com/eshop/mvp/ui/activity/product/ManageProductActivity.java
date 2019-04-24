package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerManageProductComponent;
import com.eshop.di.module.ManageProductModule;
import com.eshop.mvp.contract.ManageProductContract;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.presenter.ManageProductPresenter;
import com.eshop.mvp.ui.adapter.ManageProductAdapter;
import com.eshop.mvp.utils.AppConstant;

import java.util.List;

import butterknife.BindView;

public class ManageProductActivity extends BaseSupportActivity<ManageProductPresenter> implements ManageProductContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private ManageProductAdapter manageProductAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerManageProductComponent.builder()
                .appComponent(appComponent)
                .manageProductModule(new ManageProductModule(this))
                .build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_manage_product;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.list();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("商品管理");
        toolbarBack.setVisibility(View.VISIBLE);
        initRecyclerView();
    }

    private void initRecyclerView() {
        swipeRefresh.setOnRefreshListener(() -> {
            if (mPresenter != null) {
                mPresenter.list();
            }
        });
        manageProductAdapter = new ManageProductAdapter();
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerList.setAdapter(manageProductAdapter);

        manageProductAdapter.setOnClickBtnListener(new ManageProductAdapter.OnClickBtnListener() {
            @Override
            public void onClick(Product item, boolean onSale) {
                if (mPresenter != null) {
                    mPresenter.updateStatus(item.getId(), onSale ? 2 : 1);
                }
            }

            @Override
            public void onClickEdit(Product item) {
                Intent intent = new Intent(mContext, CreateProductActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(AppConstant.ActivityIntent.PRODUCT_BEAN, item);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        manageProductAdapter.setEmptyView(LayoutInflater.from(mContext).inflate(R.layout.view_empty, null));

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void productListsuccess(List<Product> data) {
        manageProductAdapter.setNewData(data);
    }

    @Override
    public void updateStatusSuccess() {
        if (mPresenter != null) {
            mPresenter.list();
        }
    }
}
