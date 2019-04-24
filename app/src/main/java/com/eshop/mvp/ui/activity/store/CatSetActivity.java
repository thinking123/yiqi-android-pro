package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.MockStoreCats;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.CategoryId;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreCategory;
import com.eshop.mvp.http.entity.store.StoreCategoryEdit;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.ui.adapter.CatSetQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreCatQuickAdapter;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
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
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 分类设置
 * ================================================
 */
public class CatSetActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View {

    @Inject
    List<StoreCat> storeColumns;

    @Inject
    CatSetQuickAdapter catSetQuickAdapter;


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    SwipeRecyclerView mRecyclerView;


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.cat)
    TextView cat;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    /**
     * 店铺id
     */
    private String storeId;

    protected RecyclerView.ItemDecoration mItemDecoration;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .storeManagerModule(new StoreManagerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cat_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycler();
        toolbarTitle.setText("分类设置");
        toolbarBack.setVisibility(View.VISIBLE);
        if(!LoginUtils.isLogin(this)) {
            return;
        }
        storeId = BaseApp.loginBean.getStoreId() + "";
        if(BaseApp.storeCat!=null)cat.setText(BaseApp.storeCat.categoryName);
        request(storeId);
    }

    @OnClick({R.id.add_cat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_cat:

                new MaterialDialog.Builder(CatSetActivity.this)
                        .title("添加分类")
                        .negativeText(R.string.cancel_easy_photos)
                        .inputType(InputType.TYPE_CLASS_TEXT )
                        .inputRangeRes(0, 10, R.color.gray)
                        .input("最多10个字", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if(input.length()>0) {
                                    String cat_name = input.toString();
                                    if (mPresenter != null) {
                                        String token = BaseApp.loginBean.getToken();
                                        StoreCategory storeCategory = new StoreCategory();
                                        storeCategory.setCategoryName(cat_name);
                                        storeCategory.setCategoryOrder("0");
                                        storeCategory.setStoreId(storeId);
                                        mPresenter.storeColumn(token,storeCategory);
                                    }
                                }
                            }
                        }).show();


                break;

        }
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
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
        mItemDecoration = createItemDecoration();
        mRecyclerView.addItemDecoration(mItemDecoration);

        // mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

        // storeCatQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));
        catSetQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
                        BaseApp.storeCat = (StoreCat)adapter.getItem(position);
                        cat.setText(BaseApp.storeCat.categoryName);
                }
        );

        catSetQuickAdapter.setOnClickDelListener(new CatSetQuickAdapter.OnClickDelListener() {
            @Override
            public void onClick(StoreCat item) {

            }
        });

        catSetQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);

        mRecyclerView.setAdapter(catSetQuickAdapter);
    }

    private void refresh() {
        catSetQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        catSetQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request(storeId);
    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            request(storeId);

        }else{
            catSetQuickAdapter.loadMoreEnd(true);
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            catSetQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                catSetQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            catSetQuickAdapter.loadMoreEnd(true);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            catSetQuickAdapter.loadMoreComplete();
        }
    }

    private void updateData(StoreCatBean data){
       // MockStoreCats.init();

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

            catSetQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        if(data!=null)
        setData(isRefresh, data.storeColumns);
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

    }

    @Override
    public void drawingSuccess() {

    }

    @Override
    public void goodsSuccess() {

    }

    @Override
    public void goodsDelSuccess() {

    }

    @Override
    public void goodsPutSuccess() {

    }

    @Override
    public void inSalesGoodsSuccess(Audit audit) {

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

    }

    @Override
    public void stateSuccess(StoreState storeState) {

    }

    @Override
    public void stateResult(String status, String msg, StoreState storeState) {

    }


    @Override
    public void stayOnTheShelfGoodsSuccess(Audit audit) {

    }

    @Override
    public void stopSellingGoodsSuccess() {

    }

    @Override
    public void storeSuccess() {

    }

    @Override
    public void storeColumnResult(StoreCatBean data) {
        updateData(data);
    }

    @Override
    public void storeColumnSuccess() {
        showMessage("添加分类成功");
        mNextRequestPage = 1;
        request(storeId);
    }

    @Override
    public void storeColumnCreatSuccess() {
        showMessage("编辑分类成功");
        mNextRequestPage = 1;
        request(storeId);
    }

    @Override
    public void storeColumnDelSuccess() {
        showMessage("删除分类成功");
        mNextRequestPage = 1;
        request(storeId);
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
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(CatSetActivity.this).setBackground(R.drawable.selector_red)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(CatSetActivity.this).setBackground(R.drawable.selector_green)
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
                if(menuPosition==0){
                    if(mPresenter!=null){

                        StoreCat item = catSetQuickAdapter.getItem(position);
                        CategoryId categoryId = new CategoryId();
                        categoryId.setCategoryId(item.id+"");
                        mPresenter.storeColumnDel(BaseApp.loginBean.getToken(),categoryId);
                    }
                }else{
                    StoreCat item = catSetQuickAdapter.getItem(position);
                    new MaterialDialog.Builder(CatSetActivity.this)
                            .title("编辑分类")
                            .negativeText(R.string.cancel_easy_photos)
                            .inputType(InputType.TYPE_CLASS_TEXT )
                            .inputRangeRes(0, 10, R.color.gray)
                            .input("", item.categoryName, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    if(input.length()>0) {
                                        String cat_name = input.toString();
                                        if (mPresenter != null) {
                                            String token = BaseApp.loginBean.getToken();
                                            StoreCategoryEdit storeCategoryEdit = new StoreCategoryEdit();
                                            storeCategoryEdit.setCategoryId(item.id+"");
                                            storeCategoryEdit.setCategoryName(cat_name);
                                            storeCategoryEdit.setCategoryOrder("0");

                                            mPresenter.storeColumnCreat(token,storeCategoryEdit);
                                        }
                                    }
                                }
                            }).show();

                }
            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(CatSetActivity.this, "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
    }

}
