package com.eshop.mvp.ui.activity.order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.cart.AppGoods;
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
import com.eshop.mvp.ui.activity.store.DeliverGoodsActivity;
import com.eshop.mvp.ui.activity.store.PublishGoodsActivity;
import com.eshop.mvp.ui.adapter.OrderDetailsAdapter;
import com.eshop.mvp.ui.adapter.StoreOrderAdapter;
import com.eshop.mvp.ui.adapter.StoreOrderDetailsAdapter;
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
 * 订单详情
 * ================================================
 */
public class StoreOrderDetailsActivity extends BaseSupportActivity<OrderPresenter> implements OrderContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    @BindView(R.id.btn_commit)
    Button btn_commit;

    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tv_look)
    TextView tv_look;

    @BindView(R.id.lay)
    View lay;

    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    private StoreOrderDetailsAdapter orderDetailsAdapter;
    private View headerAddressView;
    private AddressBean select_address;

    public int appClassId = 0;

    List<OrderDetail> appGoodsList;

    private int freightState = 0;

///////////////////////////

    private String orderId;

    private AppOrderForm appOrderForm;

    private boolean isneedupdate = false;

    private String action;

    MaterialDialog dialog;
    private EditText priceEditText;

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
        return R.layout.activity_store_order_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @OnClick({R.id.btn_commit,R.id.tv_send,R.id.tv_look})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                if(freightState==0){
                    showMessage("免邮商品不能修改运费");
                }else {
                    showDiloag();
                }
                break;
            case R.id.tv_send:
                Intent intent = new Intent(this, DeliverGoodsActivity.class);
                intent.putExtra("id",orderId);
                startActivity(intent);
                break;
            case R.id.tv_look:
                Intent intent1 = new Intent(this, ExpressActivity.class);
                intent1.putExtra("id",orderId);
                intent1.putExtra("type", "1");
                startActivity(intent1);
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
        freightState = getIntent().getIntExtra("freightState",0);

        initRecyclerList();
        BaseApp.addressBean = null;
        orderId = getIntent().getStringExtra("id");
        mPresenter.getOrderDetails(orderId);

        if(action.equalsIgnoreCase("price")){
            btn_commit.setVisibility(View.VISIBLE);
            tv_send.setVisibility(View.GONE);
            tv_look.setVisibility(View.GONE);
            lay.setVisibility(View.GONE);
        }else if(action.equalsIgnoreCase("send")){
            tv_send.setVisibility(View.VISIBLE);
            tv_look.setVisibility(View.GONE);
            btn_commit.setVisibility(View.GONE);
            lay.setVisibility(View.VISIBLE);
        }else if(action.equalsIgnoreCase("look")){
            tv_look.setVisibility(View.VISIBLE);
            tv_send.setVisibility(View.GONE);
            btn_commit.setVisibility(View.GONE);
            lay.setVisibility(View.VISIBLE);
        }

    }



    private void initRecyclerList() {
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        orderDetailsAdapter = new StoreOrderDetailsAdapter();
        recyclerList.setAdapter(orderDetailsAdapter);

        headerAddressView = LayoutInflater.from(mContext).inflate(R.layout.header_activity_create_order, null);
        orderDetailsAdapter.setHeaderView(headerAddressView);
        orderDetailsAdapter.setOnClickChooseListener(new StoreOrderAdapter.OnClickChooseListener() {
            @Override
            public void onClick(String action, AppGoods item) {

                if(action.equalsIgnoreCase("fright")){
                   // showDiloag();
                }

            }
        });



        headerAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /**  if(action.equalsIgnoreCase("price")) {
                    isneedupdate = true;
                    Intent intent = new Intent(StoreOrderDetailsActivity.this, AddressActivity.class);
                    intent.putExtra("from", "orderDetail");
                    startActivity(intent);
                }*/

            }
        });
    }

    private void showDiloag(){
        dialog = new MaterialDialog.Builder(StoreOrderDetailsActivity.this)

                .customView(R.layout.dialog_edit_freight_change_price, false)
                .title("运费")
                .backgroundColorRes(R.color.white)
                .titleColorRes(R.color.color_3333)
                .positiveText("确定")
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        closeSoftKeyboard(priceEditText);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        closeSoftKeyboard(priceEditText);

                        mPresenter.updateOrder(BaseApp.loginBean.getToken(), orderId, priceEditText.getText().toString(), null, null, null);


                    }
                })
                .build();

        priceEditText = dialog.getCustomView().findViewById(R.id.input);
        listenerMoney(priceEditText);


        //自动弹出软键盘
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(priceEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        dialog.show();

    }

    private void closeSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 监听输入最小金额为0.01
     * 且只能输入两位小数
     */
    private void listenerMoney(EditText etGoodPrice) {
        etGoodPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    return;
                }
                if (s.equals(".") && s.toString().length() == 0) {
                    s = "0.";
                }
                // 判断小数点后只能输入两位
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        etGoodPrice.setText(s);
                        etGoodPrice.setSelection(s.length());
                    }
                }
                //如果第一个数字为0，第二个不为点，就不允许输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etGoodPrice.setText(s.subSequence(0, 1));
                        etGoodPrice.setSelection(1);
                        return;
                    }
                    if (s.toString().length() == 4) {
                        //针对输入0.00的特殊处理
                        if (Double.valueOf(s.toString()) < 0.01) {
                            Toast.makeText(mContext, "最小为0.01", Toast.LENGTH_SHORT).show();
                            etGoodPrice.setText("0.01");
                            etGoodPrice.setSelection(etGoodPrice.getText().toString().trim().length());
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        mPresenter.getOrderDetails(orderId);
    }
}
