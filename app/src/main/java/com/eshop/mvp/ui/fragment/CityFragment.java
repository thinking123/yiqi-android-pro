package com.eshop.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.module.CityModule;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.ui.adapter.CityQuickAdapter;
import com.eshop.mvp.ui.adapter.SubCatQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerCityComponent;
import com.eshop.mvp.contract.CityContract;
import com.eshop.mvp.presenter.CityPresenter;

import com.eshop.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/30/2019 00:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CityFragment extends BaseSupportFragment<CityPresenter> implements CityContract.View {

    @Inject
    List<CityBean> cityBeans;

    @Inject
    CityQuickAdapter cityQuickAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerList;

    private String cityCode="";

    private CitySelectListener listener;

    private int page;

    public static CityFragment newInstance(int page,String code,CitySelectListener l) {
        CityFragment fragment = new CityFragment();
        fragment.cityCode = code;
        fragment.listener = l;
        fragment.page = page;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .cityModule(new CityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        getNewData(cityCode);
    }

    public void getNewData(String code){
        this.cityCode = code;
        if(cityCode.isEmpty()){
            mPresenter.selectPro();
        }else{
            mPresenter.selectCity(cityCode);
        }
    }

    public void setCitycode(String code){
        this.cityCode = code;
    }

    private void initRecyclerView() {

        recyclerList.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerList.setAdapter(cityQuickAdapter);
        cityQuickAdapter.setOnItemClickListener((adapter, view, position) -> {

                   String id = ((CityBean) (adapter.getData()).get(position)).adCode;
            String caption = ((CityBean) (adapter.getData()).get(position)).cityCaption;
            //CitySelectListener listener = (CitySelectListener) getTargetFragment();
            listener.onSelected(page,caption,id);


                }
        );
    }

    public interface CitySelectListener {
        void onSelected(int page,String caption,String cityCode);
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
    public void selectProSuccess(List<CityBean> list) {
        cityQuickAdapter.setNewData(list);
    }

    @Override
    public void selectCitySuccess(List<CityBean> list) {
        cityQuickAdapter.setNewData(list);
    }
}
