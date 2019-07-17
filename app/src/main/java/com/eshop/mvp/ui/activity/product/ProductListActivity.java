package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.module.LoginModule;
import com.eshop.di.module.ProductListModule;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.Const;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerProductListComponent;
import com.eshop.mvp.contract.ProductListContract;
import com.eshop.mvp.presenter.ProductListPresenter;

import com.eshop.R;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/22/2019 15:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ProductListActivity extends BaseSupportActivity<ProductListPresenter> implements ProductListContract.View {

    @Inject
    List<HomeGoodBean> recommendProductsBeans;

    @Inject
    RecommendQuickAdapter recommendQuickAdapter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    private String id;

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
        return R.layout.activity_product_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        String title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        toolbarTitle.setText(title);
        toolbarBack.setVisibility(View.VISIBLE);

        initRecycler();

        request(id);
    }

    private void request(String id){
        assert mPresenter != null;
        if(id.equalsIgnoreCase(Const.MONTH_ID)){
            mPresenter.getMonthGoods(mNextRequestPage,id);
        }else if(id.equalsIgnoreCase(Const.SALE7_ID)){
            mPresenter.getSaleGoods(mNextRequestPage,id);
        }else if(id.equalsIgnoreCase(Const.MIAOMIAOGOU_ID)){
            mPresenter.getMiaoMiaoGouGoods(mNextRequestPage,id);
        }else if(id.equalsIgnoreCase(Const.SALE_ID)){
            mPresenter.getPriceSaleGoods(mNextRequestPage,id);
        }else{
            //品牌id多种
            mPresenter.getBrandGoods(mNextRequestPage,id);
        }

    }

    private void initRecycler() {
        Timber.e("initRecycler");
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipe_refresh.setProgressViewOffset(true, 130, 300);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

        recommendQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));
        recommendQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra("good",((HomeGoodBean)adapter.getItem(position)));
            startActivity(intent);
                }
        );

        recommendQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);

        mRecyclerView.setAdapter(recommendQuickAdapter);
    }

    private void refresh() {
        recommendQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        recommendQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request(id);
    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            request(id);

        }else{
            recommendQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            recommendQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                recommendQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            recommendQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            recommendQuickAdapter.loadMoreComplete();
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (swipe_refresh != null && swipe_refresh.isRefreshing())
            swipe_refresh.setRefreshing(false);
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


    private void updateData(GoodsBean data){
        MockGoods.init();

        // recommendProductsBeans.clear();
        // recommendProductsBeans.addAll(MockGoods.goodsList);
        // recommendQuickAdapter.notifyDataSetChanged();
        if(data!=null) {
            if(data.pageUtil!=null)
                PAGE_SIZE = data.pageUtil.total;
            if(data.pageUtil!=null)
                pages = data.pageUtil.pages;
        }

        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            recommendQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        if(data.goodsList!=null && data.goodsList.size()!=0)
            setData(isRefresh, data.goodsList);
        else
            setData(true,null);
    }

    @Override
    public void getGoodsError(String msg, String type) {
        showMessage(msg);
    }

    @Override
    public void getGoodsResult(GoodsBean data, String type) {
        updateData(data);
    }
}
