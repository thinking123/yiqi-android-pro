package com.eshop.mvp.contract;

import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.auth.MonthData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.store.Auth;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2019 22:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MonthAuthContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getAuthSuccess(Auth auth);

        void getMonthMsgSuccess(MonthMsg monthMsg);

        void getMonthMsgStatus(String status,String msg,MonthMsg monthMsg);

        void monthAddSuccess();
        void monthAddSuccess(String msg);

        void monthEditSuccess();

        void monthPaySuccess();

        void updateUserImageSuccess(String url);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<MyBaseResponse<Auth>> getAuth(String token, String userId);

        Observable<MyBaseResponse<MonthMsg>> getMonthMsg(String token);

        Observable<MyBaseResponse<String>> monthAdd(String token,
                                                    MonthData monthData);

        Observable<MyBaseResponse<String>> monthEdit(String token,
                                                     MonthData monthData);

        Observable<MyBaseResponse<String>> monthPay(
                String token,
                String userId,
                String id);


    }
}
