package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppcarStore;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.cart.CartBean;
import com.eshop.mvp.http.entity.cart.StoreBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.mvp.contract
 **/
public interface CartContract {
    interface Model extends IModel {

        Observable<MyBaseResponse<AppCartStore>> getCartList(String token,String userId);

        Observable<MyBaseResponse<String>> addGood(String token,String userId,String goodsId,int goodNum);

        Observable<MyBaseResponse<List<Order>>> addOrder(String token, AppcarStore appcarStore);

        Observable<MyBaseResponse<String>> countPrice(String token,String ids);

        Observable<MyBaseResponse<String>> delCart(String token,String ids);

        Observable<MyBaseResponse<String>> updateNum(String token,String id,String num);

        Observable<MyBaseResponse<List<AddressBean>>> getAddressList(String token,String userId);


    }

    interface View extends IView {
        void getCartListSuccess(AppCartStore data);

        void addGoodSuccess();

        void addOrderSuccess(List<Order> data);

        void countPriceSuccess(String price);

        void delCartSuccess();

        void updateNumSuccess(int count);

        void getAddressList(List<AddressBean> list);

    }
}
