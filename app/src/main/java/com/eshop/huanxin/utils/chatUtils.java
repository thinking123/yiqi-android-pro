package com.eshop.huanxin.utils;

import com.eshop.app.base.BaseApp;
import com.eshop.app.base.LoginConfig;
import com.eshop.huanxin.DemoHelper;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.utils.LoginUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class chatUtils {
    public interface Callback{
        void onSuccess();
    }
    public static void joinHuanXinDefaultGroup(String chatRoomId) {

        new Thread(() -> {
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
            }
        });


//        EMGroupManager.getInstance().joinGroup(groupid);
    }

    public static void loadAll(chatUtils.Callback cb) {
//        Observable.just()

        Timber.e("start loadAll");
        if (LoginUtils.isLogin(BaseApp.getInstance().getApplicationContext())) {
            Timber.e("BaseApp loadAll");
            if (DemoHelper.getInstance().isLoggedIn()) {
                Timber.e("getInstance loadAll");
                new Thread(() -> {
                    try {
                        EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        EMClient.getInstance().groupManager().loadAllGroups();
                    } catch (HyphenateException e) {
                        Timber.e("join default group error : " + e.getMessage());
                    }finally {
                        Timber.e("callback loadAll");
                        cb.onSuccess();
                    }
                }).start();

                return;
            }
        }

        cb.onSuccess();
    }
}
