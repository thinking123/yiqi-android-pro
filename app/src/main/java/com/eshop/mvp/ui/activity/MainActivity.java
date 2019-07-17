package com.eshop.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eshop.app.base.BaseApp;
import com.eshop.app.base.LoginConfig;
import com.eshop.huanxin.DemoHelper;
import com.eshop.mvp.ui.fragment.CartHomeFragment;
import com.eshop.mvp.ui.fragment.CategoryAllFragment;
import com.eshop.mvp.ui.fragment.ConversationListFragment;
import com.eshop.mvp.ui.fragment.ConversationListWrapFragment;
import com.eshop.mvp.ui.fragment.HomeFragment;
import com.eshop.mvp.ui.fragment.HotLineFragment;
import com.eshop.mvp.utils.LoginUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerMainComponent;
import com.eshop.di.module.MainModule;
import com.eshop.mvp.contract.MainContract;
import com.eshop.mvp.http.entity.conversation.TokenBean;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.presenter.MainPresenter;
import com.eshop.mvp.ui.fragments.CartFragment;
import com.eshop.mvp.ui.fragments.CategoryFragment;
import com.eshop.mvp.ui.fragments.FindFragment;
import com.eshop.mvp.ui.fragments.RecommendFragment;
import com.eshop.mvp.ui.fragments.SelfFragment;
import com.eshop.mvp.ui.widget.bottombar.BottomBar;
import com.eshop.mvp.ui.widget.bottombar.BottomBarTab;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.PicChooserHelper;
import com.eshop.mvp.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
//import io.rong.imkit.RongIM;
//import io.rong.imkit.manager.IUnReadMessageObserver;
//import io.rong.imlib.RongIMClient;
//import io.rong.imlib.model.Conversation;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.yokeyword.fragmentation.ISupportFragment;
import timber.log.Timber;

public class  MainActivity extends BaseSupportActivity<MainPresenter> implements MainContract.View {//, IUnReadMessageObserver {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private ISupportFragment[] mFragments = new ISupportFragment[5];
    private PicChooserHelper picChooserHelper;
    private BottomBarTab homeTab;
    private BottomBarTab categoryTab;
    private BottomBarTab cartTab;
    private BottomBarTab findTab;
    private BottomBarTab selfTab;
    private double firstTime = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar();
        initBottomBar();
        //initRongIM();
        requestPermissions();

    }

    @Override
    public void onStart(){
        super.onStart();
        mBottomBar.setCurrentItem(BaseApp.tabindex);

    }


    /**  private void initRongIM() {
        RongIM.getInstance().getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                homeTab.setUnreadCount(integer);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.PRIVATE);
        RongIM.getInstance().addUnReadMessageCountChangedObserver(MainActivity.this, Conversation.ConversationType.PRIVATE);
    }*/

    //@Override
    public void onCountChanged(int i) {
        homeTab.setUnreadCount(i);
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
       // RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        Timber.e("%s is granted.", permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Timber.d("%s is denied. More info should be provided.", permission.name);
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Timber.e("%s is denied.", permission.name);
                    }
                });


    }


    @Override
    public void loginHuanxinResult() {
        int position = 1;
        showHideFragment(mFragments[position], mFragments[position]);
        BaseApp.tabindex = position;
    }

    private void initBottomBar() {
        Timber.e("initBottomBar main");
        Timber.i("initBottomBar main sdf");
        addFragment();
        homeTab = new BottomBarTab(mContext, R.drawable.home, "首页");
        categoryTab = new BottomBarTab(mContext, R.drawable.chatterbox, "消息");
        cartTab = new BottomBarTab(mContext, R.drawable.search_light, "搜索");
        findTab = new BottomBarTab(mContext, R.drawable.cart_light, "购物车");
        selfTab = new BottomBarTab(mContext, R.drawable.my_light, "我的");
        mBottomBar
                .addItem(homeTab)
                .addItem(categoryTab)
                .addItem(cartTab)
                .addItem(findTab)
                .addItem(selfTab);
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                if (position == 1) {
                    if(LoginUtils.isLogin(MainActivity.this)){
                        if(DemoHelper.getInstance().isLoggedIn()){
                            showHideFragment(mFragments[position], mFragments[prePosition]);
                            BaseApp.tabindex = position;
                        }else{
                            if(mPresenter != null){
                                mPresenter.loginHuanXin(BaseApp.loginBean.getHuanxinId() ,
                                        LoginConfig.HUAMXINPASSWORD);
                            }
                        }
                    }else {
                        LoginUtils.login(MainActivity.this);
                    }

                }else{
                    showHideFragment(mFragments[position], mFragments[prePosition]);
                    BaseApp.tabindex = position;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void addFragment() {
        ISupportFragment recommendFragment = findFragment(RecommendFragment.class);
        if (recommendFragment == null) {
            mFragments[0] = new HomeFragment();
            //            mFragments[1] = new HotLineFragment();
            mFragments[1] = new ConversationListWrapFragment();
//            mFragments[1] = new ConversationListFragment();
            mFragments[2] = new CategoryAllFragment();
            mFragments[3] = new CartHomeFragment();
            mFragments[4] = new SelfFragment();
            loadMultipleRootFragment(R.id.fragment_contain, 0, mFragments);
        } else {
            mFragments[0] = findFragment(HomeFragment.class);
            //            mFragments[1] = findFragment(HotLineFragment.class);
            mFragments[1] = findFragment(ConversationListWrapFragment.class);
            mFragments[2] = findFragment(CategoryAllFragment.class);
            mFragments[3] = findFragment(CartHomeFragment.class);
            mFragments[4] = findFragment(SelfFragment.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }


    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
//                updateUnreadLabel();
//                ISupportFragment fragment = getTopFragment();

                ConversationListWrapFragment fragment = findFragment(ConversationListWrapFragment.class);
                if(fragment != null){
                    ConversationListWrapFragment conversationListWrapFragment = (ConversationListWrapFragment)fragment;
                    conversationListWrapFragment.refresh();
                }
//                if (currentTabIndex == 0) {
//                    // refresh conversation list
//                    if (conversationListFragment != null) {
//                        conversationListFragment.refresh();
//                    }
//                }
            }
        });
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message: messages) {
                DemoHelper.getInstance().getNotifier().vibrateAndPlayTone(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };
    @Override
    public void connectRongIM(TokenBean data) {
       // Timber.i("RongIM token : %s", data.getToken());
        //RongIMUtils.connect(mContext, data.getToken());
    }

    @Override
    public void userInfo(UserBean data) {
        //SpUtils.put(mContext, AppConstant.User.INFO, data);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    @Override
    public void onBackPressedSupport() {
        if (JCVideoPlayer.backPress()) {
            return;
        }

        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ArmsUtils.snackbarText("再按一次退出程序");
            firstTime = secondTime;
        } else {
            ArmsUtils.exitApp();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();


        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);
    }
}
