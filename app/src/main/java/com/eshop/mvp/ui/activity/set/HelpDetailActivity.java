package com.eshop.mvp.ui.activity.set;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerHelpComponent;
import com.eshop.di.module.HelpModule;
import com.eshop.mvp.contract.HelpContract;
import com.eshop.mvp.http.entity.store.HelpBean;
import com.eshop.mvp.presenter.HelpPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import butterknife.BindView;

public class HelpDetailActivity extends BaseSupportActivity<HelpPresenter> implements HelpContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.title_top)
    TextView title_top;
    @BindView(R.id.content_sub)
    TextView content_sub;

    private String Title = "";


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHelpComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .helpModule(new HelpModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("常见问题");
        String title = getIntent().getStringExtra("q");
        String content = getIntent().getStringExtra("a");
        Title = getIntent().getStringExtra("title");
        setData(title,content);

    }


    private void setData(String title_txt,String content_txt){
        title_top.setText(title_txt);
        content_sub.setText(content_txt);
        if(Title!=null)
        toolbarTitle.setText(Title);
    }

    @Override
    public void myHelpResult(List<HelpBean> list) {

    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
