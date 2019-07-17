package com.eshop.mvp.ui.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.module.HelpModule;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.store.HelpBean;
import com.eshop.mvp.ui.activity.product.ProductDetailsActivity;
import com.eshop.mvp.ui.adapter.AddressQuickAdapter;
import com.eshop.mvp.ui.adapter.HelpQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerHelpComponent;
import com.eshop.mvp.contract.HelpContract;
import com.eshop.mvp.presenter.HelpPresenter;

import com.eshop.R;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/27/2019 22:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HelpActivity extends BaseSupportActivity<HelpPresenter> implements HelpContract.View {

    @Inject
    List<HelpBean> list;

    @Inject
    HelpQuickAdapter helpQuickAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private int mNextRequestPage;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    private String id;

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
        return R.layout.activity_help; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("常见问题");
        initRecycler();
        mPresenter.myHelp();
    }

    private void initRecycler() {
        Timber.e("initRecycler");
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipe_refresh.setProgressViewOffset(true, 130, 300);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

        // storeCatQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));
        helpQuickAdapter.setOnClickHelpItemListener(new HelpQuickAdapter.OnClickHelpItemListener() {
            @Override
            public void onClickItem(HelpBean helpBean) {
                Intent intent = new Intent(HelpActivity.this,HelpDetailActivity.class);
                intent.putExtra("q",helpBean.question);
                intent.putExtra("a",helpBean.answer);
                startActivity(intent);
            }
        });

        helpQuickAdapter.setOnItemClickListener((adapter, view, position) -> {

                }
        );

        helpQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);


        mRecyclerView.setAdapter(helpQuickAdapter);
    }


    private void refresh() {
        helpQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        helpQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mPresenter.myHelp();
    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            mPresenter.myHelp();

        }else{
            helpQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            helpQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                helpQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            helpQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            helpQuickAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (swipe_refresh != null && swipe_refresh.isRefreshing())
            swipe_refresh.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void myHelpResult(List<HelpBean> list) {
        BaseApp.helpBeanList = list;

        setData(true,list);
    }
}
