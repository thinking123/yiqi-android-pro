package com.eshop.mvp.ui.fragment;

import android.content.Intent;
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
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.home.MockAppOrder;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.ui.activity.order.CreateOrderActivity;
import com.eshop.mvp.ui.activity.order.ExpressActivity;
import com.eshop.mvp.ui.activity.order.OrderDetailsActivity;
import com.eshop.mvp.ui.activity.order.PayActivity;
import com.eshop.mvp.ui.activity.order.RefundActivity;
import com.eshop.mvp.ui.activity.set.AddressActivity;
import com.eshop.mvp.ui.adapter.CreateOrderAdapter;
import com.eshop.mvp.ui.adapter.OrderAdapter;
import com.eshop.mvp.ui.widget.AmountView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.presenter.OrderPresenter;

import com.eshop.R;

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
public class OrderFragment extends BaseSupportFragment<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerList;

    private OrderAdapter orderAdapter;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    private int orderStatus;

    public static OrderFragment newInstance(int status) {
        OrderFragment fragment = new OrderFragment();
        fragment.orderStatus = status;

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
        mNextRequestPage = 1;
        if (orderStatus == 10) {
            mPresenter.getOrder(mNextRequestPage + "", BaseApp.loginBean.getId() + "", null);

        } else {
            mPresenter.getOrder(mNextRequestPage + "", BaseApp.loginBean.getId() + "", orderStatus + "");
        }

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

    @Override
    public void onResume() {
        super.onResume();
       /** if(BaseApp.isOrderListNeedRefresh){
            BaseApp.isOrderListNeedRefresh = false;
            refresh();
        }*/

    }

    private void refresh() {
        orderAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        orderAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        //mPresenter.getOrder(mNextRequestPage+"",BaseApp.loginBean.getId()+"",orderStatus+"");
        if (orderStatus == 10) {
            mPresenter.getOrder(mNextRequestPage + "", BaseApp.loginBean.getId() + "", null);

        } else {
            mPresenter.getOrder(mNextRequestPage + "", BaseApp.loginBean.getId() + "", orderStatus + "");
        }
    }

    private void loadMore() {
        if (mNextRequestPage < pages) {
            //mPresenter.getOrder(mNextRequestPage+"",BaseApp.loginBean.getId()+"",orderStatus+"");
            if (orderStatus == 10) {
                mPresenter.getOrder(mNextRequestPage + "", BaseApp.loginBean.getId() + "", null);

            } else {
                mPresenter.getOrder(mNextRequestPage + "", BaseApp.loginBean.getId() + "", orderStatus + "");
            }
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
        orderAdapter = new OrderAdapter();
        orderAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
        orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerList);

        orderAdapter.setOnItemClickListener((adapter, view, position) -> {

                    AppGoods item = ((AppGoods) (adapter.getData()).get(position));

                    switch (item.orderStatus) {

                        case 0://待付款
                            Intent intent1 = new Intent(_mActivity, OrderDetailsActivity.class);
                            intent1.putExtra("id", item.carId + "");
                            intent1.putExtra("action", "pay");
                            startActivity(intent1);
                            break;
                        case 1://待发货
                            Intent intent2 = new Intent(_mActivity, OrderDetailsActivity.class);
                            intent2.putExtra("id", item.carId + "");
                            intent2.putExtra("action", "send");
                            startActivity(intent2);

                            break;
                        case 2://待收货
                            Intent intent3 = new Intent(_mActivity, OrderDetailsActivity.class);
                            intent3.putExtra("id", item.carId + "");
                            intent3.putExtra("action", "receive");
                            startActivity(intent3);
                            break;
                        case 3://已完成
                            Intent intent4 = new Intent(_mActivity, OrderDetailsActivity.class);
                            intent4.putExtra("id", item.carId + "");
                            intent4.putExtra("action", "rebuy");
                            startActivity(intent4);
                            break;
                        case 4://已取消
                            Intent intent5 = new Intent(_mActivity, OrderDetailsActivity.class);
                            intent5.putExtra("id", item.carId + "");
                            intent5.putExtra("action", "delete");
                            startActivity(intent5);
                            break;
                    }
                }
        );

        orderAdapter.setOnClickChooseListener(new OrderAdapter.OnClickChooseListener() {
            @Override
            public void onClick(String action, AppGoods item) {

                if (action.equalsIgnoreCase("cancel")) {
                    mPresenter.cancelOrder(item.carId + "");

                }

                if (action.equalsIgnoreCase("delete")) {
                    mPresenter.deleteOrder (item.carId + "");

                }

                if (action.equalsIgnoreCase("pay")) {

                    Intent intent = new Intent(_mActivity, PayActivity.class);
                    intent.putExtra("id", item.carId + "");
                    intent.putExtra("money", item.totalPrice);
                    intent.putExtra("time", item.remainingTime);
                    intent.putExtra("appClassId", item.appClassId);
                    startActivity(intent);

                }

                if (action.equalsIgnoreCase("refund")) {

                     Intent intent = new Intent(_mActivity, RefundActivity.class);
                     intent.putExtra("id", item.orderId);

                     startActivity(intent);
                }

                if (action.equalsIgnoreCase("tipsend")) {
                    mPresenter.reminderShipment(item.carId + "");

                }
                //mPresenter.reminderShipment(item.carId + "");

                if (action.equalsIgnoreCase("express")) {
                    Intent intent = new Intent(_mActivity, ExpressActivity.class);// OrderDetailsActivity.class);
                    intent.putExtra("id", item.carId + "");
                    intent.putExtra("type", "1");
                    startActivity(intent);
                }

                if (action.equalsIgnoreCase("receive")) {
                    mPresenter.finishOrder(item.carId + "");
                }


                if (action.equalsIgnoreCase("rebuy")) {
                    Intent intent = new Intent(_mActivity, OrderDetailsActivity.class);
                    intent.putExtra("id", item.carId + "");
                    intent.putExtra("action", "rebuy");
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
        refresh();
    }

    @Override
    public void deleteOrderSuccess() {
        refresh();
    }

    @Override
    public void deliverGoodsSuccess(AppOrder data) {

    }

    @Override
    public void finishOrderSuccess(AppOrder data) {
        showMessage("确认收货成功.");
        refresh();
    }

    @Override
    public void getOrderSuccess(AppOrder data) {

        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh = mNextRequestPage == 1;
        if (isRefresh) {

            orderAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }

        MockAppOrder.init(orderStatus);
        // setData(isRefresh, MockAppOrder.getData(MockAppOrder.appOrder));
        if (data != null) {
            if (data.apporderformList == null) {
                setData(isRefresh, null);
            } else {
                setData(isRefresh, MockAppOrder.getData(data));
            }
        }


    }

    @Override
    public void getOrderDetailsSuccess(AppOrderForm data) {

    }

    @Override
    public void getStoreOrderSuccess(AppOrder data) {

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

    }

    @Override
    public void reminderShipmentSuccess(String data) {
        showMessage("提醒发货成功.");
        refresh();
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
