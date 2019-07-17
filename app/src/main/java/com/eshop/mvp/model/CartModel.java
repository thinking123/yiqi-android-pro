package com.eshop.mvp.model;

import com.eshop.mvp.http.api.service.AddressService;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppcarStore;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.http.api.service.CartService;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.cart.CartBean;
import com.eshop.mvp.http.entity.cart.StoreBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author shijun
 * @Data 2019/1/17
 * @Package com.eshop.mvp.model
 **/
public class CartModel extends BaseModel implements CartContract.Model {

    public CartModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<MyBaseResponse<AppCartStore>> getCartList(String token,String userId) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .getCartList(token,userId);
    }

    @Override
    public Observable<MyBaseResponse<String>> addGood(String token,String userId, String goodsId, int goodNum) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .addGood(token,userId, goodsId, goodNum);
    }

    @Override
    public Observable<MyBaseResponse<List<Order>>> addOrder(String token, AppcarStore appcarStore) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .addOrder(token,appcarStore);
    }

    @Override
    public Observable<MyBaseResponse<String>> countPrice(String token,String ids) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .countPrice(token,ids);
    }

    @Override
    public Observable<MyBaseResponse<String>> delCart(String token,String ids) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .delCart(token, ids);
    }

    @Override
    public Observable<MyBaseResponse<String>> updateNum(String token,String id, String num) {
        return mRepositoryManager.obtainRetrofitService(CartService.class)
                .updateNum(token,id, num);
    }

    @Override
    public Observable<MyBaseResponse<List<AddressBean>>> getAddressList(String token,String userId) {
        return mRepositoryManager.obtainRetrofitService(AddressService.class)
                .get(token,userId);
    }


}
