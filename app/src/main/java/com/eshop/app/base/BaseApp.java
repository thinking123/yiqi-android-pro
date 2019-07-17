package com.eshop.app.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.eshop.app.BugHandler;
import com.eshop.huanxin.DemoHelper;
import com.eshop.mvp.http.entity.auth.MonthData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.category.Category;
import com.eshop.mvp.http.entity.home.CompanyBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.HelpBean;
import com.eshop.mvp.http.entity.store.PublishGoods;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.utils.Util;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.Preconditions;
import com.zkteam.discover.fresco.FrescoInitUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseApp extends Application implements App {
    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";

    private AppDelegate mAppDelegate;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static Context getInstance() {
        return mContext;
    }

    public static List<AppGoodsSection> appGoodsSectionList;

    public static List<AppGoods> appGoodsList;

    public static LoginBean loginBean;

    public static List<AddressBean> addressBeanList;

    public static AddressBean addressBean;

    public static boolean isCartNeedRefresh = false;

    public static boolean isGoodsNeedRefresh = false;

    public static boolean isOrderListNeedRefresh = false;

    public static AuthInfo authInfo;

    public static MonthData monthData;

    public static MonthMsg monthMsg;

    public static PublishGoods publishGoods = new PublishGoods();

    public static CompanyBean companyBean;

    public static String withdraw_type = "2";

    public static List<HelpBean> helpBeanList = new ArrayList<>();

    public static int tabindex = 0;

    public static List<Category> allCategory;

    private void initFrescoConfig() {

        FrescoInitUtil.initFrescoConfig();
    }

    public static String deviceId;

    public static String province = "";
    public static String city = "";

    public static boolean wxAuth = false;

    public static StoreCat storeCat;

    public static StoreState storeState;
    public static String storeStatus;
    public static String storeMsg;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = base;
        if (this.mAppDelegate == null) {
            this.mAppDelegate = new AppDelegate(base);
        }
        this.mAppDelegate.attachBaseContext(base);
    }

    /*
     * init 环信SDK
     * */
    private void initHXConfig(){
        DemoHelper.getInstance().init(this);
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
////        options.setAcceptInvitationAlways(false);
//
//        //初始化
//        EMClient.getInstance().init(this , options);
////在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
    }
    public void onCreate() {
        super.onCreate();
        initHXConfig();
        if (this.mAppDelegate != null) {
            this.mAppDelegate.onCreate(this);
        }

        appGoodsSectionList = new ArrayList<>();

        initFrescoConfig();

        deviceId = Util.getMacAddress(this);

        BugHandler handler = BugHandler.getInstance(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);

    }

    public void onTerminate() {
        super.onTerminate();
        if (this.mAppDelegate != null) {
            this.mAppDelegate.onTerminate(this);
        }

    }

    public static void reset(){
        appGoodsSectionList.clear();
        isCartNeedRefresh = true;
    }

    @NonNull
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(this.mAppDelegate, "%s cannot be null", new Object[]{AppDelegate.class.getName()});
        Preconditions.checkState(this.mAppDelegate instanceof App, "%s must be implements %s", new Object[]{AppDelegate.class.getName(), App.class.getName()});
        return ((App) this.mAppDelegate).getAppComponent();
    }
}
