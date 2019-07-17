package com.eshop.mvp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerAfterSaleComponent;
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.di.module.AfterSaleModule;
import com.eshop.mvp.contract.AfterSaleContract;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressInfo;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.presenter.AfterSalePresenter;
import com.eshop.mvp.presenter.OrderPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.SimpleSpinnerTextFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 发货
 * ================================================
 */
public class DeliverRefundActivity extends BaseSupportActivity<AfterSalePresenter> implements AfterSaleContract.View {


    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.code)
    TextView code;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.nice_spinner_express)
    NiceSpinner nice_spinner_express;

    private String orderId;

    private ExpressCode expressCode;

    private List<ExpressCode> expressCodeList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAfterSaleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .afterSaleModule(new AfterSaleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_deliver_refund; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("填写物流信息");
        toolbarBack.setVisibility(View.VISIBLE);

        orderId = getIntent().getStringExtra("id");

        mPresenter.logisticsAll(BaseApp.loginBean.getToken());

        nice_spinner_express.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                expressCode = expressCodeList.get(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                expressCode = expressCodeList.get(0);

            }
        });

    }


    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        if(code.getText().toString().isEmpty()){
            showMessage("请输入物流单号");
        }else  if(code.getText().toString().isEmpty()){
            showMessage("请输入手机号");
        }else{

            try {
                if (expressCode == null) {
                    expressCode = expressCodeList.get(0);
                }

                ExpressInfo expressInfo = new ExpressInfo();
                expressInfo.setExpressCompany(expressCode.getName());
                expressInfo.setId(orderId);
                expressInfo.setLinkPhone(phone.getText().toString());
                expressInfo.setExpressNumber(code.getText().toString());
                mPresenter.logistics(BaseApp.loginBean.getToken(), expressInfo);
            }catch (Exception e){
                e.printStackTrace();
            }
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
    public void applyRefundSuccess(String data) {

    }

    @Override
    public void applySuccess() {

    }

    @Override
    public void applyRefundDetailsSuccess(RefundDetail data) {

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
        showMessage("提交成功");
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
    public void confirmTheRefundSuccess() {

    }

    @Override
    public void confirmTheRefundUserSuccess() {

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
