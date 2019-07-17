package com.eshop.mvp.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.module.AfterSaleModule;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.home.MockAfterSaleOrder;
import com.eshop.mvp.http.entity.home.MockAppOrder;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.IdBean;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.ui.activity.order.DoAfterSaleActivity;
import com.eshop.mvp.ui.activity.order.ExpressActivity;
import com.eshop.mvp.ui.activity.order.RefundActivity;
import com.eshop.mvp.ui.activity.order.RefundFinishActivity;
import com.eshop.mvp.ui.activity.order.RefundStateActivity;
import com.eshop.mvp.ui.activity.order.RefundSucessActivity;
import com.eshop.mvp.ui.activity.store.DeliverGoodsActivity;
import com.eshop.mvp.ui.adapter.AfterSaleOrderAdapter;
import com.eshop.mvp.ui.adapter.StoreOrderAdapter;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerAfterSaleComponent;
import com.eshop.mvp.contract.AfterSaleContract;
import com.eshop.mvp.presenter.AfterSalePresenter;

import com.eshop.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 售后订单
 * ================================================
 */
public class AfterSaleFragment extends BaseSupportFragment<AfterSalePresenter> implements AfterSaleContract.View {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerList;

    private AfterSaleOrderAdapter orderAdapter;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    /**
     * 订单状态 1 售后处理中 3失败的 6 完成的
     */

    private int orderStatus;
    private String type = "client";
    /**
     * 商品状态 1 等待商家处理 2 商家已处理 3 商家未同意 4 物流中 5 退款中 退款完成 ,
     */
    private String state;

    private String storeId;

    private String phone;

