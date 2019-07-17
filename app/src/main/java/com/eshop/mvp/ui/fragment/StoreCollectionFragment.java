package com.eshop.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.eshop.di.module.ProductDetailsModule;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.Store;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.http.entity.store.MockHomeGood;
import com.eshop.mvp.http.entity.store.MockStoreGoods;
import com.eshop.mvp.presenter.ProductDetailsPresenter;
import com.eshop.mvp.ui.activity.product.StoreActivity;
import com.eshop.mvp.ui.adapter.CollectionGoodsQuickAdapter;
import com.eshop.mvp.ui.adapter.CollectionStoreQuickAdapter;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 商品收藏
 * ================================================
 */
public class StoreCollectionFragment extends BaseSupportFragment<ProductDetailsPresenter> implements ProductDetailsContract.View {

    @Inject
    List<Store> storeList;

    @Inject
    CollectionStoreQuickAdapter collectionStoreQuickAdapter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;


    public static StoreCollectionFragment newInstance() {
        StoreCollectionFragment fragment = new StoreCollectionFragment();
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

        collectionStoreQuickAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.view_empty, null));
        collectionStoreQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
            String storeId = ((Store)adapter.getItem(position)).id+"";
            Intent intent = new Intent(getActivity(), StoreActivity.class);
            intent.putExtra("id", storeId);
            startActivity(intent);
                }
        );

        collectionStoreQuickAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                new MaterialDialog.Builder(getContext())
                        .content("取消关注该店铺吗！")
                        .negativeText(R.string.cancel_easy_photos)
                        .positiveText(R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                String token = BaseApp.loginBean.getToken();
                                DelId delId = new DelId();
                                String id = ((Store)adapter.getItem(position)).id+"";
                                delId.setDelId(id);
                                mPresenter.collectionDelStore(token,delId);
                            }
                        })
                        .show();

                return false;
            }
        });

        collectionStoreQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);

        mRecyclerView.setAdapter(collectionStoreQuickAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if(!LoginUtils.isLogin(getActivity())) {
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

    private void request(){
        assert mPresenter != null;

        mPresenter.collectionStoreFind(mNextRequestPage,BaseApp.loginBean.getToken());


    }

    private void refresh() {
        collectionStoreQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        collectionStoreQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request();

    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            request();
        }else{
            collectionStoreQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            collectionStoreQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                collectionStoreQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            collectionStoreQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            collectionStoreQuickAdapter.loadMoreComplete();
        }
    }

    private void success(StoresBean data){
        MockStoreGoods.init();
        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            collectionStoreQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        if(data!=null && data.collectionStoreList!=null)
            setData(isRefresh, data.collectionStoreList);
        else setData(isRefresh,null);
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

    }

    @Override
    public void collectionStoreFindResult(StoresBean data) {
        success(data);
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
        showMessage("取消关注成功");
        refresh();

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
