package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.AdBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.product.RecommendBean;

import io.reactivex.Observable;

/**
 * @Author shijun
 * @Data 2019/1/14
 * @Package com.eshop.mvp.contract
 **/


public interface RecommendContract {

    interface Model extends IModel {

        Observable<MyBaseResponse<HomeBean>> getHomeData();

        Observable<MyBaseResponse<GoodsBean>> getGoodsData(int pageNum);

        Observable<MyBaseResponse<AdBean>> getAdData();



    }

    interface View extends IView {

        void getHomeDataResult(HomeBean data);

        void getGoodsDataResult(GoodsBean data);

        void getGoodsDataError(String msg);

        void getAdDataResult(AdBean data);

    }
}