    public static AfterSaleFragment newInstance(int status, String type) {
        AfterSaleFragment fragment = new AfterSaleFragment();
        fragment.orderStatus = status;
        fragment.type = type;
        //fragment.orderId = orderId;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAfterSaleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .afterSaleModule(new AfterSaleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_after_sale, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerList();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (!LoginUtils.isLogin(getActivity())) {
            return;
        }
        if (type.equalsIgnoreCase("store")) {
            storeId = BaseApp.loginBean.getStoreId()+"";
        }
        mPresenter.beingProcessedTab(BaseApp.loginBean.getToken(), mNextRequestPage + "", orderStatus + "", storeId);


    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            orderAdapter.setNewData(data);
        } else {
            if (size > 0) {
                orderAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            orderAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            orderAdapter.loadMoreComplete();
        }
    }

    private void refresh() {
        orderAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        orderAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mPresenter.beingProcessedTab(BaseApp.loginBean.getToken(), mNextRequestPage + "", orderStatus + "", storeId);

    }

    private void loadMore() {
        if (mNextRequestPage < pages) {
            mPresenter.beingProcessedTab(BaseApp.loginBean.getToken(), mNextRequestPage + "", orderStatus + "", storeId);

        } else {
            orderAdapter.loadMoreEnd();
        }

    }

    private void initRecyclerList() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipe_refresh.setProgressViewOffset(true, 30, 130);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));
        recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter = new AfterSaleOrderAdapter(type);
        orderAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
        orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerList);
        orderAdapter.setOnItemClickListener((adapter, view, position) -> {

                    AfterSaleOrder.AfterSaleTabsBean.GoodsListBean item = ((AfterSaleOrder.AfterSaleTabsBean.GoodsListBean) (adapter.getData()).get(position));
                    if(type.equalsIgnoreCase("client")){
                        state = item.afterSaleTabsBean.getState();
                        /**
                         * 商品状态 1 等待商家处理 2 商家已处理 3 商家未同意 4 物流中 5 退款中 退款完成 ,
                         */
                        if(state.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(getActivity(), RefundStateActivity.class);
                            intent.putExtra("orderId", item.afterSaleTabsBean.getOrederId());
                            intent.putExtra("id", item.afterSaleTabsBean.getId() + "");
                            startActivity(intent);
                        }else if(state.equalsIgnoreCase("2")) {
                            Intent intent = new Intent(getActivity(), RefundSucessActivity.class);
                            intent.putExtra("orderId", item.afterSaleTabsBean.getOrederId());
                            intent.putExtra("id", item.afterSaleTabsBean.getId() + "");
                            startActivity(intent);
                        }
                    }
                }
        );
        orderAdapter.setOnClickChooseListener(new AfterSaleOrderAdapter.OnClickChooseListener() {
            @Override
            public void onClick(String action, AfterSaleOrder.AfterSaleTabsBean.GoodsListBean item) {


                if (action.equalsIgnoreCase("precess")) {
                    Intent intent = new Intent(getActivity(), DoAfterSaleActivity.class);
                    intent.putExtra("storeid", item.afterSaleTabsBean.getStoreId() + "");
                    intent.putExtra("id", item.afterSaleTabsBean.getId() + "");
                    intent.putExtra("isLogistics", item.afterSaleTabsBean.getIsLogistics());
                    startActivity(intent);
                }

                if (action.equalsIgnoreCase("refund")) {
                    Intent intent = new Intent(getActivity(), RefundActivity.class);
                    intent.putExtra("id", item.afterSaleTabsBean.getOrederId());
                    intent.putExtra("isreopen", true);

                    startActivity(intent);
                }
                if (action.equalsIgnoreCase("look")) {
                    Intent intent = new Intent(getActivity(), ExpressActivity.class);
                    intent.putExtra("id", item.afterSaleTabsBean.getId());
                    intent.putExtra("type", "2");
                    startActivity(intent);
                }
                if (action.equalsIgnoreCase("detail")) {
                    Intent intent = new Intent(getActivity(), RefundFinishActivity.class);
                    intent.putExtra("id", item.afterSaleTabsBean.getId());
                    intent.putExtra("orderId", item.afterSaleTabsBean.getOrederId());
                    intent.putExtra("type", "2");
                    startActivity(intent);
                }

                if (action.equalsIgnoreCase("confirm")) {
                    IdBean idBean = new IdBean();
                    idBean.setId(item.afterSaleTabsBean.getId());
                    mPresenter.confirmTheRefundUser(BaseApp.loginBean.getToken(),idBean);
                }

                if (action.equalsIgnoreCase("phone")) {
                    BaseApp.tabindex = 1;
                    Intent intent1 = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent1);
                   // mPresenter.applyRefundDetails(BaseApp.loginBean.getToken(),item.afterSaleTabsBean.getId());
                }


            }
        });
        recyclerList.setAdapter(orderAdapter);

    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(getActivity());
        rxPermission
                .requestEach(
                        Manifest.permission.CALL_PHONE
                )
                .subscribe(permission -> {
                    if (permission.granted) {

                            callPhone(phone);
                        // 用户已经同意该权限
                        Timber.e("%s is granted.", permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Timber.d("%s is denied. More info should be provided.", permission.name);
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Timber.e("%s is denied.", permission.name);
                    }
                });


    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
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
    public void applyRefundSuccess(String data) {

    }

    @Override
    public void applySuccess() {

    }

    @Override
    public void applyRefundDetailsSuccess(RefundDetail data) {
        if(data!=null){
            if(data.getPlatformPhone()!=null && !data.getPlatformPhone().isEmpty()){
                phone = data.getPlatformPhone();
                requestPermissions();
            }
        }
    }

    @Override
    public void applyRefundDelSuccess() {

    }

    @Override
    public void applyRefundPutSuccess() {

    }

    @Override
    public void applyRefundByIdSuccess(RefundInfo data) {

    }

    @Override
    public void beingProcessedTabSuccess(AfterSaleOrder data) {
        setData(true, MockAfterSaleOrder.getData(data));
    }

    @Override
    public void handlingAfterSalesSuccess(AfterSaleStore data) {

    }

    @Override
    public void handlingAfterSalesSuccess(String data) {

    }

    @Override
    public void appLogisticsSuccess(List<AppLog> data) {

    }

    @Override
    public void logisticsSuccess(String data) {

    }

    @Override
    public void logisticsAllSuccess(List<ExpressCode> list) {

    }

    @Override
    public void confirmTheRefundSuccess() {

    }

    @Override
    public void confirmTheRefundUserSuccess() {
        showMessage("确认收款成功");
    }

    @Override
    public void refundSuccess(RefundDetail2 data) {

    }

    @Override
    public void applyRefundDetailsUserSuccess(RefundDetailUser data) {

    }

    @Override
    public void getOrderMsgSuccess(AppOrderForm data) {

    }

    @Override
    public void updateUserImageSuccess(String url) {

    }
}
