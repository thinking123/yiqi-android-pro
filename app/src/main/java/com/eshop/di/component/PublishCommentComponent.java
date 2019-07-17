package com.eshop.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.eshop.di.module.PublishCommentModule;
import com.eshop.mvp.ui.activity.comment.PublishCommentActivity;

import dagger.Component;

/**
 * @Author 张迁-zhangqian
 * @Data 2018/6/10 下午10:34
 * @Package com.eshop.di.component
 **/
@ActivityScope
@Component(modules = PublishCommentModule.class, dependencies = AppComponent.class)
public interface PublishCommentComponent {
    void inject(PublishCommentActivity publishCommentActivity);
}
