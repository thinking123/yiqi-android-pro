package com.eshop.mvp.presenter;

import android.net.Uri;

import com.eshop.app.base.BaseApp;
import com.eshop.huanxin.DemoHelper;
import com.eshop.huanxin.utils.chatUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.eshop.mvp.contract.MainContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.conversation.TokenBean;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.model.UserModel;
import com.eshop.mvp.utils.RxUtils;

import javax.inject.Inject;

//import io.rong.imkit.RongIM;
//import io.rong.imlib.model.UserInfo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/5/30 下午4:09
 * @Package com.eshop.mvp.presenter
 **/
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    RxErrorHandler rxErrorHandler;
    UserModel userModel;

    @Inject
    public MainPresenter(MainContract.Model model,
                         MainContract.View rootView,
                         RxErrorHandler rxErrorHandler,
                         UserModel userModel) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
        this.userModel = userModel;
    }


    public void conversationToken() {
        mModel.conversationToken()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TokenBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<TokenBean> tokenBeanBaseResponse) {
                        if (tokenBeanBaseResponse.isSuccess()) {
                            mRootView.connectRongIM(tokenBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(tokenBeanBaseResponse.getMsg());
                        }
                    }
                });
    }
//    private void joinHuanXinDefaultGroup(String chatRoomId){
//        EMGroupManager emGroupManager = EMClient.getInstance().groupManager();
//
//        try {
//            if(emGroupManager.getGroup(chatRoomId) == null){
//                Timber.e("not join default group");
//                emGroupManager.joinGroup(chatRoomId);
//            }else{
//                Timber.e("had joined default group");
//            }
//        }catch (HyphenateException e){
//            Timber.e("join default group error : " + e.getMessage());
//        }
//
////        EMGroupManager.getInstance().joinGroup(groupid);
//    }
    public void loginHuanXin(String id , String pw ){
        chatUtils.loginHuanXin(id ,pw).subscribe(new Observer<Void>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Void aVoid) {
                Timber.i("login huan xin onext");
            }

            @Override
            public void onError(Throwable e) {
                mRootView.showMessage("登入聊天系统失败:" + e.getMessage());

                mRootView.loginHuanxinResult();
            }

            @Override
            public void onComplete() {
                mRootView.loginHuanxinResult();
            }
        });
//        EMClient.getInstance().login(id, pw, new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                Timber.e("huanxin login: onSuccess");
//                chatUtils.joinHuanXinDefaultGroup(BaseApp.loginBean.getChatRoomId());
//                // ** manually load all local groups and conversation
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//
//                mRootView.loginHuanxinResult();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                mRootView.showMessage("登入聊天系统失败:" + s);
//
//                mRootView.loginHuanxinResult();
//            }
//
//            @Override
//            public void onProgress(int i, String s) {
//
//            }
//        });
    }
    public void getUserInfo() {
        userModel.getUserInfo()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                        if (userBeanBaseResponse.isSuccess()) {
                            mRootView.userInfo(userBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(userBeanBaseResponse.getMsg());
                        }
                    }
                });

     /**   RongIM.setUserInfoProvider(s -> {
            userModel.getUserInfo(Integer.parseInt(s))
                    .compose(RxUtils.applySchedulers(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<UserBean>>(rxErrorHandler) {
                        @Override
                        public void onNext(BaseResponse<UserBean> userBeanBaseResponse) {
                            if (userBeanBaseResponse.isSuccess()) {
                                UserBean data = userBeanBaseResponse.getData();
                                RongIM.getInstance().refreshUserInfoCache(
                                        new UserInfo(
                                                data.getUid().toString(),
                                                data.getUsername(),
                                                Uri.parse(data.getImage())));
                            } else {
                                mRootView.showMessage(userBeanBaseResponse.getMsg());
                            }
                        }
                    });
            return null;
        }, true);*/
    }



}
