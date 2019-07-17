package com.eshop.mvp.ui.activity.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerAfterSaleComponent;
import com.eshop.di.module.AfterSaleModule;
import com.eshop.huanxin.DemoHelper;
import com.eshop.mvp.contract.AfterSaleContract;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.DelOrderId;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.RefundBean;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.presenter.AfterSalePresenter;
import com.eshop.mvp.ui.activity.EaseChatActivity;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.Constant;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.hyphenate.chat.EMConversation;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
   申请退款
 * ================================================
 */
public class RefundStateActivity extends BaseSupportActivity<AfterSalePresenter> implements AfterSaleContract.View {


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


    private String orderId;
    private String id;

    private RefundDetail refundDetail;

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
        return R.layout.activity_refund_state; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
            mPresenter.applyRefundDetails(BaseApp.loginBean.getToken(),orderId);



    }

    private void setData(){
       /** RefundDetail refundDetail = new RefundDetail();
        refundDetail.setApplyTime("2019-09-01");
        refundDetail.setId(123456778);
        refundDetail.setRemainingTime("234");
        refundDetail.setTotalPrice("125.09");
        refundDetail.setRefundReason("测试");*/

       if(refundDetail==null)return;

        num.setText(refundDetail.getId()+"");
        atime.setText(refundDetail.getApplyTime());
        money.setText(refundDetail.getTotalPrice());
        resion.setText(refundDetail.getRefundReason());
        remain_time.setText("剩余期限"+refundDetail.getRemainingTime());

    }

    @OnClick({R.id.cancel,R.id.edit,R.id.phone1,R.id.phone2})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.cancel:
                if(!LoginUtils.isLogin(this)) {
                    return;
                }
                DelOrderId delOrderId = new DelOrderId();
                delOrderId.setOrderId(orderId);
                mPresenter.applyRefundDel(BaseApp.loginBean.getToken(),delOrderId);
                break;

            case R.id.edit:


                Intent intent = new Intent(this, RefundActivity.class);
                intent.putExtra("key_id", refundDetail.getId()+"");
                intent.putExtra("id",orderId);
                startActivity(intent);

               break;

            case R.id.phone1:
               //requestPermissions(0);
                BaseApp.tabindex = 1;
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);

                break;

            case R.id.phone2:
//                requestPermissions(1);
                // start chat acitivity

                DemoHelper.getInstance().saveUser(refundDetail.getStreoName() , refundDetail.getStoreImg() , refundDetail.getHuanxinId());
//                DemoHelper.getInstance().saveUser(refundDetail.get)
                Intent intent2 = new Intent(this, EaseChatActivity.class);
//                if(conversation.isGroup()){
//                    if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
//                        // it's group chat
//                        intent2.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
//                    }else{
//                        intent2.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
//                    }
//
//                }
                // it's single chat


                intent2.putExtra(Constant.EXTRA_USER_ID, refundDetail.getHuanxinId());
                startActivity(intent2);
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
                            callPhone(refundDetail.getPlatformPhone());
                        else
                            callPhone(refundDetail.getStorePhone());
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
        refundDetail = data;
        setData();

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

    }

    @Override
    public void getOrderMsgSuccess(AppOrderForm data) {

    }

    @Override
    public void updateUserImageSuccess(String url) {

    }



}
