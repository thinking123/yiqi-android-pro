package com.eshop.mvp.model;

import android.app.Application;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.api.service.AuthService;
import com.eshop.mvp.http.api.service.LoginService;
import com.eshop.mvp.http.api.service.OrderService;
import com.eshop.mvp.http.api.service.ProductService;
import com.eshop.mvp.http.api.service.StoreManagerService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreLogo;
import com.eshop.mvp.http.entity.store.AccountInfo;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.CategoryId;
import com.eshop.mvp.http.entity.store.DelAccountInfo;
import com.eshop.mvp.http.entity.store.DrawBean;
import com.eshop.mvp.http.entity.store.GoodsId;
import com.eshop.mvp.http.entity.store.OpBack;
import com.eshop.mvp.http.entity.store.PhonePassword;
import com.eshop.mvp.http.entity.store.PublishGoods;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreCategory;
import com.eshop.mvp.http.entity.store.StoreCategoryEdit;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eshop.mvp.contract.StoreManagerContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/08/2019 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StoreManagerModel extends BaseModel implements StoreManagerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StoreManagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MyBaseResponse<String>> accountCreat(String token, AccountInfo accountInfo) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .accountCreat(token, accountInfo);
    }

    @Override
    public Observable<MyBaseResponse<BankCards>> banCarAll(String token, String storeId, String pageNum) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .banCarAll(token, storeId, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<String>> bankIdDel(String token, DelAccountInfo delAccountInfo) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .bankIdDel(token, delAccountInfo);
    }

    @Override
    public Observable<MyBaseResponse<CashType>> cashType(String token, String storeId, int pageNum, String type) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .cashType(token, storeId, pageNum, type);
    }

    @Override
    public Observable<MyBaseResponse<Audit>> checkPendingGoods(String token, int pageNum) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .checkPendingGoods(token, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<String>> drawing(String token, DrawBean drawBean) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .drawing(token, drawBean);
    }

    @Override
    public Observable<MyBaseResponse<String>> goods(String token, PublishGoods publishGoods) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .goods(token, publishGoods);
    }

    @Override
    public Observable<MyBaseResponse<String>> goodsDel(String token, GoodsId goodsId) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .goodsDel(token, goodsId);
    }

    @Override
    public Observable<MyBaseResponse<String>> goodsPut(String token, PublishGoods publishGoods) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .goodsPut(token, publishGoods);
    }

    @Override
    public Observable<MyBaseResponse<Audit>> inSalesGoods(String token, int pageNum) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .inSalesGoods(token, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<String>> opinion(String token, OpBack backContent) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .opinion(token, backContent);
    }

    @Override
    public Observable<MyBaseResponse<String>> pwdCreat(String token, PhonePassword phonePassword) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .pwdCreat(token, phonePassword);
    }

    @Override
    public Observable<MyBaseResponse<WithDrawRecord>> record(String token, String storeId, int pageNum, int type) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .record(token, storeId, pageNum, type);
    }

    @Override
    public Observable<MyBaseResponse<QRCode>> getIdMyQRCode(String token, String storeId, String type) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .getIdMyQRCode(token, storeId, type);
    }

    @Override
    public Observable<MyBaseResponse<String>> sellingGoods(String token, GoodsId goodsId) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .sellingGoods(token, goodsId);
    }

    @Override
    public Observable<MyBaseResponse<StoreState>> state(String token) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .state(token);
    }

    @Override
    public Observable<MyBaseResponse<Audit>> stayOnTheShelfGoods(String token, int pageNum) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .stayOnTheShelfGoods(token, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<String>> stopSellingGoods(String token, GoodsId goodsId) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .stopSellingGoods(token, goodsId);
    }

    @Override
    public Observable<MyBaseResponse<String>> store(AuthInfo authInfo) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .store(BaseApp.loginBean.getToken(),authInfo);
    }

    @Override
    public Observable<MyBaseResponse<StoreCatBean>> storeColumnList(int pageNum, String storeId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .storeColumn(storeId, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<StoreCatBean>> storeColumnAll(int pageNum, String storeId) {
        return mRepositoryManager.obtainRetrofitService(ProductService.class)
                .storeColumnAll(storeId, pageNum);
    }

    @Override
    public Observable<MyBaseResponse<String>> storeColumn(String token, StoreCategory storeCategory) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .storeColumn(token, storeCategory);
    }

    @Override
    public Observable<MyBaseResponse<String>> storeColumnCreat(String token, StoreCategoryEdit storeCategoryEdit) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .storeColumnCreat(token,  storeCategoryEdit);
    }

    @Override
    public Observable<MyBaseResponse<String>> storeColumnDel(String token, CategoryId categoryId) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .storeColumnDel(token, categoryId);
    }

    @Override
    public Observable<MyBaseResponse<String>> storeLogoPut(String token, StoreLogo storeLogo) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .storeLogoPut(token, storeLogo);
    }

    @Override
    public Observable<MyBaseResponse<TransList>> transaction(String token, String storeId, int pageNum, int type) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .transaction(token, storeId, pageNum, type);
    }

    @Override
    public Observable<MyBaseResponse<Wallet>> wallet(String token, String storeId) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .wallet(token, storeId);
    }

    @Override
    public Observable<MyBaseResponse<Auth>> getAuth(String userId) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .getAuth(BaseApp.loginBean.getToken(),userId);
    }

    @Override
    public Observable<MyBaseResponse<StoreInfomation>> idStore(String token, String storeId) {
        return mRepositoryManager.obtainRetrofitService(StoreManagerService.class)
                .idStore(token, storeId);
    }

    @Override
    public Observable<MyBaseResponse<LoginBean>> updateUserInfo(String id, String phone, String password, String logo, String nickName, int sex, String deviceId, String openId) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class)
                .updateUserInfo(id,phone, password,logo,nickName,sex,deviceId,openId);
    }

    @Override
    public Observable<MyBaseResponse<MonthMsg>> getMonthMsg(String token) {
        return mRepositoryManager
                .obtainRetrofitService(AuthService.class)
                .getMonthMsg(token);
    }
}