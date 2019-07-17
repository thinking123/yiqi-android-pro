package com.eshop.di.component;

import com.eshop.mvp.ui.activity.login.AvatarActivity;
import com.eshop.mvp.ui.activity.login.BindActivity;
import com.eshop.mvp.ui.activity.login.ForgetPasswordActivity;
import com.eshop.mvp.ui.activity.login.SplashActivity;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.ui.activity.set.SetActivity;
import com.eshop.mvp.ui.activity.store.SetPasswordActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.LoginModule;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.ui.activity.login.RegisterActivity;

import dagger.Component;

/**
 * @Author shijun
 * @Data 2019/1/12
 * @Package com.eshop.di.component
 **/
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);

    void inject(RegisterActivity registerActivity);

    void inject(ForgetPasswordActivity forgetPasswordActivity);

    void inject(BindActivity bindActivity);

    void inject(AvatarActivity avatarActivity);

    void inject(UserInfoActivity userInfoActivity);

    void inject(SetActivity setActivity);

    void inject(SetPasswordActivity activity);

    void inject(SplashActivity activity);
}
