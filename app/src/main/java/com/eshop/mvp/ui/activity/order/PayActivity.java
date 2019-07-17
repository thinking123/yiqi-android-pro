package com.eshop.mvp.ui.activity.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alipay.sdk.app.AuthTask;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.AppData;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.presenter.OrderPresenter;
import com.eshop.mvp.ui.fragment.OrderFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 订单支付
 * ================================================
 */
public class PayActivity extends BaseSupportActivity<OrderPresenter> implements OrderContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.name)
    TextView money_lable;

    @BindView(R.id.imageView5)
    ImageView month_icon;

    @BindView(R.id.month_lable)
    TextView month_lable;

    @BindView(R.id.remain_time)
    TextView retime;

    //weixin_radio
    @BindView(R.id.weixin_radio)
    RadioButton weixin_radio;

    @BindView(R.id.zhifubao_radio)
    RadioButton zhifubao_radio;

    @BindView(R.id.month_radio)
    RadioButton month_radio;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI wxApi;

    private String orderId;
    private double money;
    private String remain_time;
    public int appClassId = 0;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

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
        return R.layout.activity_pay; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("订单支付");
        toolbarBack.setVisibility(View.VISIBLE);

        orderId = getIntent().getStringExtra("id");
        money = getIntent().getDoubleExtra("money", 0);
        money_lable.setText("¥" + money);
        remain_time = getIntent().getStringExtra("time");
        appClassId = getIntent().getIntExtra("appClassId", 0);
        if (remain_time != null) retime.setText(remain_time);
        setBtn();

        if (appClassId != 33) {
            month_icon.setVisibility(View.GONE);
            month_lable.setVisibility(View.GONE);
            month_radio.setVisibility(View.GONE);
        }

        wxApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), AppData.WECHAT_APPID, true);
        wxApi.registerApp(AppData.WECHAT_APPID);


    }

    private void setBtn() {
        weixin_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    zhifubao_radio.setChecked(false);
                    month_radio.setChecked(false);
                }
            }
        });

        zhifubao_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin_radio.setChecked(false);
                    month_radio.setChecked(false);
                }
            }
        });

        month_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin_radio.setChecked(false);
                    zhifubao_radio.setChecked(false);
                }
            }
        });
    }

    @OnClick({R.id.btn_pay, R.id.wx_lay, R.id.zhifubao_lay, R.id.month_lay})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.btn_pay:

                if (weixin_radio.isChecked()) {
                    mPresenter.wxpay(BaseApp.loginBean.getId() + "", orderId);

                } else if (month_radio.isChecked()) {
                    mPresenter.monthPay(BaseApp.loginBean.getId() + "", orderId);
                } else {
                   // showMessage("待实现");
                    mPresenter.alipayPay(BaseApp.loginBean.getId() + "", orderId);
                }

                break;

            case R.id.wx_lay:

                weixin_radio.setChecked(true);

                break;

            case R.id.zhifubao_lay:

                zhifubao_radio.setChecked(true);

                break;

            case R.id.month_lay:

                month_radio.setChecked(true);

                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showMessage("支付成功");
                        BaseApp.isOrderListNeedRefresh = true;

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                       // showMessage("支付成功"  + payResult);
                    }
                    break;
                }

                default:
                    break;
            }
        };
    };


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
        String authInfo = data;

        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(PayActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();

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

    }

    @Override
    public void logisticsAllSuccess(List<ExpressCode> list) {

    }

    @Override
    public void monthPaySuccess(PayRet data) {

    }

    @Override
    public void monthPayStatus(String status, String msg) {
        String info = msg;
        if (status.equalsIgnoreCase("7001")) {
           info = msg+",请前往认证中心认证月结资质认证.";
           }

        new MaterialDialog.Builder(PayActivity.this)
                .content(info)
                .backgroundColorRes(R.color.white)
                .contentColorRes(R.color.color_3333)
                .positiveText("确定")
                .show();
    }

    @Override
    public void paymentSuccess(String data) {
        finish();
    }

    @Override
    public void reminderShipmentSuccess(String data) {

    }

    @Override
    public void wxpaySuccess(PayRet data) {
        PayReq req = new PayReq();

        req.appId = data.getAppid();
        req.partnerId = data.getPartnerid();
        req.prepayId = data.getPrepayid();
        req.nonceStr = data.getNoncestr();
        req.timeStamp = data.getTimestamp();
        req.packageValue = data.getPackageX();
        req.sign = data.getSign();
        req.extData = "app data"; // optional

        wxApi.sendReq(req);

        finish();
    }

    @Override
    public void getAddressList(List<AddressBean> list) {

    }

    @Override
    public void updateOrderSuccess() {

    }
}
