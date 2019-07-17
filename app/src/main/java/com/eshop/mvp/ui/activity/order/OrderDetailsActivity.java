package com.eshop.mvp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.cart.AppcarStore;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.OrderDetail;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.presenter.OrderPresenter;
import com.eshop.mvp.ui.activity.set.AddressActivity;
import com.eshop.mvp.ui.adapter.CreateOrderAdapter;
import com.eshop.mvp.ui.adapter.OrderDetailsAdapter;
import com.eshop.mvp.ui.widget.AmountView;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.ProgressDialogUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 订单详情
 * ================================================
 */
public class OrderDetailsActivity extends BaseSupportActivity<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_cancel_order)
    TextView tv_cancel_order;

    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    private OrderDetailsAdapter orderDetailsAdapter;
    private View headerAddressView;
    private AddressBean select_address;

    public int appClassId = 0;

    List<OrderDetail> appGoodsList;

///////////////////////////

    private String orderId;

    private String orderId_refund;

    private AppOrderForm appOrderForm;

    private boolean isneedupdate = false;

    private String action;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @OnClick({R.id.tv_pay, R.id.tv_cancel_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pay:
                if (action.equalsIgnoreCase("pay")) {
                    Intent intent = new Intent(this, PayActivity.class);
                    intent.putExtra("id", orderId);
                    intent.putExtra("money", appOrderForm.totalPrice);
                    intent.putExtra("time", appOrderForm.remainingTime);
                    intent.putExtra("appClassId", appOrderForm.apporderdetailsList.get(0).appgoods.appClassId);
                    startActivity(intent);
                    finish();
                }
                if (action.equalsIgnoreCase("send")) {
                    mPresenter.reminderShipment(orderId);
                }
                if (action.equalsIgnoreCase("receive")) {
                    Intent intent = new Intent(this, ExpressActivity.class);
                    intent.putExtra("id", orderId);
                    intent.putExtra("type", "1");
                    startActivity(intent);
                }

                break;

            case R.id.tv_cancel_order:
                if (action.equalsIgnoreCase("pay")) {
                    mPresenter.cancelOrder(orderId);
                }
                if (action.equalsIgnoreCase("send")) {
                    Intent intent = new Intent(this, RefundActivity.class);
                    intent.putExtra("id", appOrderForm.orderId);

                    startActivity(intent);
                }
                if (action.equalsIgnoreCase("receive")) {
                    mPresenter.finishOrder(orderId + "");
                }
                if (action.equalsIgnoreCase("delete")) {
                    mPresenter.deleteOrder(orderId);
                }
                break;
        }

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("订单详情");
        toolbarBack.setVisibility(View.VISIBLE);

        if (!LoginUtils.isLogin(this)) {
            return;
        }

        action = getIntent().getStringExtra("action");
        setAction();
        initRecyclerList();
        BaseApp.addressBean = null;
        orderId = getIntent().getStringExtra("id");
        mPresenter.getOrderDetails(orderId);

    }

    private void setAction() {
        if (action.equalsIgnoreCase("pay")) {
            tv_pay.setText("付款");
            tv_cancel_order.setText("取消订单");
        } else if (action.equalsIgnoreCase("send")) {
            tv_pay.setText("提醒发货");
            tv_cancel_order.setText("申请退款");
        } else if (action.equalsIgnoreCase("receive")) {
            tv_pay.setText("查看物流");
            tv_cancel_order.setText("确认收货");
        } else  if (action.equalsIgnoreCase("delete")) {
            tv_cancel_order.setText("删除订单");
        }
        else {
            tv_pay.setVisibility(View.GONE);
            tv_cancel_order.setVisibility(View.GONE);
        }
    }

    private void initRecyclerList() {
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        orderDetailsAdapter = new OrderDetailsAdapter();
        recyclerList.setAdapter(orderDetailsAdapter);

        headerAddressView = LayoutInflater.from(mContext).inflate(R.layout.header_activity_create_order, null);
        orderDetailsAdapter.setHeaderView(headerAddressView);
        orderDetailsAdapter.setOnClickCartItemListener(new OrderDetailsAdapter.OnClickCartItemListener() {
            @Override
            public void onClickAmountCount(View view, AppGoods appGoods, int count) {

            }
        });


        headerAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isneedupdate = true;
                Intent intent = new Intent(OrderDetailsActivity.this, AddressActivity.class);
                intent.putExtra("from", "orderDetail");
                startActivity(intent);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setAddress();
        if (isneedupdate) {
            isneedupdate = false;
            updateAddress();
        }
    }

    private void updateAddress() {
        mPresenter.updateOrder(BaseApp.loginBean.getToken(), orderId, null, BaseApp.addressBean.receiveUserName, BaseApp.addressBean.address, BaseApp.addressBean.receivePhone);
    }

    private void setAddress() {
        TextView receive_name = headerAddressView.findViewById(R.id.receiver_name);
        TextView receiver_phone = headerAddressView.findViewById(R.id.receiver_phone);
        TextView receiver_details = headerAddressView.findViewById(R.id.receiver_details);
        View empty = headerAddressView.findViewById(R.id.empty);
        View content = headerAddressView.findViewById(R.id.content);
        select_address = BaseApp.addressBean;

        if (select_address == null) {
            empty.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);

            try {
                receive_name.setText("收货人:" + select_address.receiveUserName);
                receiver_phone.setText(select_address.receivePhone);
                receiver_details.setText("收货地址:" + select_address.address);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    private List<OrderDetail> createData(AppOrderForm appOrderForm) {
        List<OrderDetail> appGoodsList = new ArrayList();
        int k = 0;
        OrderDetail last_appgoods = null;
        for (OrderDetail orderDetail : appOrderForm.apporderdetailsList) {
            if (k == 0) {
                orderDetail.appgoods.isHead = true;
                orderDetail.appgoods.isFoot = false;
            } else {
                orderDetail.appgoods.isHead = false;
                orderDetail.appgoods.isFoot = false;
            }
            orderDetail.remarks = appOrderForm.remarks;
            orderDetail.sum = appOrderForm.goodsAmount;
            orderDetail.small_sum = appOrderForm.totalPrice;
            appGoodsList.add(orderDetail);
            last_appgoods = orderDetail;
            k++;
        }
        if (last_appgoods != null) last_appgoods.appgoods.isFoot = true;
        return appGoodsList;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        Intent intent_a = new Intent(this, OrderActivity.class);

        startActivity(intent_a);
        finish();
    }

    @Override
    public void deleteOrderSuccess() {
        Intent intent_a = new Intent(this, OrderActivity.class);

        startActivity(intent_a);
        finish();
    }

    @Override
    public void deliverGoodsSuccess(AppOrder data) {
        showMessage("发货成功");
    }

    @Override
    public void finishOrderSuccess(AppOrder data) {
        Intent intent_a = new Intent(this, OrderActivity.class);
        intent_a.putExtra("position",4);
        startActivity(intent_a);
        finish();
    }

    @Override
    public void getOrderSuccess(AppOrder data) {

    }

    @Override
    public void getOrderDetailsSuccess(AppOrderForm data) {
        appOrderForm = data;
        appGoodsList = createData(data);
        orderDetailsAdapter.setNewData(appGoodsList);

        BaseApp.addressBean = new AddressBean();
        BaseApp.addressBean.address = data.address;
        BaseApp.addressBean.receiveUserName = data.receiveUserName;
        BaseApp.addressBean.receivePhone = data.receivePhone;

        setAddress();

        tvProductPrice.setText(data.totalPrice + "");

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
        Intent intent_a = new Intent(this, OrderActivity.class);
        intent_a.putExtra("position",2);
        startActivity(intent_a);
        finish();
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
