package com.eshop.mvp.ui.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.AppData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 认证中心
 * ================================================
 */
public class AuthActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.store)
    TextView store;
    @BindView(R.id.weixin)
    TextView weixin;

    private Auth auth;

    public static IWXAPI wxApi;

    private static AuthActivity sAuthActivity;

    public static AuthActivity getInstance() {
        return sAuthActivity;
    }

    private String status="";

    private String msg="";


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .storeManagerModule(new StoreManagerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_auth; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        sAuthActivity = this;
        toolbarTitle.setText("认证中心");
        toolbarBack.setVisibility(View.VISIBLE);
        if(!LoginUtils.isLogin(this)) {
            return;
        }
        if(mPresenter!=null){
            mPresenter.getAuth(BaseApp.loginBean.getId()+"");
            mPresenter.getMonthMsg(BaseApp.loginBean.getToken());

            if(BaseApp.storeState==null)mPresenter.state(BaseApp.loginBean.getToken());
        }

        wxApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), AppData.WECHAT_APPID, true);
        wxApi.registerApp(AppData.WECHAT_APPID);

    }

    @OnClick({R.id.phone_lay, R.id.month_lay, R.id.weixin_lay, R.id.store_lay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_lay:

                break;
            case R.id.month_lay:
                if(auth.companyAuth.equalsIgnoreCase("1")){
                    showMessage("已认证");
                }else{

                    if(status.equalsIgnoreCase("7002") || status.equalsIgnoreCase("7005")){
                        return;
                    }

                    Intent intent = new Intent(this,MonthAuthActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.weixin_lay:
                if(auth.wxAuth.equalsIgnoreCase("1")){
                    showMessage("微信已认证");
                }else{
                    sendAuthReq();
                }
                break;
            case R.id.store_lay:

                if(auth.storeAuth.equalsIgnoreCase("1")){
                    showMessage("已认证");
                }else{
                    if(BaseApp.storeStatus!=null && BaseApp.storeStatus.equalsIgnoreCase("6002")){
                        //审核中
                    }else {
                        Intent intent = new Intent(this, RealNameAuthActivity.class);
                        startActivity(intent);
                    }
                }


                break;
        }
    }

    private void sendAuthReq() {
        BaseApp.wxAuth = true;
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        wxApi.sendReq(req);
    }

    public void wxAuth(String unionid){
        if (mPresenter != null) {
            String id = BaseApp.loginBean.getId()+"";
            String phones = BaseApp.loginBean.getPhone();
            String passwords = BaseApp.loginBean.getPassWord();
            int sex = BaseApp.loginBean.getSex();
            mPresenter.updateUserInfo(id,phones,passwords,null,null,sex,BaseApp.deviceId,unionid);

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
    public void accountCreatSuccess() {

    }

    @Override
    public void banCarAllSuccess(BankCards bankCard) {

    }

    @Override
    public void bankIdDelSuccess() {

    }

    @Override
    public void cashTypeSuccess(CashType cashType) {

    }

    @Override
    public void checkPendingGoodsSuccess(Audit audit) {

    }

    @Override
    public void drawingSuccess() {

    }

    @Override
    public void goodsSuccess() {

    }

    @Override
    public void goodsDelSuccess() {

    }

    @Override
    public void goodsPutSuccess() {

    }

    @Override
    public void inSalesGoodsSuccess(Audit audit) {

    }

    @Override
    public void opinionSuccess() {

    }

    @Override
    public void pwdCreatSuccess() {

    }

    @Override
    public void recordSuccess(WithDrawRecord withDrawRecord) {

    }

    @Override
    public void sellingGoodsSuccess() {

    }

    @Override
    public void stateSuccess(StoreState storeState) {

    }

    @Override
    public void stateResult(String status, String msg, StoreState storeState) {
        BaseApp.storeState = storeState;
        BaseApp.storeStatus = status;
        BaseApp.storeMsg = msg;
    }


    @Override
    public void stayOnTheShelfGoodsSuccess(Audit audit) {

    }

    @Override
    public void stopSellingGoodsSuccess() {

    }

    @Override
    public void storeSuccess() {

    }

    @Override
    public void storeColumnResult(StoreCatBean data) {

    }

    @Override
    public void storeColumnSuccess() {

    }

    @Override
    public void storeColumnCreatSuccess() {

    }

    @Override
    public void storeColumnDelSuccess() {

    }

    @Override
    public void storeLogoPutSuccess() {

    }

    @Override
    public void transactionSuccess(TransList transList) {

    }

    @Override
    public void walletSuccess(Wallet wallet) {

    }

    @Override
    public void getAuthSuccess(Auth auth) {
        this.auth = auth;
        if(auth.phoneAuth.equalsIgnoreCase("1")){
            phone.setText("已认证");
        }else{
            phone.setText("未认证");

        }

        if(auth.wxAuth.equalsIgnoreCase("1")){
            weixin.setText("已认证");
        }else{
            weixin.setText("未认证");
        }

        if(auth.storeAuth.equalsIgnoreCase("1")){
            store.setText("已认证");
        }else{
            store.setText("未认证");
            if(BaseApp.storeStatus!=null && BaseApp.storeStatus.equalsIgnoreCase("6002")){
                store.setText("审核中");
            }
        }

        if(auth.companyAuth.equalsIgnoreCase("1")){
            month.setText("已认证");
            month.setTextColor(getResources().getColor(R.color.gray_97));
        }else{

            if(status.equalsIgnoreCase("7002") || status.equalsIgnoreCase("7005")){

            }else {
                month.setText("未认证");
                month.setTextColor(getResources().getColor(R.color.gray_97));
            }
        }
    }

    @Override
    public void updateUserImageSuccess(String url) {

    }

    @Override
    public void getCatBeanList(List<CatBean> data) {

    }

    @Override
    public void idStoreSuccess(StoreInfomation storeInfomation) {

    }

    @Override
    public void updateUserInfoSuccess(LoginBean msg) {
        showMessage("微信认证成功");
    }

    @Override
    public void getGoodDetailSuccess(ProductDetail good) {

    }

    @Override
    public void getMonthMsgSuccess(MonthMsg monthMsg) {

    }

    @Override
    public void getMonthMsgStatus(String status, String msg) {
        this.status = status;
        this.msg = msg;

        if(status.equalsIgnoreCase("7002") ){
            month.setText("月结审核中");
            month.setTextColor(getResources().getColor(R.color.red));
        }else  if(status.equalsIgnoreCase("7005") ){
            month.setText("月结被封禁");
            month.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    public void getIdMyQRCodeSuccess(QRCode qrCode) {

    }
}
