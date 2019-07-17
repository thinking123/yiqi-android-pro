package com.eshop.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockCats;
import com.eshop.mvp.http.entity.home.MockGoods;
import com.eshop.mvp.ui.activity.product.CatProductActivity;
import com.eshop.mvp.ui.activity.product.ProductDetailsActivity;
import com.eshop.mvp.ui.adapter.RecommendQuickAdapter;
import com.eshop.mvp.ui.adapter.SubCatQuickAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerCatProductComponent;
import com.eshop.mvp.contract.CatProductContract;
import com.eshop.mvp.presenter.CatProductPresenter;

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
 * Created by MVPArmsTemplate on 01/15/2019 16:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CatProductFragment extends BaseSupportFragment<CatProductPresenter> implements CatProductContract.View {

    @Inject
    List<CatBean> catBeans;

    @Inject
    SubCatQuickAdapter subCatQuickAdapter;

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

    private int catid=0;
    private String catname;

    private int mNextRequestPage = 1;
    private int PAGE_SIZE = 1;
    private int pages = 1;

    public static CatProductFragment newInstance(int id,String name) {
        CatProductFragment fragment = new CatProductFragment();
        fragment.catid = id;
        fragment.catname = name;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCatProductComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cat_product, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycler();
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
        mRecyclerView_cat.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        mRecyclerView_cat.addItemDecoration(new RecommendItemDecoration(10));

        // recommendQuickAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));
        subCatQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(_mActivity, CatProductActivity.class);
                    Bundle extras = new Bundle();
                    if(((CatBean) (adapter.getData()).get(position)).categoryName.equalsIgnoreCase("全部")){
                        extras.putString("parentname","全部");
                        extras.putString("name",catname);
                        extras.putInt("id",
                                catid);
                        extras.putInt("catid",0);

                    }else {
                        extras.putString("parentname",catname);
                        extras.putString("name", ((CatBean) (adapter.getData()).get(position)).categoryName);
                        extras.putInt("id",
                                ((CatBean) (adapter.getData()).get(position)).id);
                        extras.putInt("catid",catid);
                    }

                    intent.putExtras(extras);
                    startActivity(intent);
                }
        );

        mRecyclerView_cat.setAdapter(subCatQuickAdapter);

        ///

        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

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

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;

        mPresenter.getCats(catid);
        mPresenter.getCatProducts(mNextRequestPage,catid+"",null,null);

    }

    private void refresh() {
        recommendQuickAdapter.loadMoreEnd(true);
        mNextRequestPage = 1;
        recommendQuickAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mPresenter.getCats(catid);
        mPresenter.getCatProducts(mNextRequestPage,catid+"",null,null);


    }

    private void loadMore() {
        if(mNextRequestPage<pages){
            mPresenter.getCatProducts(mNextRequestPage,catid+"",null,null);

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

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
        if (data != null && data instanceof Message) {
              switch (((Message) data).what) {
                  case 0:
                      //loadData(((Message) data).arg1);
                      break;
                  case 1:
                      //refreshUI();
                      break;
                  default:
                      //do something
                      break;
              }
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

    }

    @Override
    public void getCatProductsResult(GoodsBean data) {
        MockGoods.init();

       // recommendProductsBeans.clear();
       // recommendProductsBeans.addAll(MockGoods.goodsList);
       // recommendQuickAdapter.notifyDataSetChanged();

        PAGE_SIZE = data.pageUtil.total;
        pages = data.pageUtil.pages;
        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            recommendQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }
       // if(data.goodsList!=null && data.goodsList.size()!=0)
            setData(isRefresh, data.goodsList);
       // else
       //     setData(isRefresh, MockGoods.goodsList);
    }

    @Override
    public void getCatBeanList(List<CatBean> data) {
        //MockCats.init();

        CatBean catBean = new CatBean();
        catBean.categoryName = "全部";
        catBean.id = this.catid;
        catBean.parentId = 0;
        catBean.isselected = true;
        catBean.categoryIcon = R.drawable.c8+"";
        data.add(catBean);

        catBeans.clear();
       // catBeans.addAll(MockCats.catsList);
        catBeans.addAll(data);
        subCatQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCatProductsError(String msg) {
        boolean isRefresh =mNextRequestPage ==1;
        if(isRefresh){

            recommendQuickAdapter.setEnableLoadMore(true);
            swipe_refresh.setRefreshing(false);

        }else{
            recommendQuickAdapter.loadMoreFail();
            showMessage(msg);
        }
    }

    @Override
    public void getAllCategoryList(List<Category> data) {
        BaseApp.allCategory = data;
    }
}
