package com.eshop.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.conversation.TokenBean;
import com.eshop.mvp.http.entity.login.UserBean;

import io.reactivex.Observable;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:05
 * @Package com.eshop.mvp.contract
 **/
public interface MainContract  {
    interface View extends IView {

        void connectRongIM(TokenBean data);

        void userInfo(UserBean data);

        void loginHuanxinResult();
    }

    interface Model extends IModel {
        Observable<BaseResponse<TokenBean>>  conversationToken();
    }
}
