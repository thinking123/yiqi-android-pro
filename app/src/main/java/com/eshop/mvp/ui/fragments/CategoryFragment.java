package com.eshop.mvp.ui.fragments;


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

import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.eshop.mvp.ui.activity.product.CatProductActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerCategoryComponent;
import com.eshop.di.module.CategoryModule;
import com.eshop.mvp.contract.CategoryContract;
import com.eshop.mvp.http.entity.category.CategoryBean;
import com.eshop.mvp.presenter.CategoryPresenter;
import com.eshop.mvp.ui.activity.product.SearchProductListActivity;
import com.eshop.mvp.ui.adapter.CategoryLeftAdapter;
import com.eshop.mvp.ui.adapter.CategoryRightAdapter;
import com.eshop.mvp.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseSupportFragment<CategoryPresenter> implements CategoryContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_back)
    View toolbar_back;

    @BindView(R.id.recycler_left)
    RecyclerView recyclerLeft;
    @BindView(R.id.recycler_right)
    RecyclerView recyclerRight;
    @Inject
    CategoryLeftAdapter categoryLeftAdapter;
    @Inject
    CategoryRightAdapter categoryRightAdapter;

    @BindView(R.id.subtitle)
    TextView subtitle;

    @BindView(R.id.rl_search_bar)
    RelativeLayout rlSearchBar;

    @BindView(R.id.head_tv_search)
    EditText head_tv_search;

    private int parentid = 0;

    public CategoryFragment() {
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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initHeader();
        initLeftRecycler();
        initRightRecycler();
    }

    private void initHeader() {
        toolbarTitle.setText("分类搜索");
        toolbar_back.setVisibility(View.GONE);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;
        mPresenter.getCategorys(0);
    }

    private void initRightRecycler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 3);
        recyclerRight.setLayoutManager(gridLayoutManager);
        recyclerRight.setAdapter(categoryRightAdapter);
        categoryRightAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));

        categoryRightAdapter.setOnItemClickListener((adapter, view, position) -> {
            CatBean categoryBean = (CatBean) adapter.getData().get(position);
            Intent intent = new Intent(_mActivity, CatProductActivity.class);
            Bundle extras = new Bundle();
            extras.putString("name",((CatBean) (adapter.getData()).get(position)).categoryName);
            extras.putInt("id",
                    ((CatBean) (adapter.getData()).get(position)).id);
            extras.putInt("catid",parentid);
            intent.putExtras(extras);
            startActivity(intent);
          /**  Intent intent = new Intent(_mActivity, SearchProductListActivity.class);
            intent.putExtra(AppConstant.ActivityIntent.SEARCH_CONTENT, categoryBean.categoryName);
            _mActivity.startActivity(intent);*/
        });
    }

    private void initLeftRecycler() {
        recyclerLeft.setLayoutManager(new LinearLayoutManager(_mActivity));
        categoryLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            categoryLeftAdapter.setSelectedPosition(position);
            subtitle.setText(((CategoryLeftAdapter) adapter).getData().get(position).categoryName);
            categoryRightAdapter.setNewData(new ArrayList<>());
            if (mPresenter != null) {
                parentid = ((CategoryLeftAdapter) adapter).getData().get(position).id;
                mPresenter.getCategorys(parentid);
            }
        });
        recyclerLeft.setAdapter(categoryLeftAdapter);
        categoryLeftAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty, null));

    }

    @Override
    public void getCategoryBeanList(List<CatBean> data) {
        categoryLeftAdapter.replaceData(data);
        categoryLeftAdapter.setSelectedPosition(0);
        parentid = categoryLeftAdapter.getData().get(0).id;
        subtitle.setText(categoryLeftAdapter.getData().get(0).categoryName);
        if (data.size() != 0) {
            if (mPresenter != null) {
                mPresenter.getCategorys(data.get(0).id);
            }
        }
    }

    @Override
    public void getItemCategoryBeanList(List<CatBean> data) {
        categoryRightAdapter.setNewData(data);
    }

    @Override
    public void getAllCategoryList(List<Category> data) {

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
