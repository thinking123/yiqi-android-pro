package com.eshop.app.config.applyOptions.interceptor;

import com.eshop.app.GlobalConfiguration;
import com.eshop.app.base.BaseApp;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.SpUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Administrator on 2018/2/2.
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain.request().url().toString().contains("login")
                || chain.request().url().toString().contains("/code/sms")) {
            return chain.proceed(chain.request());
        }
        String token = (String) SpUtils.get(BaseApp.getInstance(), AppConstant.Api.TOKEN, "");
        Timber.i("App-token : %s", token);
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization",
                        "".equals(token) ?
                                "" : GlobalConfiguration.PRE_HEADER_TOKEN + token)
                .build();

        return chain.proceed(request);
    }
}
