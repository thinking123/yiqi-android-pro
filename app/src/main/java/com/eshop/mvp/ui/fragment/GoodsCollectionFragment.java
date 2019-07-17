package com.eshop.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerProductDetailsComponent;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.ProductDetailsModule;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.PageUtil;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.AuditGoods;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.MockAuditGoods;
import com.eshop.mvp.http.entity.store.MockHomeGood;
import com.eshop.mvp.http.entity.store.StoreCategoryEdit;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.ProductDetailsPresenter;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.activity.product.ProductDetailsActivity;
import com.eshop.mvp.ui.activity.store.CatSetActivity;
import com.eshop.mvp.ui.adapter.CollectionGoodsQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreGoodsQuickAdapter;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 商品收藏
 * ================================================
 */
public class GoodsCollectionFragment extends BaseSupportFragment<ProductDetailsPresenter> implements ProductDetailsContract.View {

    @Inject
    List<HomeGoodBean> homeGoodBeans;

    @Inject
    CollectionGoodsQuickAdapter collectionGoodsQuickAdapter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;


    public static GoodsCollectionFragment newInstance() {
        GoodsCollectionFragment fragment = new GoodsCollectionFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProductDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .productDetailsModule(new ProductDetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_manager, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler() {
        Timber.e("initRecycler");
        //swipe_refresh.setOnRefreshListener(() -> mPresenter.getCats());
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipe_refresh.setProgressViewOffset(true, 130, 300);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        collectionGoodsQuickAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.view_empty, null));
        collectionGoodsQuickAdapter.setOnItemClickListener((adapter, view, position) -> {

            Intent intent = new Intent(_mActivity, ProductDetailsActivity.class);
            intent.putExtra("good",((HomeGoodBean)adapter.getItem(position)));
            startActivity(intent);

                }
        );

        collectionGoodsQuickAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                new MaterialDialog.Builder(getContext())
                        .content("取消收藏该商品吗！")
                        .negativeText(R.string.cancel_easy_photos)
                        .positiveText(R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                String token = BaseApp.loginBean.getToken();
                                DelId delId = new DelId();
                                String id = ((HomeGoodBean)adapter.getItem(position)).id+"";
                                delId.setDelId(id);
                                mPresenter.collectionDel(token,delId);
                            }
                        })
                         .show();

                return false;
            }
        });

        collectionGoodsQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(collectionGoodsQuickAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (!LoginUtils.isLogin(getActivity())) {
            return;
        }
        request();

        //test
        /**  GoodsBean goodsBean = new GoodsBean();
         goodsBean.pageUtil = new PageUtil();
         goodsBean.pageUtil.pageNum=1;
         goodsBean.pageUtil.pages=1;
         goodsBean.pageUtil.total=1;
         success(goodsBean);*/

    }

    private void request() {
        assert mPresenter != null;

        mPresenter.collectionGoodsFind(mNextRequestPage, BaseApp.loginBean.getToken());


    }

    private void refresh() {
        collectionGoodsQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        collectionGoodsQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request();

    }

    private void loadMore() {
        if (mNextRequestPage < pages) {
            request();
        } else {
            collectionGoodsQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            collectionGoodsQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                collectionGoodsQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            collectionGoodsQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            collectionGoodsQuickAdapter.loadMoreComplete();
        }
    }

    private void success(GoodsBean data) {
        MockHomeGood.init();
        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh = mNextRequestPage == 1;
        if (isRefresh) {

            collectionGoodsQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        if (data != null && data.goodsList != null)
            setData(isRefresh, data.goodsList);
        else setData(isRefresh, null);
    }


    @Override
    public void setData(@Nullable Object data) {

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

    }


    @Override
    public void addGoodSuccess() {

    }

    @Override
    public void getGoodDetailSuccess(ProductDetail good) {

    }

    @Override
    public void collectioGoodsFindResult(GoodsBean data) {
        success(data);
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
        showMessage("取消收藏成功");
        refresh();
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


}
