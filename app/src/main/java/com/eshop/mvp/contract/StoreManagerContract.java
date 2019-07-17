package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
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
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * 我的店铺
 * ================================================
 */
public interface StoreManagerContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void accountCreatSuccess();
        void banCarAllSuccess(BankCards bankCard);
        void bankIdDelSuccess();
        void cashTypeSuccess(CashType cashType);
        void checkPendingGoodsSuccess(Audit audit);
        void drawingSuccess();
        void goodsSuccess();
        void goodsDelSuccess();
        void goodsPutSuccess();
        void inSalesGoodsSuccess(Audit audit);
        void opinionSuccess();
        void pwdCreatSuccess();
        void recordSuccess(WithDrawRecord withDrawRecord);
        void sellingGoodsSuccess();
        void stateSuccess(StoreState storeState);
        void stateResult(String status,String msg,StoreState storeState);
        void stayOnTheShelfGoodsSuccess(Audit audit);
        void stopSellingGoodsSuccess();
        void storeSuccess();
        void storeColumnResult(StoreCatBean data);
        void storeColumnSuccess();
        void storeColumnCreatSuccess();
        void storeColumnDelSuccess();
        void storeLogoPutSuccess();
        void transactionSuccess(TransList transList);
        void walletSuccess(Wallet wallet);
        void getAuthSuccess(Auth auth);

        void updateUserImageSuccess(String url);

        void getCatBeanList(List<CatBean> data);

        void idStoreSuccess(StoreInfomation storeInfomation);

        void updateUserInfoSuccess(LoginBean msg);

        void getGoodDetailSuccess(ProductDetail good);

        void getMonthMsgSuccess(MonthMsg monthMsg);

        void getMonthMsgStatus(String status,String msg);

        void getIdMyQRCodeSuccess(QRCode qrCode);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<MyBaseResponse<String>> accountCreat(String token, AccountInfo accountInfo);

        Observable<MyBaseResponse<BankCards>> banCarAll(String token, String storeId, String pageNum);
        Observable<MyBaseResponse<String>> bankIdDel(String token, DelAccountInfo delAccountInfo);
        Observable<MyBaseResponse<CashType>> cashType(String token, String storeId,
                                                      int pageNum,String type);
        Observable<MyBaseResponse<Audit>> checkPendingGoods(String token, int pageNum);
        Observable<MyBaseResponse<String>> drawing(String token, DrawBean drawBean);
        Observable<MyBaseResponse<String>> goods(String token, PublishGoods publishGoods);

        Observable<MyBaseResponse<String>> goodsDel(String token, GoodsId goodsId);

        Observable<MyBaseResponse<String>> goodsPut(String token, PublishGoods publishGoods);

        Observable<MyBaseResponse<Audit>> inSalesGoods(String token, int pageNum);

        Observable<MyBaseResponse<String>> opinion(String token, OpBack backContent);

        Observable<MyBaseResponse<String>> pwdCreat(String token, PhonePassword phonePassword);

        Observable<MyBaseResponse<WithDrawRecord>> record(String token, String storeId, int pageNum, int type);

        Observable<MyBaseResponse<QRCode>> getIdMyQRCode(String token, String storeId, String type);


        Observable<MyBaseResponse<String>> sellingGoods(String token, GoodsId goodsId);

        Observable<MyBaseResponse<StoreState>> state(String token);

        Observable<MyBaseResponse<Audit>> stayOnTheShelfGoods(String token, int pageNum);

        Observable<MyBaseResponse<String>> stopSellingGoods(String token, GoodsId goodsId);

        Observable<MyBaseResponse<String>> store(AuthInfo authInfo);

        Observable<MyBaseResponse<StoreCatBean>> storeColumnList(int pageNum, String storeId);

        Observable<MyBaseResponse<StoreCatBean>> storeColumnAll(int pageNum, String storeId);


        Observable<MyBaseResponse<String>> storeColumn(String token, StoreCategory storeCategory);

        Observable<MyBaseResponse<String>> storeColumnCreat(String token,StoreCategoryEdit storeCategoryEdit);

        Observable<MyBaseResponse<String>> storeColumnDel(String token,CategoryId categoryId);

        Observable<MyBaseResponse<String>> storeLogoPut(String token, StoreLogo storeLogo);

        Observable<MyBaseResponse<TransList>> transaction(String token, String storeId,
                                                          int pageNum,int type);

        Observable<MyBaseResponse<Wallet>> wallet(String token, String storeId);


        Observable<MyBaseResponse<Auth>> getAuth(String userId);

        Observable<MyBaseResponse<StoreInfomation>> idStore(String token, String storeId);

        Observable<MyBaseResponse<LoginBean>> updateUserInfo(
                String id,
                String phone,
                String password,
                String logo,
                String nickName,
                int sex,
                String deviceId,
                String openId);

        Observable<MyBaseResponse<MonthMsg>> getMonthMsg(String token);
    }
}
