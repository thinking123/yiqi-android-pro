package com.eshop.mvp.ui.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bin.david.dialoglib.BaseDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.mvp.http.entity.home.AdBean;
import com.eshop.mvp.http.entity.home.CarouseBean;
import com.eshop.mvp.http.entity.home.Const;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.ui.activity.BrandActivity;
import com.eshop.mvp.ui.activity.product.GoodsDetailActivity;
import com.eshop.mvp.ui.activity.product.ProductDetailsActivity;
import com.eshop.mvp.ui.activity.product.ProductListActivity;
import com.eshop.mvp.ui.activity.product.SaleFlashActivity;

import com.eshop.mvp.ui.activity.product.StoreActivity;
import com.eshop.mvp.ui.widget.AdDialog;
import com.jess.arms.di.component.AppComponent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.eshop.R;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerRecommendComponent;
import com.eshop.di.module.RecommendModule;
import com.eshop.mvp.contract.RecommendContract;
import com.eshop.mvp.presenter.RecommendPresenter;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.utils.GlideImageLoader;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
//import io.rong.imkit.RongIM;
//import io.rong.imkit.manager.IUnReadMessageObserver;
//import io.rong.imlib.RongIMClient;
//import io.rong.imlib.model.Conversation;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseSupportFragment<RecommendPresenter> implements RecommendContract.View, AdDialog.OnChangeListener {//, IUnReadMessageObserver {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.banner_header)
    Banner banner;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @Inject
    List<HomeGoodBean> recommendProductsBeans;

    @Inject
    RecommendQuickAdapter recommendQuickAdapter;


    private View top;
    private View view_video;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    private AdBean adBean;

    private HomeBean homeBean;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRecommendComponent
                .builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycler();
        initBanner();
        initAppLayout();

    }

    private void initBar(HomeBean data){


        ImageView icon1 = top.findViewById(R.id.icon1);
        ImageView icon2 = top.findViewById(R.id.icon2);
        ImageView icon3 = top.findViewById(R.id.icon3);
        ImageView icon4 = top.findViewById(R.id.icon4);
        ImageView icon5 = top.findViewById(R.id.icon5);

        TextView title1 = top.findViewById(R.id.title1);
        TextView title2 = top.findViewById(R.id.title2);
        TextView title3 = top.findViewById(R.id.title3);
        TextView title4 = top.findViewById(R.id.title4);
        TextView title5 = top.findViewById(R.id.title5);

        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("title","月结商品");
                intent.putExtra("id", Const.MONTH_ID);
                startActivity(intent);
            }
        });

        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SaleFlashActivity.class);
                intent.putExtra("title","7号特卖");
                intent.putExtra("id", Const.SALE7_ID);
                startActivity(intent);
            }
        });

        icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BrandActivity.class));
            }
        });

        icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("title","秒秒购半价");
                intent.putExtra("id", Const.MIAOMIAOGOU_ID);
                startActivity(intent);
            }
        });

        icon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra("title","12.5秒杀");
                intent.putExtra("id", Const.SALE_ID);
                startActivity(intent);
            }
        });

        for(int i=0;i<data.navigationBar.size();i++){
            if(i==0){
                String url = data.navigationBar.get(0).navigationImgUrl;
                Glide.with(this)
                        //.load(R.drawable.b1)
                        .load(url)
                        .into(icon1);
                title1.setText(data.navigationBar.get(0).categoryName);
            }

            if(i==1){
                String url = data.navigationBar.get(1).navigationImgUrl;
                Glide.with(this)
                        //.load(R.drawable.b2)
                        .load(url)
                        .into(icon2);
                title2.setText(data.navigationBar.get(1).categoryName);
            }

            if(i==2){
                String url = data.navigationBar.get(2).navigationImgUrl;
                Glide.with(this)
                        //.load(R.drawable.b3)
                        .load(url)
                        .into(icon3);
                title3.setText(data.navigationBar.get(2).categoryName);
            }

            if(i==3){
                String url = data.navigationBar.get(3).navigationImgUrl;
                Glide.with(this)
                        //.load(R.drawable.b4)
                        .load(url)
                        .into(icon4);
                title4.setText(data.navigationBar.get(3).categoryName);
            }

            if(i==4){
                String url = data.navigationBar.get(4).navigationImgUrl;
                Glide.with(this)
                        //.load(R.drawable.b5)
                        .load(url)
                        .into(icon5);
                title5.setText(data.navigationBar.get(4).categoryName);
            }
        }

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;
        mPresenter.getGoodsData(1);
        mPresenter.getHomeData();
        mPresenter.getAdData();
    }

    private void initAppLayout() {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                swipe_refresh.setEnabled(true);
            } else {
                swipe_refresh.setEnabled(false);
            }
            //mToolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.white), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
        });


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
        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));
        top = getLayoutInflater().inflate(R.layout.view_list_bar, (ViewGroup) mRecyclerView.getParent(), false);
        view_video = getLayoutInflater().inflate(R.layout.view_video, (ViewGroup) mRecyclerView.getParent(), false);
        View subtitle = getLayoutInflater().inflate(R.layout.view_subtitle, (ViewGroup) mRecyclerView.getParent(), false);
        recommendQuickAdapter.addHeaderView(top);
        recommendQuickAdapter.addHeaderView(view_video);
        recommendQuickAdapter.addHeaderView(subtitle);
        recommendQuickAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
        recommendQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(_mActivity, ProductDetailsActivity.class);
                    intent.putExtra("good",((HomeGoodBean)adapter.getItem(position)));
                    startActivity(intent);
                }
        );

        recommendQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);

        mRecyclerView.setAdapter(recommendQuickAdapter);
    }

    private void initBanner() {
        banner.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if(homeBean!=null) {
                    String url = homeBean.carouselList.get(position).jumpUrl;

                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                }
            }
        });
        banner.setImages(new ArrayList<>());
        banner.start();
    }

    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void refresh() {
        recommendQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        recommendQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mPresenter.getGoodsData(mNextRequestPage);
        mPresenter.getHomeData();

    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            mPresenter.getGoodsData(mNextRequestPage);

        }else{
            recommendQuickAdapter.loadMoreEnd();
        }

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            recommendQuickAdapter.setNewData(data);
        } else {
            if (size > 0) {
                recommendQuickAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            recommendQuickAdapter.loadMoreEnd(isRefresh);
            //Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            recommendQuickAdapter.loadMoreComplete();
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

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    @Override
    public void getHomeDataResult(HomeBean data) {
        homeBean = data;
        List<String> recommendImages=new ArrayList<>();
        for ( CarouseBean car : data.carouselList) {
            recommendImages.add(car.imgurl);

            //recommendImages.add("http://www.sitcamp.com/ad1.png");
            //recommendImages.add("http://www.sitcamp.com/ad1.png");
        }
       // recommendImages.add("http://www.sitcamp.com/ad1.png");
       // recommendImages.add("http://www.sitcamp.com/ad1.png");
        banner.update(recommendImages);

        initBar(data);

        initVideo(data);
    }

    @Override
    public void getGoodsDataResult(GoodsBean data) {

       // MockGoods.init();

        if(data==null)return;



        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            recommendQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        //setData(isRefresh, MockGoods.goodsList);
        setData(isRefresh, data.goodsList);

    }

    @Override
    public void getGoodsDataError(String msg) {

    }

    @Override
    public void getAdDataResult(AdBean data) {

        this.adBean = data;

        if(adBean.jumpState!=3) {

            BaseDialog.Builder builder = new BaseDialog.Builder(getActivity());
            builder.setGravity(Gravity.CENTER);
            builder.setFillWidth(true).
                    setFillHeight(true).
                    setContentViewBackground(android.R.drawable.screen_background_dark_transparent)
                    .setMargin(0, 0, 0, 0); //设置margin;
            AdDialog adDialog = new AdDialog(this, builder);
            adDialog.show(getActivity(), data.imgurl, data.productId);
        }

    }

    private void initVideo(HomeBean data){
        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) view_video.findViewById(R.id.jc_video);

        jcVideoPlayerStandard.setUp(data.vadioUrl
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
        String url = "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640";

        if(data.firstFrame!=null)url = data.firstFrame;

        Glide.with(this)
                .load(url)
                .into(jcVideoPlayerStandard.thumbImageView);

    }


    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onItemClick(String id, boolean isclose) {

        if(adBean.jumpState==0) {
            Intent intent= new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(adBean.jumpUrl);
            intent.setData(content_url);
            startActivity(intent);
        }else if(adBean.jumpState==1) {
            HomeGoodBean goods = new HomeGoodBean();
            goods.id = 26;//Integer.parseInt(adBean.productId);

            Intent intent = new Intent(_mActivity, ProductDetailsActivity.class);
            intent.putExtra("good",goods);
            startActivity(intent);
        }
    }
}
