package com.eshop.mvp.presenter;

import android.app.Application;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.order.AppOrder;
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
import com.eshop.mvp.model.HomeModel;
import com.eshop.mvp.model.LoginModel;
import com.eshop.mvp.model.ProductDetailsModel;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.eshop.mvp.contract.StoreManagerContract;

import java.io.File;
import java.util.List;


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
public class StoreManagerPresenter extends BasePresenter<StoreManagerContract.Model, StoreManagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    UserModel userModel;

    @Inject
    HomeModel homeModel;

    @Inject
    LoginModel loginModel;

    @Inject
    ProductDetailsModel productDetailsModel;

    @Inject
    public StoreManagerPresenter(StoreManagerContract.Model model, StoreManagerContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 作用: 账户设置 添加银行卡 /支付宝/微信 接口号; - Y19
     *
     * @param token
     * @param accountType   账户类型 0 支付宝 1 微信 2 银行转账
     * @param accountNumber 提现账号（微信、支付宝是图片，银行卡输入卡号）
     * @param storeId       店铺id
     * @param cardholder    持卡人(银行转账必填)
     * @param bank          开户行(银行转账必填)
     * @param phone         手机号(银行转账必填)
     * @param vcode         验证码(银行转账必填)
     */
    public void accountCreat(String token, AccountInfo accountInfo) {
        mModel.accountCreat(token, accountInfo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.accountCreatSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 查询银行卡列表 ；接口号; - Y24
     *
     * @param token
     * @param storeId
     * @param pageNum
     */
    public void banCarAll(String token, String storeId, String pageNum) {
        mModel.banCarAll(token, storeId, pageNum)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<BankCards>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<BankCards> response) {
                        if (response.isSuccess()) {
                            mRootView.banCarAllSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 删除 银行卡 /支付宝/微信 接口号; - Y20
     *
     * @param token
     * @param bankId  银行卡id
     * @param storeId
     */
    public void bankIdDel(String token, DelAccountInfo delAccountInfo) {

        mModel.bankIdDel(token, delAccountInfo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.bankIdDelSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 根据店铺id 查询已有的 提现方式 ；接口号; - Y23
     *
     * @param token
     * @param storeId
     * @param pageNum
     * @param type
     */
    public void cashType(String token, String storeId,
                         int pageNum, String type) {
        mModel.cashType(token, storeId, pageNum, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<CashType>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<CashType> response) {
                        if (response.isSuccess()) {
                            mRootView.cashTypeSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用:商品管理-- 待审核产品 ；接口号; - Y7
     *
     * @param token
     * @param pageNum
     */
    public void checkPendingGoods(String token, int pageNum) {
        mModel.checkPendingGoods(token, pageNum)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Audit>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Audit> response) {
                        if (response.isSuccess()) {
                            mRootView.checkPendingGoodsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 通过产品id获取产品详情
     * @param token
     * @param goodsId
     */
    public void getGoodDetail(String token, String goodsId) {
        productDetailsModel.getGoodDetail(token, goodsId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<ProductDetail>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<ProductDetail> baseResponse) {
                        //if(baseResponse.isSuccess())
                        mRootView.getGoodDetailSuccess(baseResponse.getData());


                    }
                });
    }

    /**
     * 作用: 申请提现；接口号; - Y21
     *
     * @param token
     * @param accountId 提现账号id
     * @param priceSum  提现金额(最少50 必须是10的倍数)
     * @param storeId
     * @param phone
     * @param vcode     验证码
     * @param pwd       提现密码
     */
    public void drawing(String token, DrawBean drawBean) {
        mModel.drawing(token, drawBean)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.drawingSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 店铺管理，发布商品
     *
     * @param token
     * @param categoryOne   商品类目 一级类目
     * @param categoryTwo   商品类目 二级类目
     * @param categoryThree 店内分类
     * @param appClassId    商品类型
     * @param title         标题
     * @param brandId       商品品牌
     * @param stock         库存
     * @param unit          商品单位
     * @param unitPrice     商品单价
     * @param freightState  快递方式 0卖家承担运费 1 买家承担运费
     * @param freight       运费 买家承担运费是必填
     * @param details       商品详情
     * @param bannerImg     商品主图 字符串逗号分隔 xxx,xxx,xxx
     * @param detailsImg    商品详情图 字符串逗号分隔 xxx,xxx,xxx
     */
    public void goods(String token, PublishGoods publishGoods) {
        mModel.goods(token, publishGoods)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.goodsSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 商品管理-商品删除
     *
     * @param token
     * @param goodsId
     */
    public void goodsDel(String token, GoodsId goodsId) {
        mModel.goodsDel(token, goodsId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.goodsDelSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 店铺管理，编辑商品
     *
     * @param token
     * @param categoryOne 商品类目 一级类目
     *                    * @param categoryTwo 商品类目 二级类目
     *                    * @param categoryThree 店内分类
     *                    * @param appClassId 商品类型
     *                    * @param title 标题
     *                    * @param brandId 商品品牌
     *                    * @param stock 库存
     *                    * @param unit 商品单位
     *                    * @param unitPrice 商品单价
     *                    * @param freightState 快递方式 0卖家承担运费 1 买家承担运费
     *                    * @param freight 运费 买家承担运费是必填
     *                    * @param details 商品详情
     *                    * @param bannerImg 商品主图 字符串逗号分隔 xxx,xxx,xxx
     *                    * @param detailsImg 商品详情图 字符串逗号分隔 xxx,xxx,xxx
     * @param goodsId     产品id
     */
    public void goodsPut(String token, PublishGoods publishGoods) {
        mModel.goodsPut(token, publishGoods)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.goodsPutSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });

    }

    /**
     * 销售中的产品
     *
     * @param token
     * @param pageNum
     */
    public void inSalesGoods(String token, int pageNum) {
        mModel.inSalesGoods(token, pageNum)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Audit>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Audit> response) {
                        if (response.isSuccess()) {
                            mRootView.inSalesGoodsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 添加意见反馈
     *
     * @param token
     * @param backContent
     */
    public void opinion(String token, OpBack backContent) {
        mModel.opinion(token, backContent)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.opinionSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 设置提款密码
     *
     * @param token
     * @param pwd   密码(6-8纯数字)
     * @param phone
     */
    public void pwdCreat(String token, PhonePassword phonePassword) {
        mModel.pwdCreat(token, phonePassword)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.pwdCreatSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 提款记录
     *
     * @param token
     * @param storeId
     * @param pageNum
     * @param type
     */
    public void record(String token, String storeId, int pageNum, int type) {
        mModel.record(token, storeId, pageNum, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<WithDrawRecord>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<WithDrawRecord> response) {
                        if (response.isSuccess()) {
                            mRootView.recordSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 商品上架
     *
     * @param token
     * @param goodsId
     */
    public void sellingGoods(String token, GoodsId goodsId) {
        mModel.sellingGoods(token, goodsId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.sellingGoodsSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void getIdMyQRCode(String token, String storeId, String type) {
        mModel.getIdMyQRCode(token, storeId, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<QRCode>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<QRCode> response) {
                        if (response.isSuccess()) {
                            mRootView.getIdMyQRCodeSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 店铺开通状态查询；接口号; - Y1
     *
     * @param token
     */
    public void state(String token) {
        mModel.state(token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreState>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreState> response) {

                            mRootView.stateResult(response.getStatus(),response.getMsg(),response.getData());

                    }
                });
    }

    /**
     * 作用:商品管理-- 待上架的产品 ；接口号; - Y8
     *
     * @param token
     * @param pageNum
     */
    public void stayOnTheShelfGoods(String token, int pageNum) {
        mModel.stayOnTheShelfGoods(token, pageNum)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Audit>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Audit> response) {
                        if (response.isSuccess()) {
                            mRootView.stayOnTheShelfGoodsSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });


    }

    /**
     * 作用:商品管理-- 商品下架 ；接口号; - Y9
     *
     * @param token
     * @param goodsId
     */
    public void stopSellingGoods(String token, GoodsId goodsId) {
        mModel.stopSellingGoods(token, goodsId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.stopSellingGoodsSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 店铺开通 数据添加；接口号; - Y2
     *
     * @param token
     * @param trueName            真实姓名
     * @param idCard              身份证
     * @param storePhone          手机号
     * @param idCardHeadImg       身份证手持头部照
     * @param idCardIMg           身份证正面照
     * @param licenseName         营业执照名称
     * @param licenseNumber       营业执照号码
     * @param licenseIsLong       营业执照是否长期
     * @param licenseDate         营业执照期限
     * @param licenseImg          营业执照图片
     * @param storeImg            店铺头像
     * @param streoName           店铺名称
     * @param storeCategoryParent 经营类目父类目
     * @param storeCategory       经营类目子类目
     * @param province            省
     * @param city                市
     * @param detailsAddress      详细地址
     * @param background          背景图
     */
    public void store(AuthInfo authInfo) {
        mModel.store(authInfo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.storeSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 通过店铺id获取 店内分类
     *
     * @param pageNum
     * @param storeId
     */
    public void storeColumnList(int pageNum, String storeId) {
        mModel.storeColumnList(pageNum, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreCatBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreCatBean> baseResponse) {
                        mRootView.storeColumnResult(baseResponse.getData());

                    }
                });
    }

    /**
     * 作用: 通过店铺id获取 店内分类
     *
     * @param pageNum
     * @param storeId
     */
    public void storeColumnAll(int pageNum, String storeId) {
        mModel.storeColumnAll(pageNum, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreCatBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreCatBean> baseResponse) {
                        mRootView.storeColumnResult(baseResponse.getData());

                    }
                });
    }

    /**
     * 作用: 添加店铺分类；接口号; - Y3
     *
     * @param token
     * @param categoryName
     * @param categoryOrder 种类排序
     * @param storeId
     */
    public void storeColumn(String token, StoreCategory storeCategory) {
        mModel.storeColumn(token, storeCategory)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.storeColumnSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });

    }

    /**
     * 作用: 编辑店铺分类；接口号; - Y5
     *
     * @param token
     * @param storeCategoryEdit
     */
    public void storeColumnCreat(String token, StoreCategoryEdit storeCategoryEdit) {
        mModel.storeColumnCreat(token, storeCategoryEdit)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.storeColumnCreatSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 删除店铺分类；接口号; - Y4
     *
     * @param token
     * @param categoryId
     */
    public void storeColumnDel(String token, CategoryId categoryId) {
        mModel.storeColumnDel(token, categoryId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.storeColumnDelSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });

    }

    /**
     * 作用: 修改店铺头像/店铺背景图；接口号; - Y15
     *
     * @param token
     * @param storeLogo

     */
    public void storeLogoPut(String token, StoreLogo storeLogo) {
        mModel.storeLogoPut(token, storeLogo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            mRootView.storeLogoPutSuccess();
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 交易明细 ；接口号; - Y22
     *
     * @param token
     * @param storeId
     * @param pageNum
     * @param type
     */
    public void transaction(String token, String storeId,
                            int pageNum, int type) {
        mModel.transaction(token, storeId, pageNum, type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<TransList>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<TransList> response) {
                        if (response.isSuccess()) {
                            mRootView.transactionSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 作用: 获取钱包信息；接口号; - Y16
     *
     * @param token
     * @param storeId
     */
    public void wallet(String token, String storeId) {
        mModel.wallet(token, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Wallet>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Wallet> response) {
                        if (response.isSuccess()) {
                            mRootView.walletSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }


    /**
     * 获取认证信息
     *
     * @param userId
     */
    public void getAuth(String userId) {
        mModel.getAuth(userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Auth>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Auth> response) {
                        if (response.isSuccess()) {
                            mRootView.getAuthSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取月结信息
     *
     * @param token
     */
    public void getMonthMsg(String token) {
        mModel.getMonthMsg(token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<MonthMsg>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<MonthMsg> response) {
                        if (response.isSuccess()) {
                            mRootView.getMonthMsgSuccess(response.getData());
                        } else {
                            mRootView.getMonthMsgStatus(response.getStatus(),response.getMsg());
                        }
                    }
                });
    }


    public void updateUserImage(String upload_file) {
        MultipartBody.Part face = MultipartBody.Part.createFormData("file", "card_image.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(upload_file)));
        userModel.upLoadImage(face)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            mRootView.updateUserImageSuccess(stringBaseResponse.getData());
                        } else {
                            mRootView.showMessage(stringBaseResponse.getMsg());
                        }
                    }
                });
    }


    public void getCats() {
        homeModel.getCats()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CatBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CatBean>> response) {
                        if (response.isSuccess())
                            mRootView.getCatBeanList(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }

    public void getCats(int parentid) {
        homeModel.getCats(parentid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CatBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CatBean>> response) {
                        if (response.isSuccess())
                            mRootView.getCatBeanList(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }


    public void idStore(String token, String storeId) {
        mModel.idStore(token, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreInfomation>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreInfomation> response) {
                        if (response.isSuccess()) {
                            mRootView.idStoreSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void sendSms(String phone) {
        loginModel.sendSms(phone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess())
                            mRootView.showMessage("验证码发送成功.");
                        else
                            mRootView.showMessage(stringBaseResponse.getMsg());
                    }
                });
    }

    public void checkCode(
                          String phone,
                          String smsCode
                          ) {
        loginModel.checkCode(phone, smsCode)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {

                                mRootView.storeSuccess();

                        }
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }

    public void updateUserInfo(String id, String phone, String password, String logo, String nickName, int sex, String deviceId,String openId) {
        mModel.updateUserInfo(id, phone, password, logo, nickName, sex, BaseApp.deviceId,openId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<LoginBean>>(mErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<LoginBean> response) {
                        if (response.isSuccess())
                            mRootView.updateUserInfoSuccess(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }

}
