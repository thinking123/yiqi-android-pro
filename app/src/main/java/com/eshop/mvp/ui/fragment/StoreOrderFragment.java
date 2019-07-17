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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.home.MockAppOrder;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.presenter.OrderPresenter;
import com.eshop.mvp.ui.activity.order.ExpressActivity;
import com.eshop.mvp.ui.activity.order.OrderDetailsActivity;
import com.eshop.mvp.ui.activity.order.PayActivity;
import com.eshop.mvp.ui.activity.order.RefundActivity;
import com.eshop.mvp.ui.activity.order.StoreOrderDetailsActivity;
import com.eshop.mvp.ui.activity.store.DeliverGoodsActivity;
import com.eshop.mvp.ui.adapter.OrderAdapter;
import com.eshop.mvp.ui.adapter.StoreOrderAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/01/2019 23:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class StoreOrderFragment extends BaseSupportFragment<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerList;

    private StoreOrderAdapter orderAdapter;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    private int orderStatus;

    private String storeId;

    public static StoreOrderFragment newInstance(int status,String storeId) {
        StoreOrderFragment fragment = new StoreOrderFragment();
        fragment.orderStatus = status;
        fragment.storeId = storeId;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerList();
       // setData(null);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;
        if (orderStatus == 10) {
            mPresenter.getStoreOrder(mNextRequestPage + "", storeId, null);

        } else {
            mPresenter.getStoreOrder(mNextRequestPage + "", storeId, orderStatus + "");
        }
        //test

       // MockAppOrder.init(orderStatus);
        //setData(true, MockAppOrder.getData(MockAppOrder.appOrder));
    }

    @Override
    public void setData(@Nullable Object data) {
       // MockAppOrder.init(0);

       // orderAdapter.setNewData(MockAppOrder.getData(MockAppOrder.appOrder));
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
        if (orderStatus == 10) {
            mPresenter.getStoreOrder(mNextRequestPage + "", storeId, null);

        } else {
            mPresenter.getStoreOrder(mNextRequestPage + "", storeId, orderStatus + "");
        }
    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            if (orderStatus == 10) {
                mPresenter.getStoreOrder(mNextRequestPage + "", storeId, null);

            } else {
                mPresenter.getStoreOrder(mNextRequestPage + "", storeId, orderStatus + "");
            }
        }else{
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

        orderAdapter = new StoreOrderAdapter();
        orderAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
        orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },recyclerList);

        orderAdapter.setOnItemClickListener((adapter, view, position) -> {

                    AppGoods item = ((AppGoods) (adapter.getData()).get(position));

                    switch (item.orderStatus) {

                        case 0://待付款
                            Intent intent = new Intent(_mActivity, StoreOrderDetailsActivity.class);
                            intent.putExtra("id", item.carId + "");
                            intent.putExtra("action", "price");
                            intent.putExtra("freightState",item.freightState);

                            startActivity(intent);
                            break;
                        case 1://待发货
                            Intent intent1 = new Intent(_mActivity, StoreOrderDetailsActivity.class);
                            intent1.putExtra("id", item.carId + "");
                            intent1.putExtra("action", "send");
                            startActivity(intent1);

                            break;
                        case 2://待收货
                            Intent intent2 = new Intent(_mActivity, StoreOrderDetailsActivity.class);
                            intent2.putExtra("id", item.carId + "");
                            intent2.putExtra("action", "look");
                            startActivity(intent2);

                            break;
                        case 3://已完成


                            break;
                        case 4://已取消

                            break;
                    }
                }
        );

        orderAdapter.setOnClickChooseListener(new StoreOrderAdapter.OnClickChooseListener() {
            @Override
            public void onClick(String action, AppGoods item) {

                if(action.equalsIgnoreCase("price")){
                    Intent intent = new Intent(_mActivity, StoreOrderDetailsActivity.class);
                    intent.putExtra("id", item.carId + "");
                    intent.putExtra("action", "price");
                    startActivity(intent);
                }

                if(action.equalsIgnoreCase("pay")){
                    mPresenter.payment(item.carId+"");
                }

                if(action.equalsIgnoreCase("tipsend"))
                {
                    Intent intent = new Intent(_mActivity, DeliverGoodsActivity.class);
                    intent.putExtra("id",item.carId+"");
                    startActivity(intent);
                }

                if(action.equalsIgnoreCase("express")){
                    Intent intent = new Intent(_mActivity, ExpressActivity.class);
                    intent.putExtra("id",item.carId+"");
                    intent.putExtra("type", "1");
                    startActivity(intent);
                }



            }
        });
        recyclerList.setAdapter(orderAdapter);

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
    public void addOrderSuccess(Order data) {

    }

    @Override
    public void addOrderSuccess(List<Order> data) {

    }

    @Override
    public void alipayPaySuccess(String data) {

    }


    @Override
    public void alipayPayNotifySuccess(PayRet data) {

    }

    @Override
    public void cancelOrderSuccess(AppOrder data) {
        showMessage("订单取消成功.");
    }

    @Override
    public void deleteOrderSuccess() {

    }

    @Override
    public void deliverGoodsSuccess(AppOrder data) {

    }

    @Override
    public void finishOrderSuccess(AppOrder data) {
        showMessage("确认收货成功.");
    }

    @Override
    public void getOrderSuccess(AppOrder data) {



    }

    @Override
    public void getOrderDetailsSuccess(AppOrderForm data) {

    }

    @Override
    public void getStoreOrderSuccess(AppOrder data) {
        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            orderAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }

        MockAppOrder.init(orderStatus);
        if(data.apporderformList==null){
            setData(isRefresh, null);
        }else {
            setData(isRefresh, MockAppOrder.getData(data));
            //setData(isRefresh, MockAppOrder.getData(MockAppOrder.appOrder));
        }
    }

    @Override
    public void logisticsSuccess(ExpressState data) {

    }

    @Override
    public void logisticsAllSuccess(List<ExpressCode> list) {

    }

    @Override
    public void monthPaySuccess(PayRet data) {

    }

    @Override
    public void monthPayStatus(String status, String msg) {

    }

    @Override
    public void paymentSuccess(String data) {
        showMessage("已提醒付款");
    }

    @Override
    public void reminderShipmentSuccess(String data) {
        showMessage("提醒发货成功.");
    }

    @Override
    public void wxpaySuccess(PayRet data) {

    }

    @Override
    public void getAddressList(List<AddressBean> list) {

    }

    @Override
    public void updateOrderSuccess() {

    }
}
