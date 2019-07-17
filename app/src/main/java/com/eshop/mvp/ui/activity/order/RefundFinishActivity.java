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
   finish退款
 * ================================================
 */
public class RefundFinishActivity extends BaseSupportActivity<AfterSalePresenter> implements AfterSaleContract.View {


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

    @BindView(R.id.all_money)
    TextView all_money;

    @BindView(R.id.path)
    TextView path;

    @BindView(R.id.info)
    TextView info;

    private String orderId;
    private String id;

    private RefundDetail2 refundDetail2;

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
        return R.layout.activity_refund_finish; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
            mPresenter.refund(BaseApp.loginBean.getToken(),orderId);



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

       if(refundDetail2==null)return;

        num.setText(refundDetail2.getId()+"");
        atime.setText(refundDetail2.getApplyTime());
        money.setText("¥"+refundDetail2.getTotalPrice());
        resion.setText(refundDetail2.getRefundReason());
        remain_time.setText("剩余时间"+refundDetail2.getRemainingTime());

        all_money.setText("¥"+refundDetail2.getTotalPrice());
        path.setText(refundDetail2.getPayType());

        info.setText(refundDetail2.getArriveTime());

    }

    @OnClick({R.id.phone1,R.id.phone2})
    public void onViewClicked(View view) {

        switch (view.getId()) {


            case R.id.phone1:
               // requestPermissions(0);
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
                            callPhone(refundDetail2.getPlatformPhone());
                        else
                            callPhone(refundDetail2.getStorePhone());
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
        refundDetail2 = data;
        setData();
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
