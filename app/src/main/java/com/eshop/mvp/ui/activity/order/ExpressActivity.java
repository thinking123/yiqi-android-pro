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
import com.eshop.mvp.http.entity.home.MockExpress;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.ExpressTime;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.OrderDetail;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.presenter.OrderPresenter;
import com.eshop.mvp.ui.activity.set.AddressActivity;
import com.eshop.mvp.ui.adapter.ExpressAdapter;
import com.eshop.mvp.ui.adapter.OrderDetailsAdapter;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 物流信息
 * ================================================
 */
public class ExpressActivity extends BaseSupportActivity<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.company)
    TextView company;

    @BindView(R.id.num)
    TextView num;
    private ExpressAdapter expressAdapter;

    List<ExpressTime> expressTimeList;

    String type = "1"; //1订单物流，2售后物流

///////////////////////////

    private String orderId;


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
        return R.layout.activity_express; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }



    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("物流状态");
        toolbarBack.setVisibility(View.VISIBLE);

        if (!LoginUtils.isLogin(this)) {
            return;
        }

        initRecyclerList();

        orderId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        if(type==null)type="1";
        mPresenter.logistics(orderId,type);

    }


    private void initRecyclerList() {
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        expressAdapter = new ExpressAdapter();
        recyclerList.setAdapter(expressAdapter);


    }

    private void setData(ExpressState expressState){
        state.setText(MockExpress.getState(expressState.state));
        if(expressState.expressCompanyName!=null)
            company.setText(expressState.expressCompanyName);
        else
            company.setText(expressState.expressCompany);
        num.setText(expressState.expressNumber);
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

    }

    @Override
    public void deleteOrderSuccess() {

    }

    @Override
    public void deliverGoodsSuccess(AppOrder data) {

    }

    @Override
    public void finishOrderSuccess(AppOrder data) {

    }

    @Override
    public void getOrderSuccess(AppOrder data) {

    }

    @Override
    public void getOrderDetailsSuccess(AppOrderForm data) {


    }

    @Override
    public void getStoreOrderSuccess(AppOrder data) {

    }

    @Override
    public void logisticsSuccess(ExpressState data) {
       // MockExpress.init();
       // expressAdapter.setNewData(MockExpress.expressTimeList);

       // setData(MockExpress.expressState);
        setData(data);
        expressAdapter.setNewData(data.logisticslist);
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
