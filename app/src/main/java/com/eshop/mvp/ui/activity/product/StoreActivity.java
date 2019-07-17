package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bin.david.dialoglib.BaseDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerProductDetailsComponent;
import com.eshop.di.component.DaggerStoreComponent;
import com.eshop.di.module.ProductDetailsModule;
import com.eshop.mvp.contract.ProductDetailsContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.Const;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.http.entity.product.DelId;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.product.StoreId;
import com.eshop.mvp.http.entity.product.StoreInfo;
import com.eshop.mvp.http.entity.product.StoreLogo;
import com.eshop.mvp.http.entity.product.StoresBean;
import com.eshop.mvp.presenter.ProductDetailsPresenter;
import com.eshop.mvp.ui.activity.login.LoginActivity;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.ui.widget.SubCatDialog;
import com.eshop.mvp.utils.AppConstant;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.eshop.mvp.utils.SpUtils;
import com.eshop.mvp.utils.ViewUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.SimpleSpinnerTextFormatter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Avatar;
import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;

public class StoreActivity extends BaseSupportActivity<ProductDetailsPresenter> implements ProductDetailsContract.View {

    @Inject
    List<HomeGoodBean> recommendProductsBeans;

    @Inject
    RecommendQuickAdapter recommendQuickAdapter;


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.fl_layout)
    View mFLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.top_image)
    ImageView top_image;

    @BindView(R.id.head)
    ImageView head;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.count)
    TextView count;

    @BindView(R.id.btn_shoucang)
    TextView btn_shoucang;

