package com.eshop.mvp.ui.activity.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerAfterSaleComponent;
import com.eshop.di.module.AfterSaleModule;
import com.eshop.mvp.contract.AfterSaleContract;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.DelOrderId;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.presenter.AfterSalePresenter;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
   申请退款
 * ================================================
 */
public class RefundSucessActivity extends BaseSupportActivity<AfterSalePresenter> implements AfterSaleContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.time)
    TextView atime;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.resion)
    TextView resion;
    @BindView(R.id.remain_time)
    TextView remain_time;

    @BindView(R.id.receiver_name)
    TextView receiver_name;

    @BindView(R.id.receiver_phone)
    TextView receiver_phone;

    @BindView(R.id.receiver_details)
    TextView receiver_details;

    @BindView(R.id.btn_tuihuo)
    Button btn_tuihuo;

    private String orderId;
    private String id;

    private RefundDetailUser refundDetailUser;

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
        return R.layout.activity_refund_sucess; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("退款申请");
        toolbarBack.setVisibility(View.VISIBLE);
        orderId = getIntent().getStringExtra("orderId");
        id = getIntent().getStringExtra("id");

        if(!LoginUtils.isLogin(this)) {
            return;
        }

        if(orderId!=null)
            mPresenter.applyRefundDetailsUser(BaseApp.loginBean.getToken(),orderId);

        setData();

    }

    private void setData(){
       /** refundDetailUser = new RefundDetailUser();
        refundDetailUser.setApplyTime("2019-09-01");
        refundDetailUser.setId(123456778);
        refundDetailUser.setRemainingTime("234");
        refundDetailUser.setTotalPrice("125.09");
        refundDetailUser.setRefundReason("测试");

        refundDetailUser.setNsigneeName("hahahhkjhkhkhkhkj");
        refundDetailUser.setNsigneeAdress("sdfsdfsfskjhkjhkjhkjhkjhkjhkjhkjhkjhkjhkhk");
        refundDetailUser.setNsigneePhone("13333333");*/

       if(refundDetailUser==null)return;

        num.setText(refundDetailUser.getId()+"");
        atime.setText(refundDetailUser.getApplyTime());
        money.setText(refundDetailUser.getTotalPrice());
        resion.setText(refundDetailUser.getRefundReason());
        remain_time.setText("剩余期限"+refundDetailUser.getRemainingTime());

        receiver_name.setText(refundDetailUser.getNsigneeName());
        receiver_phone.setText(refundDetailUser.getNsigneePhone());
        receiver_details.setText(refundDetailUser.getNsigneeAdress());

    }

    @OnClick({R.id.btn_tuihuo,R.id.phone1,R.id.phone2})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.btn_tuihuo:
                Intent intent = new Intent(this, DeliverRefundActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);

                break;

            case R.id.phone1:
                //requestPermissions(0);
                BaseApp.tabindex = 1;
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);

                break;

            case R.id.phone2:
                requestPermissions(1);
                break;

        }
    }

    @SuppressLint("CheckResult")
    private void requestPermissions(int index) {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(
                        Manifest.permission.CALL_PHONE
                )
                .subscribe(permission -> {
                    if (permission.granted) {
                        if(index==0)
                            callPhone(refundDetailUser.getPlatformPhone());
                        else
                            callPhone(refundDetailUser.getStorePhone());
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
        money.setText("¥"+data+"元");
    }

    @Override
    public void applySuccess() {
        showMessage("提交成功");
    }

    @Override
    public void applyRefundDetailsSuccess(RefundDetail data) {


    }

    @Override
    public void applyRefundDelSuccess() {
        showMessage("撤销成功");
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

    }

    @Override
    public void logisticsAllSuccess(List<ExpressCode> list) {

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
        refundDetailUser = data;

        setData();
    }

    @Override
    public void getOrderMsgSuccess(AppOrderForm data) {

    }

    @Override
    public void updateUserImageSuccess(String url) {

    }



}
