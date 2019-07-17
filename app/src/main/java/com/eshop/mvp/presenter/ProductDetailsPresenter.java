package com.eshop.mvp.presenter;

import com.eshop.huanxin.utils.chatUtils;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.GoodsId;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreId;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoreLogo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.model.HomeModel;
import com.eshop.mvp.model.StoreManagerModel;
import com.eshop.mvp.model.UserModel;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.utils.RxUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * @Author shijun
 * @Data 2011/1/24
 * @Package com.eshop.mvp.presenter
 **/
@ActivityScope
public class ProductDetailsPresenter extends BasePresenter<ProductDetailsContract.Model, ProductDetailsContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    HomeModel homeModel;

    @Inject
    UserModel userModel;

    @Inject
    StoreManagerModel storeManagerModel;

    @Inject
    public ProductDetailsPresenter(ProductDetailsContract.Model model, ProductDetailsContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void getCats() {
        homeModel.getCats()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<CatBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<CatBean>> response) {
                        if (response.isSuccess())
                            mRootView.getCatBeanList(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }


    public void loginHuanXin(String id , String pw ) {
        chatUtils.loginHuanXin(id, pw).subscribe(new Observer<Void>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Void aVoid) {
                Timber.i("login huan xin onext");
            }

            @Override
            public void onError(Throwable e) {
                mRootView.showMessage("登入聊天系统失败:" + e.getMessage());

                mRootView.loginHuanxinResult();
            }

            @Override
            public void onComplete() {
                mRootView.loginHuanxinResult();
            }
        });
    }
    /**
     * 添加商品到购物车
     * @param userId
     * @param goodsId
     * @param goodNum
     */
    public void addGood(String token,String userId, String goodsId,int goodNum) {
        mModel.addGood(token,userId, goodsId,goodNum)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> baseResponse) {
                        if(baseResponse.isSuccess())
                            mRootView.addGoodSuccess();
                        else
                            mRootView.showMessage(baseResponse.getMsg());

                    }
                });
    }

    /**
     * 作用: 通过产品id获取产品详情
     * @param token
     * @param goodsId
     */
    public void getGoodDetail(String token, String goodsId) {
        mModel.getGoodDetail(token, goodsId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<ProductDetail>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<ProductDetail> baseResponse) {
                        //if(baseResponse.isSuccess())
                            mRootView.getGoodDetailSuccess(baseResponse.getData());


                    }
                });
    }

    /**
     * 获取 收藏的产品
     * @param pageNum
     * @param token
     */
    public void collectionGoodsFind(int pageNum,String token){
        mModel.collectionGoodsFind(pageNum, token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> baseResponse) {
                        mRootView.collectioGoodsFindResult(baseResponse.getData());

                    }
                });
    }

    /**
     * 获取关注的店铺
     * @param pageNum
     * @param token
     */
    public void collectionStoreFind(int pageNum,String token) {
        mModel.collectionStoreFind(pageNum, token)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoresBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoresBean> baseResponse) {
                        mRootView.collectionStoreFindResult(baseResponse.getData());

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
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreCatBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreCatBean> baseResponse) {
                        mRootView.storeColumnResult(baseResponse.getData());

                    }
                });
    }

    /**
     * 作用: 通过店铺id获取 店内分类
     * @param pageNum
     * @param storeId
     */
    public void storeColumn(int pageNum, String storeId) {
        mModel.storeColumn(pageNum, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreCatBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreCatBean> baseResponse) {
                        mRootView.storeColumnResult(baseResponse.getData());

                    }
                });
    }

    /**
     * 作用:通过店铺id、店铺类目id 获取 店铺商品
     * @param pageNum
     * @param storeId
     * @param storeColumn
     * @param sortType asc 价格正序，desc 价格倒序，不传默认时间倒序
     */
    public void storeGoods(int pageNum,String storeId,String storeColumn,String sortType) {
        mModel.storeGoods(pageNum, storeId, storeColumn, sortType)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<GoodsBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<GoodsBean> baseResponse) {
                        mRootView.storeGoodsResult(baseResponse.getData());

                    }
                });
    }

    /**
     * 作用: 通过店铺id获取店铺 列表页店铺信息
     * @param token
     * @param storeId
     */
    public void storeId(String storeId) {
        mModel.storeId(null, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreInfo>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreInfo> baseResponse) {
                        mRootView.storeIdResult(baseResponse.getData());

                    }
                });
    }

    public void storeId(String token,String storeId) {
        mModel.storeId(token, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<StoreInfo>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<StoreInfo> baseResponse) {
                        mRootView.storeIdResult(baseResponse.getData());

                    }
                });
    }

    /**
     * 收藏产品
     * @param token
     * @param goodsId
     */
    public void collectionAddGoods(String token, GoodsId goodsId)
    {
        mModel.collectionAddGoods(token, goodsId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> baseResponse) {
                        if(baseResponse.isSuccess())
                            mRootView.collectionAddGoodsSuccess();
                        else
                            mRootView.showMessage(baseResponse.getMsg());

                    }
                });
    }

    /**
     * 添加关注店铺
     * @param token
     * @param storeId
     */
    public void collectionAddStore(String token, StoreId storeId)
    {
        mModel.collectionAddStore(token, storeId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> baseResponse) {
                        if(baseResponse.isSuccess())
                            mRootView.collectionAddStoreSuccess();
                        else
                            mRootView.showMessage(baseResponse.getMsg());

                    }
                });
    }

    /**
     * 取消收藏商品
     * @param token
     * @param delId
     */
    public void collectionDel(String token, DelId delId)
    {
        mModel.collectionDel(token, delId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> baseResponse) {
                        if(baseResponse.isSuccess())
                            mRootView.collectionDelSuccess();
                        else
                            mRootView.showMessage(baseResponse.getMsg());

                    }
                });
    }

    /**
     * 取消收藏店铺
     * @param token
     * @param delId
     */
    public void collectionDelStore(String token, DelId delId)
    {
        mModel.collectionDelStore(token, delId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> baseResponse) {
                        if(baseResponse.isSuccess())
                            mRootView.collectionDelStoreSuccess();
                        else
                            mRootView.showMessage(baseResponse.getMsg());

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
        storeManagerModel.storeLogoPut(token, storeLogo)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                           // mRootView.showMessage("修改成功");
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void updateUserImage(String upload_file) {
        MultipartBody.Part face = MultipartBody.Part.createFormData("file", "card_image.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(upload_file)));
        userModel.upLoadImage(face)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
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


}
