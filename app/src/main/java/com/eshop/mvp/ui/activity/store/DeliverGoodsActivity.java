package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.presenter.OrderPresenter;
import com.eshop.mvp.ui.fragment.OrderFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.SimpleSpinnerTextFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 发货
 * ================================================
 */
public class DeliverGoodsActivity extends BaseSupportActivity<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.receiver_name)
    TextView receiver_name;
    @BindView(R.id.receiver_phone)
    TextView receiver_phone;
    @BindView(R.id.receiver_details)
    TextView receiver_details;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.nice_spinner_express)
    NiceSpinner nice_spinner_express;

    private String orderId;

    private AppOrderForm appOrderForm;

    private List<ExpressCode> expressCodeList;

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
        return R.layout.activity_deliver_goods; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("发货");
        toolbarBack.setVisibility(View.VISIBLE);

        orderId = getIntent().getStringExtra("id");
        mPresenter.getOrderDetails(orderId);
        mPresenter.logisticsAll();

        nice_spinner_express.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ExpressCode expressCode = expressCodeList.get(position);

                appOrderForm.expressCompany = expressCode.getName();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ExpressCode expressCode = expressCodeList.get(0);
                appOrderForm.expressCompany = expressCode.getName();
            }
        });

    }

    private void setData(){
        receiver_name.setText(appOrderForm.receiveUserName);
        receiver_phone.setText(appOrderForm.receivePhone);
        receiver_details.setText(appOrderForm.address);
        code.setText(appOrderForm.expressNumber);

    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        if(code.getText().toString().isEmpty()){
            showMessage("请输入物流单号");
        }else{
            try {
                if (appOrderForm.expressCompany == null || appOrderForm.expressCompany.isEmpty()) {
                    appOrderForm.expressCompany = expressCodeList.get(0).getName();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            mPresenter.deliverGoods(orderId,appOrderForm.expressCompany,code.getText().toString());
        }

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
        showMessage("发货成功");
    }

    @Override
    public void finishOrderSuccess(AppOrder data) {

    }

    @Override
    public void getOrderSuccess(AppOrder data) {

    }

    @Override
    public void getOrderDetailsSuccess(AppOrderForm data) {
        appOrderForm = data;
        setData();
    }

    @Override
    public void getStoreOrderSuccess(AppOrder data) {

    }

    @Override
    public void logisticsSuccess(ExpressState data) {

    }

    @Override
    public void logisticsAllSuccess(List<ExpressCode> list) {
        SimpleSpinnerTextFormatter textFormatter = new SimpleSpinnerTextFormatter() {
            @Override
            public Spannable format(Object item) {
                ExpressCode expressCode = (ExpressCode) item;
                return new SpannableString(expressCode.getName());
            }
        };

        nice_spinner_express.setSpinnerTextFormatter(textFormatter);
        nice_spinner_express.setSelectedTextFormatter(textFormatter);

        nice_spinner_express.attachDataSource(list);
        expressCodeList = list;
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
