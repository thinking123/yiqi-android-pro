package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.product.CollectNum;
import com.eshop.mvp.http.entity.store.StoreState;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Header;

/**
 * @Author shijun
 * @Data 2019/3/11
 * @Package com.eshop.mvp.contract
 **/
public interface SelfContract {
    interface Model extends IModel {

        Observable<MyBaseResponse<CollectNum>> getCollectionNum(String token);

    }

    interface View extends IView {

        void getCollectionNumSuccess(CollectNum data);
        void stateSuccess(StoreState storeState);
        void stateResult(String status,String msg,StoreState storeState);
    }
}
