package com.eshop.mvp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.eshop.mvp.http.entity.order.AfterSales;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.presenter.AfterSalePresenter;
import com.eshop.mvp.ui.adapter.ImageListAdapter;
import com.eshop.mvp.ui.adapter.ImageListRefundAdapter;
import com.eshop.mvp.ui.fragment.AfterSaleFragment;
import com.eshop.mvp.ui.widget.GoodsItemDecoration;
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
 * 售后处理
 * ================================================
 */
public class DoAfterSaleActivity extends BaseSupportActivity<AfterSalePresenter> implements AfterSaleContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.orderid)
    TextView orderid;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.content)
    TextView resion;
    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.accept)
    RadioButton accept;
    @BindView(R.id.reject)
    RadioButton reject;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.lin10)
    View lin10;
    @BindView(R.id.lin4)
    View lin4;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView1;

    ImageListRefundAdapter imageListAdapter1;

    private String storeId;
    private String id;

    private AfterSaleStore afterSaleStore;

    private List<String> list1 = new ArrayList<>();

    private int isLogistics = 0;

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
        return R.layout.activity_do_after_sale; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("处理售后");
        toolbarBack.setVisibility(View.VISIBLE);

        if (!LoginUtils.isLogin(this)) {
            return;
        }
        initRecyclerView();
        storeId = getIntent().getStringExtra("storeid");
        id = getIntent().getStringExtra("id");
        isLogistics = getIntent().getIntExtra("isLogistics",0);
        mPresenter.handlingAfterSales(BaseApp.loginBean.getToken(), id, storeId);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(R.id.accept==group.getCheckedRadioButtonId()){
                    lin4.setVisibility(View.GONE);
                }else{
                    lin4.setVisibility(View.VISIBLE);
                }

                if(R.id.accept==group.getCheckedRadioButtonId() && isLogistics==1){
                    lin10.setVisibility(View.VISIBLE);
                }else{
                    lin10.setVisibility(View.GONE);
                }
            }
        });

    }


    private void initRecyclerView() {
        list1 = new ArrayList<>();
        imageListAdapter1 = new ImageListRefundAdapter(list1);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView1.setLayoutManager(manager);
        mRecyclerView1.addItemDecoration(new GoodsItemDecoration(20));

        mRecyclerView1.setAdapter(imageListAdapter1);


    }

    private boolean veryfy(){

        if(name.getText().toString().isEmpty())return false;
        if(phone.getText().toString().isEmpty())return false;
        if(address.getText().toString().isEmpty())return false;
        return true;
    }

    @OnClick({R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:

                AfterSales afterSales = new AfterSales();
                afterSales.setId(id);
                afterSales.setStoreId(storeId);
                afterSales.setAuditMessage(remark.getText().toString());
                if (accept.isChecked() && isLogistics==1) {
                    afterSales.setState("2");
                    if(veryfy()){
                        afterSales.setNsigneeAdress(address.getText().toString());
                        afterSales.setNsigneeName(name.getText().toString());
                        afterSales.setNsigneePhone(phone.getText().toString());
                    }else{
                        showMessage("请输入收货人信息");
                        return;
                    }



                } else {
                    afterSales.setState("3");
                    afterSales.setNsigneeAdress("");
                    afterSales.setNsigneeName("");
                    afterSales.setNsigneePhone("");
                }

                mPresenter.handlingAfterSales(BaseApp.loginBean.getToken(),afterSales);

                break;
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
        afterSaleStore = data;

        orderid.setText(data.getOrderId());
        money.setText(data.getTotalPrice());
        resion.setText(data.getRefundReason());
        time.setText(data.getApplyTime());

        imageListAdapter1.setNewData(data.getVoucher());

    }

    @Override
    public void handlingAfterSalesSuccess(String data) {
        showMessage("售后处理信息提交成功");
        //finish();
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
