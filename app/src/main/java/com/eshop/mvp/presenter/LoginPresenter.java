package com.eshop.mvp.presenter;

import com.eshop.app.base.BaseApp;
import com.eshop.huanxin.DemoHelper;
import com.eshop.huanxin.utils.chatUtils;
import com.eshop.mvp.http.entity.MyBaseResponse;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.eshop.mvp.model.UserModel;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.BaseResponse;
import com.eshop.mvp.http.entity.login.JWTBean;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.utils.RxUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * @Author shijun
 * @Data 2019/1/11
 * @Package com.eshop.mvp.presenter
 **/
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    RxErrorHandler rxErrorHandler;

    @Inject
    UserModel userModel;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView, RxErrorHandler rxErrorHandler) {
        super(model, rootView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void login(String phone, String password, String deviceId) {
        mModel.login(phone, password,BaseApp.deviceId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<LoginBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<LoginBean> jwtBeanBaseResponse) {
                        if (jwtBeanBaseResponse.isSuccess()) {
                            mRootView.loginResult(jwtBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(jwtBeanBaseResponse.getMsg());
                        }
                    }
                });
    }


    public void loadHuanXinData(){

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
//
//                chatUtils.joinHuanXinDefaultGroup(BaseApp.loginBean.getChatRoomId());
//
//                // ** manually load all local groups and conversation
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//
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
    public void sendSms(String phone) {
        mModel.sendSms(phone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess())
                            mRootView.showMessage("验证码发送成功.");
                        else
                            mRootView.showMessage(stringBaseResponse.getMsg());
                    }
                });
    }

    public void checkCode(String password,
                          String phone,
                          String smsCode,
                          String from) {
        mModel.checkCode(phone, smsCode)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> response) {
                        if (response.isSuccess()) {
                            if (from.equalsIgnoreCase("register")) {
                                //register(phone, password);
                                mRootView.checkCodeSuccess();
                            } else if (from.equalsIgnoreCase("password")) {
                                setPassword(phone, password, "android");
                            } else if (from.equalsIgnoreCase("bind")) {
                                mRootView.checkCodeSuccess();
                            }
                        }
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }

    public void register(String phone,
                         String password,
                         String logo,
                         String nickName) {
        mModel.register(phone, password,logo,nickName)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<UserInfoBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<UserInfoBean> userBeanBaseResponse) {
                        if (userBeanBaseResponse.isSuccess())
                            mRootView.registerSuccess(userBeanBaseResponse.getData());
                        else
                            mRootView.showMessage(userBeanBaseResponse.getMsg());
                    }
                });
    }

    public void setPassword(String phone, String password, String deviceId) {
        mModel.setPassword(phone, password,BaseApp.deviceId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<LoginBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<LoginBean> jwtBeanBaseResponse) {
                        if (jwtBeanBaseResponse.isSuccess()) {
                            mRootView.setPasswordResult(jwtBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(jwtBeanBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void updateUserImage(String upload_file) {
        MultipartBody.Part face = MultipartBody.Part.createFormData("file", "header_image.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(upload_file)));
        userModel.upLoadImage(face)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()){
                            mRootView.updateUserImageSuccess(stringBaseResponse.getData());
                        } else {
                            mRootView.showMessage(stringBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void wxLogin(String unionid) {
        mModel.wxlogin(unionid)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<LoginBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<LoginBean> jwtBeanBaseResponse) {
                        if (jwtBeanBaseResponse.isSuccess()) {
                            mRootView.wxLoginResult(jwtBeanBaseResponse.getData());
                        } else {
                            mRootView.showMessage(jwtBeanBaseResponse.getMsg());
                        }
                    }
                });
    }

    public void checkPhone(String phone) {
        mModel.checkPhone(phone)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<String>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess())
                            mRootView.checkPhoneSuccess();
                        else
                            mRootView.showMessage(stringBaseResponse.getMsg());
                    }
                });
    }

    public void updateUserInfo(String id, String phone, String password, String logo, String nickName, int sex, String deviceId,String openId) {
        mModel.updateUserInfo(id, phone, password, logo, nickName, sex, BaseApp.deviceId,openId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<MyBaseResponse<LoginBean>>(rxErrorHandler) {
                    @Override
                    public void onNext(MyBaseResponse<LoginBean> response) {
                        if (response.isSuccess())
                            mRootView.updateUserInfoSuccess(response.getData());
                        else
                            mRootView.showMessage(response.getMsg());
                    }
                });
    }

}
