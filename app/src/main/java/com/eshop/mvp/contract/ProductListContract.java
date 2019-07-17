package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/22/2019 15:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ProductListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getGoodsError(String msg,String type);
        void getGoodsResult(GoodsBean data,String type);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<MyBaseResponse<GoodsBean>> getMonthGoods(int pageNum,String monthId);
        Observable<MyBaseResponse<GoodsBean>> getSaleGoods(int pageNum,String saleId);
        Observable<MyBaseResponse<GoodsBean>> getPriceSaleGoods(int pageNum,String priceSale);
        Observable<MyBaseResponse<GoodsBean>> getMiaoMiaoGouGoods(int pageNum,String miaoMiaoGou);
        Observable<MyBaseResponse<GoodsBean>> getBrandGoods(int pageNum,String brandId);


    }
}
