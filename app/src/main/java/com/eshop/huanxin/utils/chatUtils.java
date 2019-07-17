package com.eshop.huanxin.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.LoginConfig;
import com.eshop.huanxin.DemoHelper;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.LoginUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.exceptions.HyphenateException;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class chatUtils {
    public interface Callback{
        void onSuccess();
    }



    public static Observable<Void> loginHuanXin(String id , String pw ){

        Observable observable = Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                EMClient.getInstance().login(id, pw, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Timber.e("huanxin login: onSuccess");


                        emitter.onNext(null);

//                        chatUtils.joinHuanXinDefaultGroupEx(BaseApp.loginBean.getChatRoomId());
                        // ** manually load all local groups and conversation
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        LoginBean loginBean = BaseApp.loginBean;
                        DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(loginBean.getNickNmae());
                        DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(loginBean.getLogo());
//                        boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
//                                BaseApp.currentUserNick.trim());
//                        if (!updatenick) {
//                            Timber.e("update current user nick fail");
//                        }
//                        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//
                        emitter.onComplete();
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onError(new Exception(s));
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

            }
        });


        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


    }




    public static void joinHuanXinDefaultGroupEx(String chatRoomId) {

        EMGroupManager emGroupManager = EMClient.getInstance().groupManager();

        try {
            if (emGroupManager.getGroup(chatRoomId) == null) {
                Timber.e("not join default group");
                emGroupManager.joinGroup(chatRoomId);
            } else {
                Timber.e("had joined default group");
            }

            List<EMGroup> list = emGroupManager.getJoinedGroupsFromServer();
            Timber.i("EMGroup length : " + list.size());
            if (list.size() > 0) {
            }
        } catch (HyphenateException e) {
            Timber.e("join default group error : " + e.getMessage());
        }finally {

        }


//        EMGroupManager.getInstance().joinGroup(groupid);
    }
//    public static void joinHuanXinDefaultGroup(String chatRoomId) {
//
//        new Thread(() -> {
//            EMGroupManager emGroupManager = EMClient.getInstance().groupManager();
//
//            try {
//                if (emGroupManager.getGroup(chatRoomId) == null) {
//                    Timber.e("not join default group");
//                    emGroupManager.joinGroup(chatRoomId);
//                } else {
//                    Timber.e("had joined default group");
//                }
//
//                List<EMGroup> list = emGroupManager.getJoinedGroupsFromServer();
//                Timber.i("EMGroup length : " + list.size());
//                if (list.size() > 0) {
//                }
//            } catch (HyphenateException e) {
//                Timber.e("join default group error : " + e.getMessage());
//            }finally {
//
//            }
//        }).start();
//
//
////        EMGroupManager.getInstance().joinGroup(groupid);
//    }


    public static Single<List<EMGroup>> loadGroupFromServer(){
        return Single.create(new SingleOnSubscribe<List<EMGroup>>() {
            @Override
            public void subscribe(SingleEmitter<List<EMGroup>> emitter) throws Exception {
                EMClient.getInstance().groupManager().asyncGetJoinedGroupsFromServer(new EMValueCallBack<List<EMGroup>>() {
                    @Override
                    public void onSuccess(List<EMGroup> emGroups) {
                        emitter.onSuccess(emGroups);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Timber.e(s);
//                        emitter.onError(new HyphenateException(s));

                        emitter.onSuccess(new ArrayList<EMGroup>());
                    }
                });
            }
        });
    }

    public static Completable loadAll() {
//        Observable.just()
        return   Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                if (LoginUtils.isLogin(BaseApp.getInstance().getApplicationContext())) {
                    Timber.e("BaseApp loadAll");
                    if (DemoHelper.getInstance().isLoggedIn()) {

//                        try {
//                            EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(BaseApp.loginBean.getChatRoomId() , EMConversation.EMConversationType.ChatRoom , true);
//                            if(emConversation != null)
//                                Timber.i(emConversation.toString());
//                            else {
//                                EMClient.getInstance().chatManager().getAllConversations();
//                            }
//                        }catch (Exception e){
//
//                            Timber.e(e.getMessage());
//                        }

                        EMClient.getInstance().chatManager().loadAllConversations();
                        EMClient.getInstance().groupManager().loadAllGroups();
                    }
                }

                emitter.onComplete();

            }
        }) .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());




//       return chatUtils.loadGroupFromServer()
//                .subscribeOn(Schedulers.io())
//                .flatMapCompletable(new Function<List<EMGroup>, CompletableSource>() {
//            @Override
//            public CompletableSource apply(List<EMGroup> emGroups) throws Exception {
//
//                Timber.i("emgroup" );
//                Timber.i(emGroups.toString());
//                return Completable.create(new CompletableOnSubscribe() {
//                    @Override
//                    public void subscribe(CompletableEmitter emitter) throws Exception {
//                        if (LoginUtils.isLogin(BaseApp.getInstance().getApplicationContext())) {
//                            Timber.e("BaseApp loadAll");
//                            if (DemoHelper.getInstance().isLoggedIn()) {
//
//                                try {
//                                    EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(BaseApp.loginBean.getChatRoomId() , EMConversation.EMConversationType.ChatRoom , true);
//                                    if(emConversation != null)
//                                    Timber.i(emConversation.toString());
//                                    else {
//                                        EMClient.getInstance().chatManager().getAllConversations();
//                                    }
//                                }catch (Exception e){
//
//                                    Timber.e(e.getMessage());
//                                }
//
//                                EMClient.getInstance().chatManager().loadAllConversations();
//                                EMClient.getInstance().groupManager().loadAllGroups();
//                            }
//                        }
//
//                        emitter.onComplete();
//
//                    }
//                });
//            }
//        }).observeOn(AndroidSchedulers.mainThread());
//
//        return Completable.create(new CompletableOnSubscribe() {
//            @Override
//            public void subscribe(CompletableEmitter emitter) throws Exception {
//                if (LoginUtils.isLogin(BaseApp.getInstance().getApplicationContext())) {
//                    Timber.e("BaseApp loadAll");
//                    if (DemoHelper.getInstance().isLoggedIn()) {
//                        try {
//                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
//                            EMClient.getInstance().chatManager().loadAllConversations();
//                            EMClient.getInstance().groupManager().loadAllGroups();
//                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                        } catch (HyphenateException e) {
//                            Timber.e("join default group error : " + e.getMessage());
//                        }finally {
//                            Timber.e("callback loadAll");
//                        }
//                    }
//                }
//
//                emitter.onComplete();
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//
//        Timber.e("start loadAll");
//        if (LoginUtils.isLogin(BaseApp.getInstance().getApplicationContext())) {
//            Timber.e("BaseApp loadAll");
//            if (DemoHelper.getInstance().isLoggedIn()) {
//                Timber.e("getInstance loadAll");
//                new Thread(() -> {
//                    try {
//                        EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
//                        EMClient.getInstance().chatManager().loadAllConversations();
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                    } catch (HyphenateException e) {
//                        Timber.e("join default group error : " + e.getMessage());
//                    }finally {
//                        Timber.e("callback loadAll");
//                        cb.onSuccess();
//                    }
//                }).start();
//
//                return;
//            }
//        }
//
//        cb.onSuccess();
    }

    public static void setChatTitleBarStyle(EaseTitleBar titleBar , Context context){
        titleBar.setBackgroundColor(ContextCompat.getColor(context , R.color.normal_back_ground));
        TextView titleView = (TextView) titleBar.findViewById(com.hyphenate.easeui.R.id.title);
        titleView.setTextColor(ContextCompat.getColor(context ,R.color.text_black_33));
    }

}
