package com.eshop.app;

import android.content.Context;
import android.util.Log;

public class BugHandler implements Thread.UncaughtExceptionHandler {
    private static BugHandler myCrashHandler;

    private Context mContext;

    private BugHandler(Context context) {
        mContext = context;
    }

    public static synchronized BugHandler getInstance(Context context) {
        if (null == myCrashHandler) {
            myCrashHandler = new BugHandler(context);
        }
        return myCrashHandler;
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        long threadId = thread.getId();
        String message = throwable.getMessage();
        String localizedMessage = throwable.getLocalizedMessage();
        Log.i("BugHandler", "------------------------------------------------------");
        Log.i("BugHandler", "threadId = " + threadId);
        Log.i("BugHandler", "message = " + message);
        Log.i("BugHandler", "localizedMessage = " + localizedMessage);
        Log.i("BugHandler", "------------------------------------------------------");
        throwable.printStackTrace();
        Log.i("BugHandler", "------------------------------------------------------");

        // TODO 下面捕获到异常以后要做的事情，可以重启应用，获取手机信息上传到服务器等
        Log.i("BugHandler", "------------------应用被重启----------------");
        // 重启应用
        mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()));
        //干掉当前的程序
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}