package com.eshop.mvp.ui.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.home.HomeGoodBean;
import com.eshop.mvp.http.entity.home.MockAddress;
import com.eshop.mvp.http.entity.home.MockAppCartStore;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.ui.activity.order.CreateOrderActivity;
import com.eshop.mvp.ui.activity.product.ProductDetailsActivity;
import com.eshop.mvp.ui.adapter.AppCartSectionAdapter;
import com.eshop.mvp.ui.widget.AmountView;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerCartComponent;
import com.eshop.di.module.CartModule;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.http.entity.cart.CartBean;
import com.eshop.mvp.presenter.CartPresenter;
import com.eshop.mvp.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseSupportFragment<CartPresenter> implements CartContract.View, CompoundButton.OnCheckedChangeListener {



    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Inject
    AppCartSectionAdapter appCartSectionAdapter;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.btn_balance)
    Button btnBalance;

    @BindView(R.id.top_bar)
    View top_bar;
    @BindView(R.id.bottom_bar)
    View bottom_bar;

    @BindView(R.id.gongji)
    TextView gongji;


    private ProgressDialogUtils progressDialogUtils;

    private int goodNum = 0;
    private List<String> cardIdList = new ArrayList<>();

    private int type = 0;

    public CartFragment() {
        // Required empty public constructor
    }

    public void setType(int type){
        this.type = type;
    }

    private String token = null;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCartComponent.builder()
                .appComponent(appComponent)
                .cartModule(new CartModule(this))
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initRecyclerView();
        initBtn();
        if (!LoginUtils.isLogin(getActivity())) {
            return;
        }

        token = BaseApp.loginBean.getToken();
        if (mPresenter != null) {
            mPresenter.getCartList(token, BaseApp.loginBean.getId() + "");
            mPresenter.getAddressList(token, BaseApp.loginBean.getId() + "");
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        if (BaseApp.isCartNeedRefresh) {
            if (BaseApp.loginBean != null) {
                if (mPresenter != null) {
                    mPresenter.getCartList(token, BaseApp.loginBean.getId() + "");
                    mPresenter.getAddressList(token, BaseApp.loginBean.getId() + "");
                }
            }
            BaseApp.isCartNeedRefresh = false;
        }

    }


    private void initBtn() {
        tvBalance.setText("0");
        cbAll.setChecked(false);
        cbAll.setOnCheckedChangeListener(this);
    }

    private void refresh(){
        if (!LoginUtils.isLogin(getActivity())) {
            if (progressDialogUtils != null) progressDialogUtils.dismiss();
            if (swipeRefresh != null) {
                swipeRefresh.setRefreshing(false);
            }
            return;
        }
        mPresenter.getCartList(token, BaseApp.loginBean.getId() + "");
    }

    private void initRecyclerView() {
        swipeRefresh.setOnRefreshListener(() -> refresh()

        );
        recyclerList.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerList.setAdapter(appCartSectionAdapter);
        appCartSectionAdapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.view_empty_cart, null));
        appCartSectionAdapter.setOnClickCartItemListener(new AppCartSectionAdapter.OnClickCartItemListener() {

            @Override
            public void onClickAmountCount(View view, AppGoods appGoods, int count) {
                if (mPresenter != null) {
                    mPresenter.updateNum(token, (AmountView) view, appGoods.carId + "", count);
                }
                appGoods.goodNum = count;
                refreshCurrentPrice();
            }

            /**
             *
             * @param isAllChecked 购物车所有商品选中
             * @param isStoreAllChecked 本商铺所有商品选中
             * @param appGoods 点击的商品
             * @param isHead 商铺题头
             */
            @Override
            public void onAllChecked(boolean isAllChecked, boolean isStoreAllChecked, AppGoods appGoods, boolean isHead) {
                refreshCurrentPrice();
                // cbAll.setChecked(isAllChecked);
                if (isHead) { //商铺题头
                    setStoreChecked(appGoods, appGoods.isChecked);
                } else {
                    setStoreHeadChecked(appGoods, isStoreAllChecked);
                }

                isAllChecked = appCartSectionAdapter.isAllChecked();
                cbAll.setOnCheckedChangeListener(null);
                cbAll.setChecked(isAllChecked);
                cbAll.setOnCheckedChangeListener(CartFragment.this);
            }
        });
    }

    /**
     * 把一个商铺的所有商品设为isChecked
     *
     * @param appGoods
     * @param isChecked
     */
    public void setStoreChecked(AppGoods appGoods, boolean isChecked) {
        for (AppGoodsSection cb : appCartSectionAdapter.getData()) {
            if (cb.t.storeId == appGoods.storeId) {
                cb.t.isChecked = isChecked;
            }
        }
        refreshCurrentPrice();
    }

    /**
     * 把一个商铺的题头设为isChecked
     *
     * @param appGoods
     * @param isChecked
     */
    public void setStoreHeadChecked(AppGoods appGoods, boolean isChecked) {
        for (AppGoodsSection cb : appCartSectionAdapter.getData()) {
            if (cb.t.storeId == appGoods.storeId && cb.isHeader) {
                cb.t.isChecked = isChecked;
            }
        }
    }


    @OnClick({R.id.btn_balance, R.id.del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.del:
                if (goodNum == 0) showMessage("您还没有选择商品哦");
                else
                    showMessageDialog(R.string.del_tip, R.string.empty);
                break;
            case R.id.btn_balance:
                if (goodNum == 0) showMessage("您还没有选择商品哦");
                else {
                    BaseApp.appGoodsSectionList = appCartSectionAdapter.getData();
                    List<AppGoods> appGoodsList = new ArrayList();
                    int k = 0;
                    AppGoods last_appgoods = null;
                    for (AppGoodsSection cb : BaseApp.appGoodsSectionList) {
                        if (cb.isHeader) {
                            if (last_appgoods != null) last_appgoods.isFoot = true;
                            k = 0;
                            last_appgoods = null;
                            continue;
                        }
                        if (cb.t.isChecked && !cb.isHeader) {

                            if (k == 0) cb.t.isHead = true;
                            else cb.t.isHead = false;

                            last_appgoods = cb.t;

                            k++;

                            appGoodsList.add(cb.t);

                        }
                    }
                    if (last_appgoods != null) last_appgoods.isFoot = true;
                    BaseApp.appGoodsList = appGoodsList;
                    Intent intent = new Intent(_mActivity, CreateOrderActivity.class);
                    startActivity(intent);
                }
                break;

        }

    }

    private String getCartIds() {
        //拼接字符串
        StringBuilder productIds = new StringBuilder();
        for (String id : cardIdList) {
            productIds.append(id).append(",");
        }
        productIds.deleteCharAt(productIds.length() - 1);

        return productIds.toString();
    }

    private void showMessageDialog(int title, int message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.delCart(token, getCartIds());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void showLoading() {
        //progressDialogUtils = ProgressDialogUtils.getInstance(_mActivity).show();
    }

    @Override
    public void hideLoading() {
        if (progressDialogUtils != null) progressDialogUtils.dismiss();
        if (swipeRefresh != null) {
            swipeRefresh.setRefreshing(false);
        }
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

    public void refreshCurrentPrice() {
        double price = 0;
        goodNum = 0;
        cardIdList.clear();
        for (AppGoodsSection cb : appCartSectionAdapter.getData()) {
            if (cb.t.isChecked && !cb.isHeader) {
                price = price + cb.t.unitPrice * cb.t.goodNum;
                goodNum += 1;
                cardIdList.add(cb.t.carId + "");
            }
        }
        gongji.setText("共计" + goodNum + "件商品");
        tvBalance.setText(String.format("合计: %s", price));
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        appCartSectionAdapter.setAllChecked(isChecked);
        refreshCurrentPrice();
    }

    @Override
    public void getCartListSuccess(AppCartStore data) {

        MockAppCartStore.init();

        if (data == null || MockAppCartStore.getAppGoodsSection(type,data).size() == 0) {
            top_bar.setVisibility(View.GONE);
            bottom_bar.setVisibility(View.GONE);
        } else {
            top_bar.setVisibility(View.VISIBLE);
            bottom_bar.setVisibility(View.VISIBLE);
        }

        appCartSectionAdapter.setNewData(MockAppCartStore.getAppGoodsSection(type,data));
        // appCartSectionAdapter.setNewData(MockAppCartStore.getAppGoodsSection(MockAppCartStore.appCartStore));

        appCartSectionAdapter.notifyDataSetChanged();
        refreshCurrentPrice();

        cbAll.setOnCheckedChangeListener(null);
        cbAll.setChecked(false);
        cbAll.setOnCheckedChangeListener(this);

    }

    @Override
    public void addGoodSuccess() {

    }

    @Override
    public void addOrderSuccess(List<Order> data) {

    }



    @Override
    public void countPriceSuccess(String price) {

    }

    @Override
    public void delCartSuccess() {
        mPresenter.getCartList(token, BaseApp.loginBean.getId() + "");
    }

    @Override
    public void updateNumSuccess(int count) {

    }

    @Override
    public void getAddressList(List<AddressBean> list) {
        BaseApp.addressBeanList = list;

        //MockAddress.init();
        //if(list==null || list.size()==0)
        //BaseApp.addressBeanList = MockAddress.addressList;
    }


}
