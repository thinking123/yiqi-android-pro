package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerStoreCatComponent;
import com.eshop.di.component.DaggerStoreComponent;
import com.eshop.di.module.ProductDetailsModule;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.http.entity.home.MockStoreCats;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.presenter.ProductDetailsPresenter;
import com.eshop.mvp.ui.activity.store.CatSetActivity;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreCatQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.SpUtils;
import com.eshop.mvp.utils.ViewUtils;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class StoreCatActivity extends BaseSupportActivity<ProductDetailsPresenter> implements ProductDetailsContract.View {

    @Inject
    List<StoreCat> storeColumns;

    @Inject
    StoreCatQuickAdapter storeCatQuickAdapter;


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.no_lay)
    View no_lay;

    @BindView(R.id.use_lay)
    View use_lay;

    @BindView(R.id.empty)
    View empty;

    @BindView(R.id.line)
    TextView line;

    @BindView(R.id.no_cat)
    RadioButton no_cat;

    @BindView(R.id.use_cat)
    RadioButton use_cat;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    private String type;

    /**
     * 店铺id
     */
    private String storeId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreCatComponent.builder()
                .appComponent(appComponent)
                .productDetailsModule(new ProductDetailsModule(this))
                .build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_inner_cat_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        initRecycler();

        toolbarTitle.setText("商品分类");
        toolbarBack.setVisibility(View.VISIBLE);
        storeId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");

        if(type.equalsIgnoreCase("publishgoodsactivity")){
            toolbarTitle.setText("店内类目");
            no_lay.setVisibility(View.VISIBLE);
            use_lay.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);

        }

        no_cat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)use_cat.setChecked(false);
                else use_cat.setChecked(true);
            }
        });

        use_cat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)no_cat.setChecked(false);
                else no_cat.setChecked(true);
            }
        });

        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreCatActivity.this,CatSetActivity.class);

                startActivity(intent);
            }
        });

        request(storeId);
    }

    private void request(String id){
        assert mPresenter != null;

            mPresenter.storeColumnAll(mNextRequestPage,id);
    }


    private void initRecycler() {
        Timber.e("initRecycler");
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipe_refresh.setProgressViewOffset(true, 2, 100);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       // mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

       // storeCatQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));
        storeCatQuickAdapter.setOnItemClickListener((adapter, view, position) -> {

                   String  storeColumnId = ((StoreCat) (adapter.getData()).get(position)).id+"";
                   String  categoryName = ((StoreCat) (adapter.getData()).get(position)).categoryName;
                   Intent intent = new Intent();
                   intent.putExtra("id",storeColumnId);
                   intent.putExtra("name",categoryName);
                   if(use_cat!=null && use_cat.isChecked()){
                       intent.putExtra("usecat",true);
                   }
                   setResult(RESULT_OK,intent);
                   finish();

                }
        );

        storeCatQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);

        mRecyclerView.setAdapter(storeCatQuickAdapter);
    }

    private void refresh() {
        storeCatQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        storeCatQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request(storeId);
    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            request(storeId);

        }else{
            storeCatQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            storeCatQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                storeCatQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            storeCatQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            storeCatQuickAdapter.loadMoreComplete();
        }
    }

    private void updateData(StoreCatBean data){
       // MockStoreCats.init();

        if(data!=null) {
            if(data.pageUtil!=null)
                PAGE_SIZE = data.pageUtil.total;
            if(data.pageUtil!=null)
                pages = data.pageUtil.pages;
        }

        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            storeCatQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

            StoreCat storeCat = new StoreCat();
            storeCat.categoryName = "全部";
            storeCat.id = 0;

            data.storeColumns.add(0,storeCat);

        }
        setData(isRefresh, data.storeColumns);
        //setData(isRefresh, MockStoreCats.catsList);
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
        updateData(data);
        if(data.storeColumns==null || data.storeColumns.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        }else{
            empty.setVisibility(View.GONE);
        }
    }



    @Override
    public void storeIdResult(StoreInfo data) {

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
        if (swipe_refresh != null && swipe_refresh.isRefreshing())
            swipe_refresh.setRefreshing(false);
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
