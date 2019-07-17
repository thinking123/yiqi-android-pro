package com.eshop.mvp.presenter;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppcarStore;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.cart.CartBean;
import com.eshop.mvp.http.entity.cart.StoreBean;
import com.eshop.mvp.ui.widget.AmountView;
import com.eshop.mvp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author shijun
 * @Data 2019/1/23
 * @Package com.eshop.mvp.presenter
 **/
@FragmentScope
public class CartPresenter extends BasePresenter<CartContract.Model, CartContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    public CartPresenter(CartContract.Model model, CartContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void getCartList(String token,String userId) {
        mModel.getCartList(token,userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<AppCartStore>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<AppCartStore> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.getCartListSuccess(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }


    public void addGood(String token,String userId, String goodsId,int goodNum) {
        mModel.addGood(token,userId, goodsId,goodNum)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> baseResponse) {
                       mRootView.addGoodSuccess();

                    }
                });
    }

    public void addOrder(String token,AppcarStore appcarStore) {
        mModel.addOrder(token,appcarStore)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<Order>>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<Order>> baseResponse) {
                        mRootView.addOrderSuccess(baseResponse.getData());

                    }
                });
    }

    public void countPrice(String token,String ids) {
        mModel.countPrice(token,ids)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> baseResponse) {
                        mRootView.countPriceSuccess(baseResponse.getData());

                    }
                });
    }

    public void delCart(String token,String ids) {
        mModel.delCart(token, ids)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.delCartSuccess();
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }

    public void updateNum(String token,AmountView view, String id,
                                   int num) {
        mModel.updateNum(token,id, num+"")
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> cartBeanBaseResponse) {
                        if (cartBeanBaseResponse.isSuccess()) {
                            mRootView.updateNumSuccess(num);
                        } else {
                            view.setCurrentAmount(num);
                            mRootView.showMessage(cartBeanBaseResponse.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        view.setCurrentAmount(num);

                    }
                });
    }

    public void getAddressList(String token,String userId) {
        mModel.getAddressList(token,userId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<AddressBean>>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<AddressBean>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.getAddressList(baseResponse.getData());
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });
    }


}