//    @BindView(R.id.nice_spinner_cat)
//    NiceSpinner nice_spinner_cat;
//
//    @BindView(R.id.nice_spinner_price)
//    NiceSpinner nice_spinner_price;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private boolean isstore = false;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    /**
     * 店铺id
     */
    private String storeId;
    /**
     * 店铺类目id
     */
    private String storeColumnId;

    private StoreInfo storeInfo;

    public static final int REQUEST_CAT = 99;

    private List<StoreCat> storeColumns;

    private List<String> priceList;

    /**
     * 排序方式，价格正序asc, 价格倒序desc, 默认时间倒序
     */
    private String sorttype;

    private String token;

    private PicChooserHelper picChooserHelper;
    private String head_url;

    private boolean isHead = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreComponent.builder()
                .appComponent(appComponent)
                .productDetailsModule(new ProductDetailsModule(this))
                .build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scrolling;
    }

    class ViewHolder {
        TextView textView;

        ViewHolder(View tabView) {
            textView = (TextView) tabView.findViewById(R.id.text);
        }
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout,6,6);
            }
        });

        List<String> tabs = new ArrayList<String>();
        tabs.add("全部");
        tabs.add("价格");
        for (int i = 0; i < tabs.size(); i++) {
            //获取tab
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //给tab设置自定义布局
            tab.setCustomView(R.layout.table_item_custom);
            ViewHolder holder = new ViewHolder(tab.getCustomView());

            holder.textView.setText(tabs.get(i));
            //默认选择第一项
            if (i == 0) {
                holder.textView.setSelected(true);
                holder.textView.setTextSize(18);
                holder.textView.setTextColor(ContextCompat.getColor(StoreActivity.this , R.color.black));
            }
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                ViewHolder holder = new ViewHolder(tab.getCustomView());
                holder.textView.setSelected(true);
                holder.textView.setTextSize(18);
                holder.textView.setTextColor(ContextCompat.getColor(StoreActivity.this , R.color.black));
//                if(tabLayout == null)return;
//                LinearLayout tabLayout = (LinearLayout)((ViewGroup) StoreActivity.this.tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
//                TextView tabTextView = (TextView) tabLayout.getChildAt(1);
//                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.BOLD);



//                tabTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP , 60);
//                tabTextView.setTextSize(32);

//                request(storeId, storeColumnId, sorttype);
                if(tab.getPosition() == 0){
//                    storeId = "0" ;
                    storeColumnId = null ;
                    sorttype = null;
                }else{
                    storeColumnId = null ;
                    sorttype = "desc";
                }
                refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHolder holder = new ViewHolder(tab.getCustomView());
                holder.textView.setSelected(false);
                //恢复默认字体大小
                holder.textView.setTextSize(12);
                holder.textView.setTextColor(ContextCompat.getColor(StoreActivity.this , R.color.gray));
//                if(tabLayout == null)return;
//                LinearLayout tabLayout = (LinearLayout)((ViewGroup) StoreActivity.this.tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
//                TextView tabTextView = (TextView) tabLayout.getChildAt(1);
//                tabTextView.setTypeface(tabTextView.getTypeface(), Typeface.NORMAL);
//                tabTextView.setTextSize(22);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ViewUtils.setImmersionStateMode(this);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());

                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();

                int dy = Math.abs(verticalOffset);

                if (storeInfo != null) {
                    toolbarTitle.setText(storeInfo.streoName);


                }

                if (dy <= toolbarHeight) {
                    float scale = (float) dy / toolbarHeight;
                    float alpha = scale * 255;
                    mFLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    toolbarTitle.setTextColor(Color.argb((int) alpha, 0, 0, 0));

                } else {

                }

                //第二种

                // mFLayout.setAlpha(percent);


            }
        });

        initRecycler();

        set_cat();

        storeId = getIntent().getStringExtra("id");
        isstore = getIntent().getBooleanExtra("isstore", false);
        if (isstore) btn_shoucang.setVisibility(View.GONE);
        if (LoginUtils.isLogin(this)) {
           token = BaseApp.loginBean.getToken();
        }
            mPresenter.storeId(token,storeId);
        request(storeId, storeColumnId, sorttype);

        mPresenter.storeColumn(1, storeId);
    }

    public void setIndicator (TabLayout tabs, int leftDip, int rightDip){
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);

            child.invalidate();
        }
    }

    private void set_cat() {
        priceList = new ArrayList<>();
        priceList.add("价格(默认)");
        priceList.add("价格(低到高)");
        priceList.add("价格(高到低)");

//        nice_spinner_price.attachDataSource(priceList);
//        nice_spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                StoreCat storeCat = storeColumns.get(position);
//
//                storeColumnId = storeCat.id + "";
//                if (storeColumnId.equalsIgnoreCase("0")) storeColumnId = null;
//                refresh();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        nice_spinner_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if(position==0){
//                    sorttype = null;
//                }else if(position==1){
//                    sorttype = "asc";
//                }else{
//                    sorttype = "desc";
//                }
//
//                refresh();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void request(String storeId, String storeColumnId, String sorttype) {
        assert mPresenter != null;

        mPresenter.storeGoods(mNextRequestPage, storeId, storeColumnId, sorttype);
    }

    @OnClick({R.id.btn_shoucang, R.id.cat, R.id.store,R.id.top_image,R.id.head})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.top_image:
                isHead = false;
                if(isstore)uploadImage();
                break;
            case R.id.head:
                isHead = true;
                if(isstore)uploadImage();
                break;
            case R.id.btn_shoucang:

                if (LoginUtils.isLogin(this)) {

                    String token = BaseApp.loginBean.getToken();

                    StoreId storeId_bean = new StoreId();
                    storeId_bean.setStoreId(storeId);

                    if(storeInfo.collectionState.equalsIgnoreCase("0")){
                        new MaterialDialog.Builder(this)
                                .content("取消关注该店铺吗！")
                                .negativeText(R.string.cancel_easy_photos)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        String token = BaseApp.loginBean.getToken();
                                        DelId delId = new DelId();

                                        delId.setDelId(storeId);
                                        mPresenter.collectionDelStore(token,delId);
                                    }
                                })
                                .show();
                    }else {

                        mPresenter.collectionAddStore(token, storeId_bean);
                    }
                } else {
                    LoginUtils.login(this);
                }

                break;
            case R.id.cat:
                Intent intent1 = new Intent(this, StoreCatActivity.class);
                intent1.putExtra("id", storeId);
                intent1.putExtra("type", "StoreActivity");
                startActivityForResult(intent1, REQUEST_CAT);
                break;
            case R.id.store:
                Intent intent = new Intent(this, StoreIntroduceActivity.class);
                intent.putExtra("id", storeId);
                startActivity(intent);
                break;
        }
    }

    private void initRecycler() {
        Timber.e("initRecycler");
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipe_refresh.setProgressViewOffset(true, 2, 100);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

        recommendQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));
        recommendQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(this, ProductDetailsActivity.class);
                    intent.putExtra("good", ((HomeGoodBean) adapter.getItem(position)));
                    startActivity(intent);
                }
        );

        recommendQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(recommendQuickAdapter);
    }

    private void refresh() {
        recommendQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        recommendQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        request(storeId, storeColumnId, sorttype);
    }

    private void loadMore() {
        if (mNextRequestPage < pages) {
            request(storeId, storeColumnId, sorttype);

        } else {
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

    private void updateData(GoodsBean data) {
        MockGoods.init();

        if (data != null) {
            if (data.pageUtil != null)
                PAGE_SIZE = data.pageUtil.total;
            if (data.pageUtil != null)
                pages = data.pageUtil.pages;
        }

        boolean isRefresh = mNextRequestPage == 1;
        if (isRefresh) {

            recommendQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        // setData(isRefresh, MockGoods.goodsList);
        setData(isRefresh, data.goodsList);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode == REQUEST_CAT) {
            if (responseCode == RESULT_OK) {
                storeColumnId = data.getStringExtra("id");
                if (storeColumnId.equalsIgnoreCase("0")) storeColumnId = null;
                refresh();
            }
        }

        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, responseCode, data);
    }


    @Override
    public void storeGoodsResult(GoodsBean data) {
        updateData(data);
    }

    @Override
    public void addGoodSuccess() {

    }

    @Override
    public void getGoodDetailSuccess(ProductDetail good) {

    }

    @Override
    public void collectioGoodsFindResult(GoodsBean data) {

    }

    @Override
    public void collectionStoreFindResult(StoresBean data) {

    }

    @Override
    public void storeColumnResult(StoreCatBean data) {

        SimpleSpinnerTextFormatter textFormatter = new SimpleSpinnerTextFormatter() {
            @Override
            public Spannable format(Object item) {
                StoreCat storeCat = (StoreCat) item;
                return new SpannableString(storeCat.categoryName);
            }
        };

//        nice_spinner_cat.setSpinnerTextFormatter(textFormatter);
//        nice_spinner_cat.setSelectedTextFormatter(textFormatter);

        StoreCat storeCat = new StoreCat();
        storeCat.categoryName = "全部";
        storeCat.id = 0;

        data.storeColumns.add(0, storeCat);

//        nice_spinner_cat.attachDataSource(data.storeColumns);

        storeColumns = data.storeColumns;

    }


    @Override
    public void storeIdResult(StoreInfo data) {
        storeInfo = data;
        if (storeInfo != null) {
            toolbarTitle.setText(storeInfo.streoName);
            title.setText(storeInfo.streoName);
            count.setText("商品" + storeInfo.goodsCountNum + "件");
            if(data.collectionState.equalsIgnoreCase("0")){
                this.btn_shoucang.setText("已关注");
            }else{
                this.btn_shoucang.setText("关注");
            }

            if (storeInfo.storeImg != null)
                Glide.with(mContext)
                        .load(storeInfo.storeImg)
                        .into(head);

            if (storeInfo.background != null)
                Glide.with(mContext)
                        .load(storeInfo.background)
                        .into(top_image);
        }

    }

    @Override
    public void collectionAddGoodsSuccess() {
        showMessage("收藏商品成功.");
    }

    @Override
    public void collectionAddStoreSuccess() {
        //showMessage("关注店铺成功.");
        mPresenter.storeId(token,storeId);
    }

    @Override
    public void collectionDelSuccess() {

    }

    @Override
    public void collectionDelStoreSuccess() {
        mPresenter.storeId(token,storeId);
    }

    @Override
    public void updateUserImageSuccess(String url) {
        if(LoginUtils.isLogin(this)) {
            if (mPresenter != null) {
                head_url = url;
                String id = BaseApp.loginBean.getId() + "";
                StoreLogo storeLogo = new StoreLogo();
                if(isHead) {
                    storeLogo.setLogoImg(url);
                    storeLogo.setStoreId(storeId);
                    storeLogo.setBackground(storeInfo.background);
                    mPresenter.storeLogoPut(BaseApp.loginBean.getToken(),storeLogo);
                    if(head_url!=null) {
                        Glide.with(this)
                                .load(head_url)//new MultiTransformation(
                                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                                .into(head);
                    }
                }else {
                    storeLogo.setBackground(url);
                    storeLogo.setLogoImg(storeInfo.storeImg);
                    storeLogo.setStoreId(storeId);
                    mPresenter.storeLogoPut(BaseApp.loginBean.getToken(),storeLogo);
                    if(head_url!=null)
                        Glide.with(this)
                                .load(head_url)//new MultiTransformation(
                               // .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                                .into(top_image);
                }
            }
        }
    }

    @Override
    public void getCatBeanList(List<CatBean> data) {

    }

    @Override
    public void loginHuanxinResult() {

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
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    public void uploadImage() {
        if(isHead) {
            picChooserHelper = new PicChooserHelper(this, Avatar);
            picChooserHelper.setOnChooseResultListener(url -> {
                if (mPresenter != null) {
                    mPresenter.updateUserImage(url);
                }
            });
        }else{
            picChooserHelper = new PicChooserHelper(this, Cover);
            picChooserHelper.setOnChooseResultListener(url -> {
                if (mPresenter != null) {
                    mPresenter.updateUserImage(url);
                }
            });
        }

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(StoreActivity.this)
                .title("图片上传")
                .negativeText(R.string.cancel_easy_photos)
                .inputType(InputType.TYPE_CLASS_TEXT )
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                picChooserHelper.takePicFromCamera();
                                break;
                            case 1:
                                picChooserHelper.takePicFromAlbum();
                                break;
                        }}
                }).show();

    }


}
