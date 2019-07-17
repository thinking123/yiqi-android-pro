package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.CategoryBean;
import com.eshop.mvp.http.entity.home.CompanyBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2019 08:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getCatBeanList(List<CatBean> data);
        void getAboutResult(CompanyBean data);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<MyBaseResponse<List<CatBean>>> getCats(int parentId);
        Observable<MyBaseResponse<List<CatBean>>> getCats();

        Observable<MyBaseResponse<CompanyBean>> getAbout();




    }
}
