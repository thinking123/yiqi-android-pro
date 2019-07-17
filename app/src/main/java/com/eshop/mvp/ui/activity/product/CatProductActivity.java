package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bin.david.dialoglib.BaseDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerCatProductComponent;
import com.eshop.mvp.contract.CatProductContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockCats;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.presenter.CatProductPresenter;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.adapter.SubCatLineQuickAdapter;
import com.eshop.mvp.ui.adapter.SubCatQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.ui.widget.SubCatDialog;
import com.eshop.mvp.ui.widget.SubCatMenu;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**

 */
public class CatProductActivity extends BaseSupportActivity<CatProductPresenter> implements CatProductContract.View, SubCatDialog.OnCheckChangeListener, SubCatMenu.OnCheckChangeListener {

    @Inject
    List<CatBean> catBeans;


    @Inject
    SubCatLineQuickAdapter subCatQuickAdapter;

    @Inject
    List<HomeGoodBean> recommendProductsBeans;

    @Inject
    RecommendQuickAdapter recommendQuickAdapter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.recycler_view_cat)
    RecyclerView mRecyclerView_cat;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.down)
    ImageView down;

    @BindView(R.id.catname)
    TextView catname;


    private int catid = 0;
    private int parent_id = 0;
    private int sub_catid = 0;
    private String goodsName;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    private CatBean selectCatBean;

    SubCatMenu subCatMenu;

    private String title;

    private String parentname;

    @OnClick({R.id.down})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.down:
                /**  BaseDialog.Builder builder =  new  BaseDialog.Builder(this);
                 builder.setGravity(Gravity.TOP);
                 builder.setFillWidth(true).setContentViewBackground(R.drawable.dialog_white_bg).setMargin(0,300,0,0); //设置margin;
                 SubCatDialog subCatDialog = new SubCatDialog(this,builder);
                 subCatDialog.show(this,true,catBeans);*/
                down.setImageResource(R.drawable.jiantoushang);
                catname.setVisibility(View.VISIBLE);
                catname.setText(selectCatBean.categoryName);
                mRecyclerView_cat.setVisibility(View.INVISIBLE);
                subCatMenu = new SubCatMenu(this);
                subCatMenu.show(this, true, catBeans, findViewById(R.id.down));
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCatProductComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cat_product; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        title = getIntent().getStringExtra("name");
        if (title == null) {
            title = "搜索";
        }
        catid = getIntent().getIntExtra("id", 0);
        parent_id = getIntent().getIntExtra("catid", 0);
        parentname = getIntent().getStringExtra("parentname");
        if(parentname==null)parentname="全部";
        goodsName = getIntent().getStringExtra("goodsname");
        toolbarTitle.setText(title);
        toolbarBack.setVisibility(View.VISIBLE);

        initRecycler();
        assert mPresenter != null;

        if (goodsName == null) {

            if (parent_id == 0) {
                mPresenter.getCatProducts(mNextRequestPage, catid + "", null, goodsName);
                mPresenter.getCats(catid);
            } else {
                mPresenter.getCatProducts(mNextRequestPage, null, catid + "", goodsName);
                mPresenter.getCats(parent_id);
            }
        } else {
            mPresenter.getCatProducts(mNextRequestPage, null, null, goodsName);
            down.setVisibility(View.GONE);
        }

    }

    private void initRecycler() {
        Timber.e("initRecycler");
        //swipe_refresh.setOnRefreshListener(() -> mPresenter.getCats());
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        swipe_refresh.setProgressViewOffset(true, 130, 300);
        swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.red));
        mRecyclerView_cat.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        mRecyclerView_cat.addItemDecoration(new RecommendItemDecoration(10));
        recommendQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));
        subCatQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
            selectCatBean = ((CatBean) (adapter.getData()).get(position));
            toolbarTitle.setText(selectCatBean.categoryName);
            if(selectCatBean.categoryName.equalsIgnoreCase("全部")) toolbarTitle.setText(parentname);
            sub_catid = selectCatBean.id;
            int parentid = selectCatBean.parentId;
            setSelected(selectCatBean);
            adapter.notifyDataSetChanged();
            mNextRequestPage = 1;
            if (parentid == 0) {
                mPresenter.getCatProducts(mNextRequestPage, sub_catid + "", null, null);

            } else {
                mPresenter.getCatProducts(mNextRequestPage, null, sub_catid + "", null);

            }

        });

        mRecyclerView_cat.setAdapter(subCatQuickAdapter);

        ///

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

    private void setSelected(CatBean cb) {
        for (CatBean catBean : catBeans) {
            catBean.isselected = false;
        }
        cb.isselected = true;
    }

    private void initSelected() {
        for (CatBean catBean : catBeans) {
            if(catBean.id==this.catid && catBean.parentId!=0){
                catBean.isselected = true;
                selectCatBean = catBean;
            }else  if(catBean.id==this.catid && parent_id==0 )   {
                catBean.isselected = true;
                selectCatBean = catBean;
            }else{
                catBean.isselected = false;
            }
        }

    }

    private void refresh() {
        recommendQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        recommendQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载

        if (goodsName == null) {

            if(selectCatBean!=null){
                if (selectCatBean.parentId == 0) {
                    mPresenter.getCatProducts(mNextRequestPage, selectCatBean.id + "", null, goodsName);

                } else {
                    mPresenter.getCatProducts(mNextRequestPage, null, selectCatBean.id + "", goodsName);

                }
            }else {

                if (parent_id == 0) {
                    mPresenter.getCatProducts(mNextRequestPage, catid + "", null, goodsName);
                    mPresenter.getCats(catid);
                } else {
                    mPresenter.getCatProducts(mNextRequestPage, null, catid + "", goodsName);
                    mPresenter.getCats(parent_id);
                }
            }
        } else {
            mPresenter.getCatProducts(mNextRequestPage, null, null, goodsName);

        }

    }

    private void loadMore() {
        if (mNextRequestPage < pages) {
            if (goodsName == null) {

                if (parent_id == 0) {
                    mPresenter.getCatProducts(mNextRequestPage, catid + "", null, goodsName);
                } else {
                    mPresenter.getCatProducts(mNextRequestPage, null, catid + "", goodsName);

                }
            } else {
                mPresenter.getCatProducts(mNextRequestPage, null, null, goodsName);

            }

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
    public void getCatProductsResult(GoodsBean data) {
        MockGoods.init();

        // recommendProductsBeans.clear();
        // recommendProductsBeans.addAll(MockGoods.goodsList);
        // recommendQuickAdapter.notifyDataSetChanged();

        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh = mNextRequestPage == 1;
        if (isRefresh) {

            recommendQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
        //setData(isRefresh, MockGoods.goodsList);
        setData(isRefresh, data.goodsList);
    }

    @Override
    public void getCatBeanList(List<CatBean> data) {
        //MockCats.init();

        CatBean catBean = new CatBean();
        catBean.categoryName = "全部";
        if(this.parent_id==0)
            catBean.id = this.catid;
        else
            catBean.id = this.parent_id;
        catBean.parentId = 0;
        catBean.isselected = false;
        catBean.categoryIcon = R.drawable.c8 + "";

        catBeans.clear();
        catBeans.add(catBean);
        // catBeans.addAll(MockCats.catsList);
        catBeans.addAll(data);
        subCatQuickAdapter.notifyDataSetChanged();
        initSelected();

    }

    @Override
    public void getCatProductsError(String msg) {
        boolean isRefresh = mNextRequestPage == 1;
        if (isRefresh) {

            recommendQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        } else {
            recommendQuickAdapter.loadMoreFail();
            showMessage(msg);
        }
    }

    @Override
    public void getAllCategoryList(List<Category> data) {

    }

    @Override
    public void onItemClick(CatBean catBean, int position) {
        down.setImageResource(R.drawable.jiantouxia);
        catname.setVisibility(View.GONE);

        mRecyclerView_cat.setVisibility(View.VISIBLE);
        if (position != -1) {
            selectCatBean = catBean;
            toolbarTitle.setText(selectCatBean.categoryName);
            if(selectCatBean.categoryName.equalsIgnoreCase("全部")) toolbarTitle.setText(parentname);

            mRecyclerView_cat.callOnClick();
            sub_catid = catBean.id;
            setSelected(catBean);
            subCatQuickAdapter.notifyDataSetChanged();
           // mNextRequestPage = 1;
           // mPresenter.getCatProducts(mNextRequestPage, null, sub_catid + "", null);

            mNextRequestPage = 1;
            if (catBean.parentId == 0) {
                mPresenter.getCatProducts(mNextRequestPage, sub_catid + "", null, null);

            } else {
                mPresenter.getCatProducts(mNextRequestPage, null, sub_catid + "", null);

            }
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
