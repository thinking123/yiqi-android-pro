package com.eshop.mvp.ui.activity.store;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
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
import com.eshop.mvp.http.entity.store.DelAccountInfo;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.adapter.BankSetQuickAdapter;
import com.eshop.mvp.ui.adapter.CatSetQuickAdapter;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 账户选择
 * ================================================
 */
public class BankSetActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View {

    @Inject
    List<BankCard> bankCardList;

    @Inject
    BankSetQuickAdapter bankSetQuickAdapter;


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

    /**
     * 店铺id
     */
    private String storeId;

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
        return R.layout.activity_bank_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycler();
        toolbarTitle.setText("账户选择");
        toolbarBack.setVisibility(View.VISIBLE);

        if(!LoginUtils.isLogin(this)) {
            return;
        }

        storeId = BaseApp.loginBean.getStoreId()+"";
        request(storeId);
    }



    private void request(String id){
        assert mPresenter != null;

        mPresenter.banCarAll(BaseApp.loginBean.getToken(),storeId,mNextRequestPage+"");
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
        View  top = getLayoutInflater().inflate(R.layout.adapter_item_bank_foot, (ViewGroup) mRecyclerView.getParent(), false);
        bankSetQuickAdapter.addFooterView(top);

        View addtop = LayoutInflater.from(this).inflate(R.layout.adapter_item_bank_foot, null);
        bankSetQuickAdapter.setEmptyView(addtop);
       // bankSetQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.adapter_item_bank_foot, null));
        bankSetQuickAdapter.setOnItemClickListener((adapter, view, position) -> {

                }
        );

        bankSetQuickAdapter.setOnClickDelListener(new BankSetQuickAdapter.OnClickDelListener() {
            @Override
            public void onClick(BankCard item) {
                new  MaterialDialog.Builder(BankSetActivity.this)
                        .title("要删除该银行卡吗?")
                        .backgroundColorRes(R.color.white)
                        .titleColorRes(R.color.color_3333)
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if(item!=null){
                                    DelAccountInfo delAccountInfo = new DelAccountInfo();
                                    delAccountInfo.setBankId(item.accountnumber);
                                    delAccountInfo.setStoreId(BaseApp.loginBean.getStoreId()+"");
                                    mPresenter.bankIdDel(BaseApp.loginBean.getToken(),delAccountInfo);
                                }
                            }
                        })
                        .show();


            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankSetActivity.this,AddAccountActivity.class);
                startActivity(intent);
            }
        });

        addtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankSetActivity.this,AddAccountActivity.class);
                startActivity(intent);
            }
        });

        bankSetQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);

        mRecyclerView.setAdapter(bankSetQuickAdapter);
    }

    private void refresh() {
        bankSetQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        bankSetQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request(storeId);
    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            request(storeId);

        }else{
            bankSetQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            bankSetQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                bankSetQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            bankSetQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            bankSetQuickAdapter.loadMoreComplete();
        }
    }

    private void updateData(BankCards data){
        //MockStoreCats.init();

        if(data!=null) {
            if(data.pageUtil!=null)
                PAGE_SIZE = data.pageUtil.total;
            if(data.pageUtil!=null)
                pages = data.pageUtil.pages;
        }

        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            bankSetQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        setData(isRefresh, data.carLists);
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
    public void banCarAllSuccess(BankCards data) {
        updateData(data);
    }

    @Override
    public void bankIdDelSuccess() {
            showMessage("删除银行卡成功");
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


}
