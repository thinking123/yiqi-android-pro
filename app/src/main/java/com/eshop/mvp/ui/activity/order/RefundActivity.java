package com.eshop.mvp.ui.activity.order;

import android.content.Intent;
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
import com.eshop.di.component.DaggerOrderComponent;
import com.eshop.di.module.AfterSaleModule;
import com.eshop.di.module.LoginModule;
import com.eshop.mvp.contract.AfterSaleContract;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.order.AfterSaleOrder;
import com.eshop.mvp.http.entity.order.AfterSaleStore;
import com.eshop.mvp.http.entity.order.AppLog;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.DelOrderId;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.order.RefundBean;
import com.eshop.mvp.http.entity.order.RefundDetail;
import com.eshop.mvp.http.entity.order.RefundDetail2;
import com.eshop.mvp.http.entity.order.RefundDetailUser;
import com.eshop.mvp.http.entity.order.RefundInfo;
import com.eshop.mvp.http.entity.order.RefundUpdateBean;
import com.eshop.mvp.presenter.AfterSalePresenter;
import com.eshop.mvp.presenter.OrderPresenter;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Avatar;
import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
   申请退款
 * ================================================
 */
public class RefundActivity extends BaseSupportActivity<AfterSalePresenter> implements AfterSaleContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.name)
    TextView money;
    @BindView(R.id.editText)
    TextView resion;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;

    @BindView(R.id.del1)
    ImageView del1;
    @BindView(R.id.del2)
    ImageView del2;
    @BindView(R.id.del3)
    ImageView del3;

    private int index=0;
    private String orderId;
    private String key_id;

    private PicChooserHelper picChooserHelper;

    private List<String> url_list = new ArrayList<>();

    boolean isreopen = false;

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
        return R.layout.activity_refund; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("退款申请");
        toolbarBack.setVisibility(View.VISIBLE);

        if(!LoginUtils.isLogin(this)) {
            return;
        }

        orderId = getIntent().getStringExtra("id");
        isreopen = getIntent().getBooleanExtra("isreopen",false);
        if(orderId!=null)
            mPresenter.applyRefund(BaseApp.loginBean.getToken(),orderId);
        key_id = getIntent().getStringExtra("key_id");

        if(key_id!=null){
            mPresenter.applyRefundById(BaseApp.loginBean.getToken(),key_id);
        }


        if(isreopen) {
            DelOrderId delOrderId = new DelOrderId();
            delOrderId.setOrderId(orderId);
            mPresenter.applyRefundDel(BaseApp.loginBean.getToken(), delOrderId);
        }

        //test
       // Intent intent = new Intent(this,RefundStateActivity.class);
       // intent.putExtra("id",orderId);
       // startActivity(intent);

    }

    private void updateimg(){
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        del1.setVisibility(View.GONE);
        del2.setVisibility(View.GONE);
        del3.setVisibility(View.GONE);
        int k = 0;
        for(String url : url_list){
            if(k==0){
                img1.setVisibility(View.VISIBLE);
                del1.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(6)))
                        .into(img1);
                }
            if(k==1){
                img2.setVisibility(View.VISIBLE);
                del2.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(6)))
                        .into(img2);
            }
            if(k==2){
                img3.setVisibility(View.VISIBLE);
                del3.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(6)))
                        .into(img3);
            }
            k++;
            if(k>2)break;
        }
    }

    @OnClick({R.id.camera,R.id.btn_pay,R.id.del1,R.id.del2,R.id.del3})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.camera:
                if(index==3){showMessage("最多3张");}
                else {
                    uploadImage();
                }
                break;

            case R.id.del1:
                url_list.remove(0);
                updateimg();
                break;

            case R.id.del2:
                url_list.remove(1);
                updateimg();
                break;


            case R.id.del3:
                url_list.remove(2);
                updateimg();
                break;

            case R.id.btn_pay:
                StringBuilder sb = new StringBuilder();
                for(String url : url_list){
                    sb.append(url);
                    sb.append(",");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));

                if(key_id==null) {


                    RefundBean refundBean = new RefundBean();
                    refundBean.setOrderId(orderId);
                    refundBean.setRefundReason(resion.getText().toString());
                    refundBean.setVoucher(sb.toString());

                    if (!LoginUtils.isLogin(this)) {
                        return;
                    }

                    if (veryFy())
                        mPresenter.applyRefund(BaseApp.loginBean.getToken(), refundBean);
                }else{
                    RefundUpdateBean refundUpdateBean = new RefundUpdateBean();
                    refundUpdateBean.setOrderId(orderId);
                    refundUpdateBean.setRefundReason(resion.getText().toString());
                    refundUpdateBean.setVoucher(sb.toString());
                    refundUpdateBean.setId(key_id);

                    if (!LoginUtils.isLogin(this)) {
                        return;
                    }

                    if (veryFy()){
                        mPresenter.applyRefundPut(BaseApp.loginBean.getToken(),refundUpdateBean);
                        return;
                    }
                }

                break;

        }
    }

    private boolean veryFy(){
        if(resion.getText().toString().isEmpty()){
            showMessage("请输入问题");
            return false;
        }

        if(url_list.size() == 0){
            showMessage("请上传退款凭证");
            return false;
        }

        return true;
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

    }

    @Override
    public void applyRefundPutSuccess() {
        showMessage("修改成功");
    }

    @Override
    public void applyRefundByIdSuccess(RefundInfo data) {
            if(data!=null){
                money.setText(data.getTotalPrice());
                resion.setText(data.getRefundReason());
                int k = 0;
                for(String url : data.getVoucher()){
                    index = k;
                    updateUserImageSuccess(url);
                    k++;
                    if(k>2)break;
                }
            }
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
        if(data!=null) {
            money.setText("¥" + data.totalPrice + "元");
        }
    }

    @Override
    public void updateUserImageSuccess(String url) {
        if(index==0){
            url_list.clear();
            url_list.add(url);
            index++;
            img1.setVisibility(View.VISIBLE);
            del1.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(url)//new MultiTransformation(
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(6)))
                    .into(img1);

        }else if(index==1){

            url_list.add(url);
            index++;
            img2.setVisibility(View.VISIBLE);
            del2.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(url)//new MultiTransformation(
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(6)))
                    .into(img2);

        }else if(index==2){

            url_list.add(url);
            index++;
            img3.setVisibility(View.VISIBLE);
            del3.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(url)//new MultiTransformation(
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(6)))
                    .into(img3);

        }else{
            showMessage("最多3张");
        }
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Cover);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(RefundActivity.this)
                .title("上传图片")
                .titleColorRes(R.color.color_3333)
                .backgroundColorRes(R.color.white)
                .positiveColorRes(R.color.color_3333)
                .itemsColorRes(R.color.color_3333)
                .negativeText(R.string.cancel_easy_photos)
                .inputType(InputType.TYPE_CLASS_TEXT )
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                picChooserHelper.takePicFromCamera();
                                break;
                            case 1:
                                picChooserHelper.takePicFromAlbum();
                                break;
                        }}
                }).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, resultCode, data);
    }

}
