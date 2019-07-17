package com.eshop.mvp.presenter;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.cart.AppcarStore;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.model.CartModel;
import com.eshop.mvp.model.OrderModel;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.ui.widget.AmountView;
import com.eshop.mvp.utils.RxUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @Author shijun
 * @Data 2019/2/1
 * @Package com.eshop.mvp.presenter
 **/
@ActivityScope
public class CreateOrderPresenter extends BasePresenter<IModel, OrderContract.View> {

    @Inject
    RxErrorHandler rxErrorHandler;

    @Inject
    OrderModel orderModel;

    @Inject
    CartModel cartModel;

    @Inject
    public CreateOrderPresenter(OrderContract.View rootView) {
        super(rootView);
    }

    /**
     * 下订单
     * @param userId 用户id
     * @param goodsId 商品id
     * @param goodsAmount 数量
     * @param remarks 留言
     * @param addressId 地址Id
     * @param orderType 订单类型0普通1月结
     */
    public void addOrder(String userId, String goodsId, String goodsAmount, String remarks, String addressId, int orderType) {
        orderModel.addOrder(BaseApp.loginBean.getToken(),userId, goodsId, goodsAmount, remarks, addressId, orderType)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<Order>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<Order> response) {
                        if (response.isSuccess()) {
                            mRootView.addOrderSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    /**
     * 购物车订单
     * @param params
     * json
     * {
     * 	"addressId": "2",
     * 	"orderType": "0",
     * 	"storelist": [{
     * 		"storeId": "1",
     * 		"remarks": "111",
     * 		"carlist": "2, 5"
     * 	}, {
     * 		"storeId": "3",
     * 		"remarks": "222",
     * 		"carlist": "3"
     * 	}]
     * }
     */
    public void addCartOrder(String token,AppcarStore appcarStore) {
        cartModel.addOrder(token,appcarStore)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<List<Order>>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<List<Order>> response) {
                        if (response.isSuccess()) {
                            mRootView.addOrderSuccess(response.getData());
                        } else {
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

    public void updateNum(String token,AmountView view, String id,
                          int num) {
        cartModel.updateNum(token,id, num+"")
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> cartBeanBaseResponse) {
                        if (cartBeanBaseResponse.isSuccess()) {
                            //mRootView.updateNumSuccess(num);
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
        cartModel.getAddressList(token,userId)
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
