package com.eshop.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.http.entity.home.PageUtil;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.AuditGoods;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.CategoryId;
import com.eshop.mvp.http.entity.store.GoodsId;
import com.eshop.mvp.http.entity.store.MockAuditGoods;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreCategoryEdit;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.ui.activity.product.CatProductActivity;
import com.eshop.mvp.ui.activity.product.ProductDetailsActivity;
import com.eshop.mvp.ui.activity.store.CatSetActivity;
import com.eshop.mvp.ui.activity.store.EditGoodsActivity;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreGoodsQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.presenter.StoreManagerPresenter;

import com.eshop.R;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 商品管理
 * ================================================
 */
public class StoreManagerFragment extends BaseSupportFragment<StoreManagerPresenter> implements StoreManagerContract.View {

    @Inject
    List<AuditGoods> auditGoodsList;

    @Inject
    StoreGoodsQuickAdapter storeGoodsQuickAdapter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    SwipeRecyclerView mRecyclerView;

    private int catid = 0;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    protected RecyclerView.ItemDecoration mItemDecoration;


    public static StoreManagerFragment newInstance(int id) {
        StoreManagerFragment fragment = new StoreManagerFragment();
        fragment.catid = id;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerStoreManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .storeManagerModule(new StoreManagerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(BaseApp.isGoodsNeedRefresh){
            refresh();
        }
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
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
        mItemDecoration = createItemDecoration();
        mRecyclerView.addItemDecoration(mItemDecoration);


        storeGoodsQuickAdapter.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.view_empty, null));
        storeGoodsQuickAdapter.setOnItemClickListener((adapter, view, position) -> {

            Intent intent = new Intent(_mActivity, ProductDetailsActivity.class);
            AuditGoods auditGoods = (AuditGoods)adapter.getItem(position);
            HomeGoodBean homeGoodBean = new HomeGoodBean();
            homeGoodBean.id = auditGoods.id;
            intent.putExtra("good",homeGoodBean);
            startActivity(intent);

                }
        );

        storeGoodsQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(storeGoodsQuickAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (!LoginUtils.isLogin(getActivity())) {
            return;
        }
        request();

        //test
        /** Audit audit = new Audit();
         audit.pageUtil = new PageUtil();
         audit.pageUtil.pageNum=1;
         audit.pageUtil.pages=1;
         audit.pageUtil.total=1;
         success(audit);*/

    }

    private void request() {
        assert mPresenter != null;
        if (catid == 0) {
            mPresenter.inSalesGoods(BaseApp.loginBean.getToken(), mNextRequestPage);
        }
        if (catid == 1) {
            mPresenter.checkPendingGoods(BaseApp.loginBean.getToken(), mNextRequestPage);
        }
        if (catid == 2) {
            mPresenter.stayOnTheShelfGoods(BaseApp.loginBean.getToken(), mNextRequestPage);
        }

    }

    private void refresh() {
        storeGoodsQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        storeGoodsQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request();

    }

    private void loadMore() {
        if (mNextRequestPage < pages) {
            request();
        } else {
            storeGoodsQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            storeGoodsQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                storeGoodsQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            storeGoodsQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            storeGoodsQuickAdapter.loadMoreComplete();
        }
    }

    private void success(Audit data) {
        MockAuditGoods.init();
        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh = mNextRequestPage == 1;
        if (isRefresh) {

            storeGoodsQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        if (data.goodsList == null) {
            setData(isRefresh, null);
        } else {
            setData(isRefresh, data.goodsList);
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
        success(audit);
    }

    @Override
    public void drawingSuccess() {

    }

    @Override
    public void goodsSuccess() {

    }

    @Override
    public void goodsDelSuccess() {
        showMessage("删除商品成功。");
        refresh();
    }

    @Override
    public void goodsPutSuccess() {

    }

    @Override
    public void inSalesGoodsSuccess(Audit audit) {
        success(audit);
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
        showMessage("商品上架成功");
    }

    @Override
    public void stateSuccess(StoreState storeState) {

    }

    @Override
    public void stateResult(String status, String msg, StoreState storeState) {

    }


    @Override
    public void stayOnTheShelfGoodsSuccess(Audit audit) {
        success(audit);
    }

    @Override
    public void stopSellingGoodsSuccess() {
        showMessage("商品下架成功");
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

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            if(catid==1)
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity()).setBackground(R.drawable.selector_red)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(getActivity()).setBackground(R.drawable.selector_green)
                        .setText("编辑")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }else if(catid==0){
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity()).setBackground(R.drawable.selector_red)
                        .setText("下架")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }else{
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity()).setBackground(R.drawable.selector_red)
                        .setText("上架")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(getActivity()).setBackground(R.drawable.selector_green)
                        .setText("编辑")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if(catid==1) {
                    if (menuPosition == 0) {

                        AuditGoods auditGoods = storeGoodsQuickAdapter.getItem(position);
                        String token = BaseApp.loginBean.getToken();
                        new MaterialDialog.Builder(getActivity())
                                .title("确定删除该商品吗？")
                                .negativeText(R.string.cancel_easy_photos)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        if (mPresenter != null) {
                                            GoodsId goodsId = new GoodsId();
                                            goodsId.setGoodsId(auditGoods.id + "");

                                            mPresenter.goodsDel(token, goodsId);
                                        }
                                    }
                                })
                                .show();


                    } else {
                        AuditGoods auditGoods = storeGoodsQuickAdapter.getItem(position);
                        Intent intent3 = new Intent(getActivity(), EditGoodsActivity.class);
                        intent3.putExtra("goodsId", auditGoods.id + "");
                        startActivity(intent3);
                    }
                }else if(catid==0){
                    if (menuPosition == 0) {

                        AuditGoods auditGoods = storeGoodsQuickAdapter.getItem(position);
                        String token = BaseApp.loginBean.getToken();
                        new MaterialDialog.Builder(getActivity())
                                .title("确定下架该商品吗？")
                                .negativeText(R.string.cancel_easy_photos)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        if (mPresenter != null) {
                                            GoodsId goodsId = new GoodsId();
                                            goodsId.setGoodsId(auditGoods.id + "");

                                            mPresenter.stopSellingGoods(token, goodsId);
                                        }
                                    }
                                })
                                .show();


                    }
                }else{
                    if (menuPosition == 0) {

                        AuditGoods auditGoods = storeGoodsQuickAdapter.getItem(position);
                        String token = BaseApp.loginBean.getToken();
                        new MaterialDialog.Builder(getActivity())
                                .title("确定上架该商品吗？")
                                .negativeText(R.string.cancel_easy_photos)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        if (mPresenter != null) {
                                            GoodsId goodsId = new GoodsId();
                                            goodsId.setGoodsId(auditGoods.id + "");

                                            mPresenter.sellingGoods(token, goodsId);
                                        }
                                    }
                                })
                                .show();


                    } else {
                        AuditGoods auditGoods = storeGoodsQuickAdapter.getItem(position);
                        Intent intent3 = new Intent(getActivity(), EditGoodsActivity.class);
                        intent3.putExtra("goodsId", auditGoods.id + "");
                        startActivity(intent3);
                    }

                }
            }
        }


        protected RecyclerView.ItemDecoration createItemDecoration() {
            return new DefaultItemDecoration(ContextCompat.getColor(getActivity(), R.color.divider_color));
        }

    };

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultItemDecoration(ContextCompat.getColor(getActivity(), R.color.divider_color));
    }
}

