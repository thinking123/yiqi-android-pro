package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.GoodsId;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreId;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.eshop.mvp.http.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Author shijun
 * @Data 2019/1/24
 * @Package com.eshop.mvp.contract
 **/
public interface ProductDetailsContract {
    interface View extends IView {
        void addGoodSuccess();
        void getGoodDetailSuccess(ProductDetail good);
        void collectioGoodsFindResult(GoodsBean data);
        void collectionStoreFindResult(StoresBean data);
        void storeColumnResult(StoreCatBean data);
        void storeGoodsResult(GoodsBean data);
        void storeIdResult(StoreInfo data);
        void collectionAddGoodsSuccess();
        void collectionAddStoreSuccess();
        void collectionDelSuccess();
        void collectionDelStoreSuccess();
        void updateUserImageSuccess(String data);
        void getCatBeanList(List<CatBean> data);
        void loginHuanxinResult();
    }

    interface Model extends IModel {
      //  Observable<BaseResponse> addCart(Integer productId, Integer count);

        Observable<MyBaseResponse<String>> addGood(String token,String userId, String goodsId, int goodNum);

        Observable<MyBaseResponse<ProductDetail>> getGoodDetail(String token, String goodsId);

        Observable<MyBaseResponse<GoodsBean>> collectionGoodsFind(int pageNum,String token);

        Observable<MyBaseResponse<StoresBean>> collectionStoreFind(int pageNum,String token);

        Observable<MyBaseResponse<StoreCatBean>> storeColumn(int pageNum, String storeId);

        Observable<MyBaseResponse<StoreCatBean>> storeColumnAll(int pageNum, String storeId);


        Observable<MyBaseResponse<GoodsBean>> storeGoods(int pageNum,String storeId,String storeColumn,String sortType);

        Observable<MyBaseResponse<StoreInfo>> storeId(String token,String storeId);

        //post
        Observable<MyBaseResponse<String>> collectionAddGoods(String token, GoodsId goodsId);

        Observable<MyBaseResponse<String>> collectionAddStore(String token, StoreId storeId);

        Observable<MyBaseResponse<String>> collectionDel(String token, DelId delId);

        Observable<MyBaseResponse<String>> collectionDelStore(String token, DelId delId);

    }
}
