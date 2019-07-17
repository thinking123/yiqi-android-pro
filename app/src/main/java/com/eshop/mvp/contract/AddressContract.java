package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.home.BrandBean;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.Query;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/28/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AddressContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void selectProSuccess(List<CityBean> list);
        void selectCitySuccess(List<CityBean> list);

        void getSuccess(List<AddressBean> list);
        void addSuccess();
        void delSuccess();
        void setDefaultSuccess();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<MyBaseResponse<List<CityBean>>> selectPro();
        Observable<MyBaseResponse<List<CityBean>>> selectCity(String cityCode);

        Observable<MyBaseResponse<List<AddressBean>>> get(String token,String userId);
        Observable<MyBaseResponse<List<AddressBean>>> getById(String token,String userId);

        Observable<MyBaseResponse<String>> add(
                String token,
                String userId,
                String address,
                String receiveUserName,
                String receivePhone
        );

        Observable<MyBaseResponse<String>> del(
                String token,
                String id
        );

        Observable<MyBaseResponse<String>> edit(
                String token,
                String id,
                String address,
                String receiveUserName,
                String receivePhone,
                String isDefault

        );

        Observable<MyBaseResponse<String>> setDefault(
                String token,
                String userId,
                String id
        );


    }
}
