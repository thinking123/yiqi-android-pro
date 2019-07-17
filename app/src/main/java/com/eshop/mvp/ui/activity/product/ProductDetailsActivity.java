package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.app.base.LoginConfig;
import com.eshop.di.component.DaggerProductDetailsComponent;
import com.eshop.di.module.ProductDetailsModule;
import com.eshop.huanxin.DemoHelper;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.CarouseBean;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.GoodsId;
import com.eshop.mvp.http.entity.product.MockProductDetail;
import com.eshop.mvp.http.entity.product.Product;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.presenter.ProductDetailsPresenter;
import com.eshop.mvp.ui.activity.EaseChatActivity;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.ui.activity.order.CreateOrderActivity;
import com.eshop.mvp.ui.activity.order.OrderActivity;
import com.eshop.mvp.ui.adapter.GoodsImgQuickAdapter;
import com.eshop.mvp.ui.fragment.AddCartDialogFragment;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.Constant;
import com.eshop.mvp.utils.GlideImageLoader;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.SpUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class ProductDetailsActivity extends BaseSupportActivity<ProductDetailsPresenter> implements ProductDetailsContract.View,AddCartDialogFragment.Listener {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.store_icon)
    ImageView store_icon;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.month_num)
    TextView month_num;
    @BindView(R.id.kuaidi)
    TextView kuaidi;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.store)
    TextView store;
    @BindView(R.id.record)
    TextView record;
    @BindView(R.id.introduce)
    TextView introduce;

    @BindView(R.id.banner_header)
    Banner banner;

    @BindView(R.id.shoucang_icon)
    ImageView shoucang_icon;

    @BindView(R.id.shoucang_txt)
    TextView shoucang_txt;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    GoodsImgQuickAdapter goodsImgQuickAdapter;

    private String token;
    private ProductDetail productDetail;
    private HomeGoodBean homeGoodBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProductDetailsComponent.builder()
                .appComponent(appComponent)
                .productDetailsModule(new ProductDetailsModule(this))
                .build().inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_goods_detail;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("name");
        toolbarTitle.setText(title);
        toolbarBack.setVisibility(View.VISIBLE);
        initBanner();
        getData();
    }

    private void initBanner() {
        banner.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(new ArrayList<>());
        banner.start();
    }

    public void getData() {

         if(LoginUtils.isLogin(this)) {

             token = BaseApp.loginBean.getToken();
         }else{
             token = null;
         }

        homeGoodBean = (HomeGoodBean) getIntent().getSerializableExtra("good");
        assert mPresenter != null;

        mPresenter.getGoodDetail(token, homeGoodBean.id + "");

        title.setText(homeGoodBean.title);
        toolbarTitle.setText(homeGoodBean.title);

        initRecycler();

        // }

    }

    private void initRecycler() {
        List<String> list = new ArrayList<>();
        goodsImgQuickAdapter = new GoodsImgQuickAdapter(list);

        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggered);

        mRecyclerView.setAdapter(goodsImgQuickAdapter);
    }


    @Override
    public void addGoodSuccess() {
        showMessage("加入购物车成功.");
        BaseApp.isCartNeedRefresh = true;
        //BaseApp.tabindex = 3;
        //finish();
    }

    @Override
    public void getGoodDetailSuccess(ProductDetail good) {
        if (good == null) return;

        productDetail = good;
        if (productDetail == null) {
            return;
           // MockProductDetail.init();
           // productDetail = MockProductDetail.productDetail;
        }

        // productDetail.rotationChartList.add(productDetail.rotationChartList.get(0));

        banner.update(productDetail.rotationChartList);

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String url = productDetail.rotationChartList.get(position);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }
        });

        Glide.with(mContext)
                .load(productDetail.storeImg)
                .into(store_icon);
        title.setText(productDetail.title);
        price.setText("¥" + productDetail.unitPrice);
        month_num.setText(productDetail.monthlySalesNum);
        if(productDetail.freightState==1){
            kuaidi.setText(productDetail.freight+"元");
        }else {
            kuaidi.setText(productDetail.freightStateMsg);
        }
        location.setText(productDetail.storeArea);
        store.setText(productDetail.streoName);
        record.setText("收藏" + productDetail.collectionNum + "笔");
        toolbarTitle.setText("产品详情");

        if (productDetail.details != null) {
            introduce.setText(productDetail.details);
        }else{
            introduce.setText("");
        }

        if(productDetail.collectionGoodsState.equalsIgnoreCase("1")){
            //已收藏
            shoucang_icon.setImageResource(R.drawable.shoucang_fill);
            shoucang_txt.setText("已收藏");
        }else{
            shoucang_icon.setImageResource(R.drawable.shoucang);
            shoucang_txt.setText("收藏");
        }

        goodsImgQuickAdapter.setNewData(productDetail.detailMapList);
    }

    @Override
    public void loginHuanxinResult() {
//        int position = 1;
//        showHideFragment(mFragments[position], mFragments[position]);
//        BaseApp.tabindex = position;

        Intent intent2 = new Intent(this, EaseChatActivity.class);
        intent2.putExtra(Constant.EXTRA_USER_ID, productDetail.huanxinId);
        startActivity(intent2);
    }

    @OnClick({R.id.store_bar, R.id.store, R.id.btn_add_cart, R.id.btn_pay, R.id.ll_favorites, R.id.ll_connect_cart , R.id.ll_chat})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_chat:
                if(LoginUtils.isLogin(ProductDetailsActivity.this)){
                    if(DemoHelper.getInstance().isLoggedIn()){
                        DemoHelper.getInstance().saveUser(productDetail.streoName , productDetail.storeImg , productDetail.huanxinId);
                        Intent intent2 = new Intent(this, EaseChatActivity.class);
                        intent2.putExtra(Constant.EXTRA_USER_ID, productDetail.huanxinId);
                        startActivity(intent2);
                    }else{
                        if(mPresenter != null){
                            mPresenter.loginHuanXin(BaseApp.loginBean.getHuanxinId() ,
                                    LoginConfig.HUAMXINPASSWORD);
                        }
                    }
                }else{
                    LoginUtils.login(ProductDetailsActivity.this);
                }

                break;
            case R.id.store_bar:
                Intent intent = new Intent(this, StoreActivity.class);
                intent.putExtra("id", productDetail.storeId + "");
                startActivity(intent);
                break;
            case R.id.btn_add_cart:

                if (LoginUtils.isLogin(this)) {

                    AddCartDialogFragment.newInstance(productDetail.title,productDetail.unitPrice,productDetail.rotationChartList.get(0)).show(getSupportFragmentManager(), "dialog");

                } else {
                    LoginUtils.login(this);
                }

                break;
            case R.id.btn_pay:
                if (LoginUtils.isLogin(this)) {
                    BaseApp.appGoodsList = new ArrayList<>();
                    BaseApp.appGoodsList.add(getAppGoods());
                    Intent intent1 = new Intent(this, CreateOrderActivity.class);
                    intent1.putExtra("from", "pay");
                    startActivity(intent1);
                    finish();
                } else {
                    LoginUtils.login(this);
                }
                break;
            case R.id.ll_favorites:
                if (LoginUtils.isLogin(this)) {
                    if(productDetail.collectionGoodsState.equalsIgnoreCase("0")) {
                        token = BaseApp.loginBean.getToken();
                        GoodsId goodsId = new GoodsId();
                        goodsId.setGoodsId(homeGoodBean.id + "");
                        mPresenter.collectionAddGoods(token, goodsId);
                    }else{
                        new MaterialDialog.Builder(this)
                                .content("取消收藏该商品吗！")
                                .negativeText(R.string.cancel_easy_photos)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        String token = BaseApp.loginBean.getToken();
                                        DelId delId = new DelId();

                                        delId.setDelId(homeGoodBean.id + "");
                                        mPresenter.collectionDel(token,delId);
                                    }
                                })
                                .show();
                    }
                } else {
                    LoginUtils.login(this);
                }

                break;
            case R.id.ll_connect_cart:
                Intent intent1 = new Intent(this, StoreActivity.class);
                intent1.putExtra("id", productDetail.storeId + "");
                startActivity(intent1);
                break;
        }
    }

    private AppGoods getAppGoods() {
        AppGoods appGoods = new AppGoods();
        appGoods.id = productDetail.id;
        appGoods.streoName = productDetail.streoName;
        appGoods.goodNum = 1;
        appGoods.unitPrice = productDetail.unitPrice;
        appGoods.imgUrl = productDetail.rotationChartList.get(0);
        appGoods.storeId = productDetail.storeId;
        appGoods.isHead = true;
        appGoods.isFoot = true;

        appGoods.appClassId = productDetail.appClassId;
        if(appGoods.appClassId==33)appGoods.isMonth = true;
        else appGoods.isMonth =false;
        appGoods.title = productDetail.title;
        appGoods.totalPrice = productDetail.unitPrice;

        if(productDetail.freight!=null && !productDetail.freight.isEmpty()) {
            try {
                appGoods.freight = Double.parseDouble(productDetail.freight);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else appGoods.freight = 0;
        appGoods.freightState = productDetail.freightState;




        return appGoods;

    }

    @Override
    public void collectioGoodsFindResult(GoodsBean data) {

    }

    @Override
    public void collectionStoreFindResult(StoresBean data) {

    }

    @Override
    public void storeColumnResult(StoreCatBean data) {

    }

    @Override
    public void storeGoodsResult(GoodsBean data) {

    }

    @Override
    public void storeIdResult(StoreInfo data) {

    }

    @Override
    public void collectionAddGoodsSuccess() {
        showMessage("收藏商品成功.");
        mPresenter.getGoodDetail(token, homeGoodBean.id + "");

    }

    @Override
    public void collectionAddStoreSuccess() {
        showMessage("收藏店铺成功.");
    }

    @Override
    public void collectionDelSuccess() {
        showMessage("取消收藏成功.");
        mPresenter.getGoodDetail(token, homeGoodBean.id + "");


    }

    @Override
    public void collectionDelStoreSuccess() {

    }

    @Override
    public void updateUserImageSuccess(String data) {

    }

    @Override
    public void getCatBeanList(List<CatBean> data) {

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

    @Override
    public void onItemClicked(int count) {
        if(count==0)count=1;
         token = BaseApp.loginBean.getToken();
         String userId = BaseApp.loginBean.getId() + "";
         mPresenter.addGood(token, userId, homeGoodBean.id + "", count);
    }
}
