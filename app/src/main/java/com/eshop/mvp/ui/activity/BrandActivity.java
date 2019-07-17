package com.eshop.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.module.BrandModule;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.home.BrandBean;
import com.eshop.mvp.http.entity.home.BrandBeanList;
import com.eshop.mvp.http.entity.home.BrandBeanSection;
import com.eshop.mvp.http.entity.home.Const;
import com.eshop.mvp.http.entity.home.MockBrandBean;
import com.eshop.mvp.ui.activity.product.ProductListActivity;
import com.eshop.mvp.ui.adapter.AppCartSectionAdapter;
import com.eshop.mvp.ui.adapter.BrandAdapter;
import com.eshop.mvp.ui.adapter.BrandBeanSectionAdapter;
import com.eshop.mvp.ui.fragments.CartFragment;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.ui.widget.TitleItemDecoration;
import com.eshop.mvp.utils.PinyinComparator;
import com.eshop.mvp.utils.PinyinUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerBrandComponent;
import com.eshop.mvp.contract.BrandContract;
import com.eshop.mvp.presenter.BrandPresenter;

import com.eshop.R;
import com.xp.wavesidebar.WaveSideBar;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2019 17:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BrandActivity extends BaseSupportActivity<BrandPresenter> implements BrandContract.View {

    @Inject
    BrandBeanSectionAdapter brandBeanSectionAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.num)
    TextView num;


    // @BindView(R.id.sideBar)
    private WaveSideBar mSideBar;

  //  private BrandAdapter mAdapter;

    //private GridLayoutManager manager;
    private LinearLayoutManager manager;

    private List<BrandBean> mDateList;

    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator mComparator;

    private TitleItemDecoration mDecoration;

    private String type = "brand";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBrandComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .brandModule(new BrandModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_brand; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.getBrand();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolBar();
        initRecyclerView();
        type = getIntent().getStringExtra("type");
        if(type==null)type = "brand";
      /**  mComparator = new PinyinComparator();

        mSideBar = (WaveSideBar) findViewById(R.id.sideBar);

        //设置右侧SideBar触摸监听
        mSideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });


        mDateList = filledData(getResources().getStringArray(R.array.date));

        // 根据a-z进行排序源数据
        Collections.sort(mDateList, mComparator);

        //RecyclerView设置manager
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        // manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);
       // mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));
        mDecoration = new TitleItemDecoration(this, mDateList);
        //如果add两个，那么按照先后顺序，依次渲染。
        mRecyclerView.addItemDecoration(mDecoration);
       // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        mAdapter = new BrandAdapter(this, mDateList);
        mRecyclerView.setAdapter(mAdapter);
*/

    }

    private void initToolBar() {
        toolbarTitle.setText("品牌区");
        toolbarBack.setVisibility(View.VISIBLE);
        mComparator = new PinyinComparator();

        mSideBar = (WaveSideBar) findViewById(R.id.sideBar);

        //设置右侧SideBar触摸监听
        mSideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = brandBeanSectionAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });


    }

    private void initRecyclerView() {

        manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);
       // mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

        brandBeanSectionAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));

        mRecyclerView.setAdapter(brandBeanSectionAdapter);

        brandBeanSectionAdapter.setOnClickCartItemListener(new BrandBeanSectionAdapter.OnClickCartItemListener() {
            @Override
            public void onClick(BrandBean brandBean) {
                if(type.equalsIgnoreCase("brand")){
                    Intent intent = new Intent(mContext, ProductListActivity.class);
                    intent.putExtra("title",brandBean.brandName);
                    intent.putExtra("id", brandBean.id+"");
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("id",brandBean.id+"");
                    intent.putExtra("name",brandBean.brandName);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void getBrandSuccess(BrandBeanList list) {
        if(list!=null) {
            num.setText("共"+list.num+"个品牌");
            MockBrandBean.init(this);
            List<BrandBean> data = MockBrandBean.getBrandList(list);
            MockBrandBean.sortData(data);
            brandBeanSectionAdapter.setNewData(MockBrandBean.brandSectionList);
            brandBeanSectionAdapter.notifyDataSetChanged();
        }
    }

    private List<BrandBean> filledData(String[] date) {
        List<BrandBean> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            BrandBean brandBean = new BrandBean();
            brandBean.brandName=date[i];
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                brandBean.brandZm=sortString.toUpperCase();
            } else {
                brandBean.brandZm = "#";
            }

            mSortList.add(brandBean);
        }
        return mSortList;

    }
}
