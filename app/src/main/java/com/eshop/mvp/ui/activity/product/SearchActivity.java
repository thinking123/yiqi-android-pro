package com.eshop.mvp.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerCatProductComponent;
import com.eshop.mvp.contract.CatProductContract;
import com.eshop.mvp.contract.CategoryContract;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.category.Category;
import com.eshop.mvp.http.entity.home.GoodsBean;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.presenter.CatProductPresenter;
import com.eshop.mvp.presenter.CategoryPresenter;
import com.eshop.mvp.ui.adapter.KeywordAdapter;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.mvp.utils.AppConstant;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseSupportActivity<CatProductPresenter> implements CatProductContract.View {


    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_back)
    View toolbar_back;


    private String currentStr = "";

    private String[] mVals = new String[]
            {"Hel", "Android", "Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView dsffsfsfs", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private List<String> list = new ArrayList<>();

    LayoutInflater mInflater;

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
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initHeader();

        initTagFlowLayout();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentStr = s.toString();
            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (event.getAction() == KeyEvent.KEYCODE_SEARCH) {

                return true;
            }
            return false;
        });
    }

    private void initHeader() {
        toolbarTitle.setText("分类搜索");
        toolbar_back.setVisibility(View.VISIBLE);
    }

    private void initTagFlowLayout() {
        mInflater = LayoutInflater.from(this);
        list = LoginUtils.getkeyWord(this);
        mFlowLayout.setAdapter(new TagAdapter<String>(list) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(SearchActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                Toast.makeText(SearchActivity.this, selectPosSet.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    private void refresh(){
        list = LoginUtils.getkeyWord(this);
        if(list.size()!=0) {
            mFlowLayout.setVisibility(View.VISIBLE);
            mFlowLayout.setAdapter(new TagAdapter<String>(list) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                            mFlowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }else{
            mFlowLayout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_finish,R.id.del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                if (TextUtils.isEmpty(currentStr)) {
                    ArmsUtils.snackbarText("搜索内容不能为空");

                    return;
                }
                String replace = currentStr.replace(" ", "");
                replace = replace.trim();
                Intent intent = new Intent(mContext, CatProductActivity.class);
                intent.putExtra("goodsname", replace);
                startActivity(intent);

                LoginUtils.saveKeyWord(this, replace);

                break;

            case R.id.del:
                LoginUtils.clearKeyWord(this);
                refresh();
                break;
        }
    }

    @Override
    public void getCatProductsResult(GoodsBean data) {

    }

    @Override
    public void getCatBeanList(List<CatBean> data) {

    }

    @Override
    public void getCatProductsError(String msg) {

    }

    @Override
    public void getAllCategoryList(List<Category> data) {

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
