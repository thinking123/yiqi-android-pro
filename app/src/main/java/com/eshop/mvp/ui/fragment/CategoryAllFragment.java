package com.eshop.mvp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerCategoryComponent;
import com.eshop.di.module.CategoryModule;
import com.eshop.mvp.contract.CategoryContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.eshop.mvp.presenter.CategoryPresenter;
import com.eshop.mvp.ui.activity.product.CatProductActivity;
import com.eshop.mvp.ui.activity.product.SearchActivity;
import com.eshop.mvp.ui.adapter.CategoryLeftAdapter;
import com.eshop.mvp.ui.adapter.CategoryRightAdapter;
import com.eshop.mvp.ui.adapter.Leve1Adapter;
import com.eshop.mvp.ui.adapter.Level2Adapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zkteam.discover.adapter.DiscoverLeve1Adapter;
import com.zkteam.discover.adapter.DiscoverLevel2Adapter;
import com.zkteam.discover.base.OnExRvItemViewClickListener;
import com.zkteam.discover.bean.DiscoverIndexResult;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.decoration.DiscoverIndexLevel2Decoration;
import com.zkteam.discover.manager.TopSnappedLayoutManager;
import com.zkteam.discover.util.CollectionUtil;
import com.zkteam.discover.util.DimenConstant;
import com.zkteam.discover.util.DiscoverIndexUtil;
import com.zkteam.discover.util.IndexUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryAllFragment extends BaseSupportFragment<CategoryPresenter> implements CategoryContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_back)
    View toolbar_back;

    @BindView(R.id.recycler_left)
    RecyclerView mErvLevel1;
    public Leve1Adapter mLevel1Adapter;
    public LinearLayoutManager mLevel1LayoutMgr;


    @BindView(R.id.recycler_right)
    RecyclerView mErvLevel2;
    public Level2Adapter mLevel2Adapter;
    public TopSnappedLayoutManager mLevel2LayoutMgr;

    @BindView(R.id.rl_search_bar)
    RelativeLayout rlSearchBar;

    @BindView(R.id.head_tv_search)
    TextView head_tv_search;

    private int parentid = 0;

    private float halfHeight = -1.0f;                        // 屏幕一半高度
    private final int DEFAULT_INT = -1024;                   // 默认位置
    private int targetPosition = -1024;                      // 右侧默认位置
    private int mCurrentLeftPos;                             // 当前左侧位置

    public CategoryAllFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCategoryComponent
                .builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_cat, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initHeader();
        initContentView();

    }

    private void initHeader() {
        toolbarTitle.setText("分类搜索");
        toolbar_back.setVisibility(View.GONE);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;
        mPresenter.getAllCategorys();
    }

    @OnClick({R.id.head_tv_search,R.id.rl_search_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_search_bar:
            case R.id.head_tv_search:
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initContentView() {

        initLevel1Views();
        initLevel2Views();

    }

    /**
     * 一级类目
     */
    private void initLevel1Views() {

        mLevel1Adapter = new Leve1Adapter();
        mLevel1Adapter.setOnExRvItemViewClickListener(new OnExRvItemViewClickListener() {
            @Override
            public void onExRvItemViewClick(View view, int dataPos) {

                onListItemLeve1ViewClick(dataPos);
            }
        });

        mLevel1LayoutMgr = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mErvLevel1.setLayoutManager(mLevel1LayoutMgr);
        mErvLevel1.setAdapter(mLevel1Adapter);
        ViewGroup.LayoutParams vglp = mErvLevel1.getLayoutParams();
        vglp.width = (int) (DimenConstant.SCREEN_WIDTH * (0.25));
    }

    /**
     * 二级类目
     */
    private void initLevel2Views() {

        mLevel2Adapter = new Level2Adapter();
        mLevel2Adapter.setOnExRvItemViewClickListener(new OnExRvItemViewClickListener() {
            @Override
            public void onExRvItemViewClick(View view, int dataPos) {

                onRvItemLevel2ViewClick(dataPos);
            }
        });

        mLevel2LayoutMgr = new TopSnappedLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);

        mLevel2LayoutMgr.setRecycleChildrenOnDetach(true);
        mLevel2LayoutMgr.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int dataPos) {

                int type = mLevel2Adapter.getDataItemViewType(dataPos);
                switch (type) {
                    //case Level2Adapter.TYPE_ITEM_BANNER:
                    case Level2Adapter.TYPE_ITEM_TITLE:
                        return 3;
                    default:
                    case Level2Adapter.TYPE_ITEM_WEBVIEW:
                        return 1;
                }
            }
        });
        mErvLevel2.setLayoutManager(mLevel2LayoutMgr);
        mErvLevel2.setItemViewCacheSize(10);
        mErvLevel2.addItemDecoration(new DiscoverIndexLevel2Decoration());
        mErvLevel2.setAdapter(mLevel2Adapter);
        mErvLevel2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    targetPosition = DEFAULT_INT;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (targetPosition == DEFAULT_INT)
                    setLeftSelection();
            }
        });

        RecyclerView.RecycledViewPool recycledViewPool = mErvLevel2.getRecycledViewPool();
        recycledViewPool.setMaxRecycledViews(mLevel2Adapter.TYPE_ITEM_WEBVIEW, 20);
        ViewGroup.LayoutParams vglp = mErvLevel2.getLayoutParams();
        vglp.width = (int) (DimenConstant.SCREEN_WIDTH * (0.75));
    }

    private void invalidateLevel1View(List<Category> categoryList) {

        mLevel1Adapter.setData(categoryList);
        mLevel1Adapter.notifyDataSetChanged();
    }

    private void invalidateLevel2View(List<Category> categoryList) {

        mLevel2Adapter.setData(categoryList);
        mLevel2Adapter.notifyDataSetChanged();
    }

    private void setLeftSelection() {

        try {

            int leftPos = setLeftPosition();
            if (leftPos == -1)
                return;

            if (!mErvLevel2.canScrollVertically(1))
                leftPos = mLevel1Adapter.getDataLastItemPosition();

            if (mLevel1LayoutMgr.findFirstVisibleItemPosition() > leftPos)
                mErvLevel1.smoothScrollToPosition(leftPos);
            else if (mLevel1LayoutMgr.findLastVisibleItemPosition() < leftPos)
                mErvLevel1.smoothScrollToPosition(leftPos);

            if (mCurrentLeftPos != leftPos) {

                // 居中滚动
                scrollListItemLevel1ViewToCenter(leftPos);
                mCurrentLeftPos = leftPos;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private int setLeftPosition() {

        int fristPosition = mLevel2LayoutMgr.findFirstVisibleItemPosition();
        int lastPosition = mLevel2LayoutMgr.findLastVisibleItemPosition();

        if (halfHeight == -1.0f)
            halfHeight = ((float) (mErvLevel2.getHeight()) * 0.5f);

        for (int i = fristPosition; i <= lastPosition; i++) {

            Object obj = mLevel2Adapter.getDataItem(i);
            if (obj instanceof Category) {

                Category category = (Category) obj;
                if (category.isTypeTitle()) {

                    float currentTop = mErvLevel2.getChildAt(i - fristPosition).getTop();

                    if (currentTop > halfHeight)   //小于屏幕一半  切换 Select
                        return category.getParentPosition() - 1;
                    else
                        return category.getParentPosition();
                }
            }
        }
        return -1;
    }

    private void onListItemLeve1ViewClick(int dataPos) {

        // 选中列表Postition位置元素 并居中
        scrollListItemLevel1ViewToCenter(dataPos);
        // 根据Position查找列表对应Item锚点定位到顶部
        level2ListScrollToPosition(dataPos);
    }

    private void onRvItemLevel2ViewClick(int dataPos) {

        Object object = mLevel2Adapter.getDataItem(dataPos);
        if (object instanceof Category) {
            // Toast.makeText(getActivity(), String.format("点击%s", ((Category) object).getName()), Toast.LENGTH_LONG).show();

            Category category = (Category) object;
            Intent intent = new Intent(_mActivity, CatProductActivity.class);
            Bundle extras = new Bundle();
            extras.putString("name", category.getName());
            extras.putInt("id",
                    Integer.parseInt(category.getId()));
            extras.putInt("catid", Integer.parseInt(category.getParentId()));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    private void scrollListItemLevel1ViewToCenter(int position) {

        try {

            View childAt = mErvLevel1.getChildAt(position - mLevel1LayoutMgr.findFirstVisibleItemPosition());
            if (childAt != null) {

                int y = (childAt.getTop() - mErvLevel1.getHeight() / 2);
                mErvLevel1.smoothScrollBy(0, y);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

            mLevel1Adapter.setSelectPos(position);
        }
    }

    private void level2ListScrollToPosition(int dataPos) {

        Object obj = mLevel1Adapter.getDataItem(dataPos);
        if (obj instanceof Category) {

            Category category = (Category) obj;
            targetPosition = mLevel2Adapter.getSelectPosition(Integer.parseInt(category.getId()));
            if (targetPosition > -1)
                mErvLevel2.smoothScrollToPosition(targetPosition);
        }
    }

    private void initlidateContent(List<Category> data) {

        List<Category> list = IndexUtil.merageOperLevel2List(data);

        if (CollectionUtil.isEmpty(list))
            return;

        invalidateLevel1View(data);
        invalidateLevel2View(list);

    }


    @Override
    public void getCategoryBeanList(List<CatBean> data) {

    }

    @Override
    public void getItemCategoryBeanList(List<CatBean> data) {

    }

    @Override
    public void getAllCategoryList(List<Category> data) {
        BaseApp.allCategory = data;
        initlidateContent(data);
    }

    @Override
    public void setData(@Nullable Object data) {

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


}
