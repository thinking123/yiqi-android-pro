package com.eshop.mvp.ui.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerHomeComponent;
import com.eshop.di.component.DaggerLoginComponent;
import com.eshop.di.module.LoginModule;
import com.eshop.mvp.contract.HomeContract;
import com.eshop.mvp.contract.LoginContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.CompanyBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.login.UserInfoBean;
import com.eshop.mvp.presenter.HomePresenter;
import com.eshop.mvp.presenter.LoginPresenter;
import com.eshop.mvp.ui.activity.login.ForgetPasswordActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseSupportActivity<HomePresenter> implements HomeContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.version)
    TextView version;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("关于我们");
        toolbarBack.setVisibility(View.VISIBLE);

        mPresenter.getAbout();

    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }






    @Override
    public void getCatBeanList(List<CatBean> data) {

    }

    @Override
    public void getAboutResult(CompanyBean data) {
        BaseApp.companyBean = data;
        setData();

    }

    private void setData(){
        if(BaseApp.companyBean.getLogo()!=null)
        Glide.with(this)
                .load(BaseApp.companyBean.getLogo())//new MultiTransformation(
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                .into(head);

        title.setText(BaseApp.companyBean.getCompanyName());
        version.setText(BaseApp.companyBean.getVersion());
    }

    @OnClick({R.id.about_bar,R.id.tip_bar,R.id.borth_contact})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.about_bar:
                Intent intent = new Intent(AboutActivity.this,HelpDetailActivity.class);
                intent.putExtra("q",BaseApp.companyBean.getAppcompany().get(0).getChName());
                intent.putExtra("a",BaseApp.companyBean.getAppcompany().get(0).getDescribes());
                intent.putExtra("title","");
                startActivity(intent);
                break;

            case R.id.tip_bar:
                Intent intent1 = new Intent(AboutActivity.this,HelpDetailActivity.class);
                intent1.putExtra("q",BaseApp.companyBean.getAppcompany().get(1).getChName());
                intent1.putExtra("a",BaseApp.companyBean.getAppcompany().get(1).getDescribes());
                intent1.putExtra("title","");
                startActivity(intent1);
                break;

            case R.id.borth_contact:
                Intent intent3 = new Intent(AboutActivity.this,HelpDetailActivity.class);
                intent3.putExtra("q",BaseApp.companyBean.getAppcompany().get(2).getChName());
                intent3.putExtra("a",BaseApp.companyBean.getAppcompany().get(2).getDescribes());
                intent3.putExtra("title","");
                startActivity(intent3);
                break;
        }
    }
}
