package com.eshop.mvp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eshop.app.base.BaseApp;
import com.eshop.mvp.contract.CartContract;
import com.eshop.mvp.contract.OrderContract;
import com.eshop.mvp.http.entity.cart.AppCartStore;
import com.eshop.mvp.http.entity.cart.AppGoods;
import com.eshop.mvp.http.entity.cart.AppGoodsSection;
import com.eshop.mvp.http.entity.cart.AppcarStore;
import com.eshop.mvp.http.entity.order.AppOrder;
import com.eshop.mvp.http.entity.order.AppOrderForm;
import com.eshop.mvp.http.entity.order.ExpressCode;
import com.eshop.mvp.http.entity.order.ExpressState;
import com.eshop.mvp.http.entity.order.Order;
import com.eshop.mvp.http.entity.order.PayRet;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.ui.activity.set.AddressActivity;
import com.eshop.mvp.ui.widget.AmountView;
import com.eshop.mvp.utils.LoginUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerCreateOrderComponent;
import com.eshop.di.module.CreateOrderModule;
import com.eshop.mvp.http.entity.order.OrderSettlementsBean;
import com.eshop.mvp.http.entity.ship.ShippingBean;
import com.eshop.mvp.presenter.CreateOrderPresenter;
import com.eshop.mvp.ui.adapter.CreateOrderAdapter;
import com.eshop.mvp.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateOrderActivity extends BaseSupportActivity<CreateOrderPresenter> implements OrderContract.View, CartContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    private CreateOrderAdapter createOrderAdapter;
    private View headerAddressView;
    private AddressBean select_address;
    private String productIds;
    private ProgressDialogUtils progressDialogUtils;

    private String from = "cart";

    public int appClassId = 0;

    /**
     * 生成的普通订单
     */
    private AppcarStore appcarStore;
    /**
     * 生成的月结订单
     */
    private AppcarStore appcarStore1;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCreateOrderComponent.builder()
                .appComponent(appComponent)
                .createOrderModule(new CreateOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_create_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("结算详情");
        toolbarBack.setVisibility(View.VISIBLE);

        if (!LoginUtils.isLogin(this)) {
            return;
        }

        initRecyclerList();
        setData();
        refreshCurrentPrice();

        from = getIntent().getStringExtra("from");
        if (from == null) from = "cart";

        mPresenter.getAddressList(BaseApp.loginBean.getToken(), BaseApp.loginBean.getId() + "");


    }

    @Override
    public void onStart() {
        super.onStart();
        setAddress();
    }

    private void initRecyclerList() {
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        createOrderAdapter = new CreateOrderAdapter();
        recyclerList.setAdapter(createOrderAdapter);

        headerAddressView = LayoutInflater.from(mContext).inflate(R.layout.header_activity_create_order, null);
        createOrderAdapter.setHeaderView(headerAddressView);
        createOrderAdapter.setOnClickCartItemListener(new CreateOrderAdapter.OnClickCartItemListener() {
            @Override
            public void onClickAmountCount(View view, AppGoods appGoods, int count) {
                if (mPresenter != null) {
                    mPresenter.updateNum(BaseApp.loginBean.getToken(), (AmountView) view, appGoods.carId + "", count);
                }
                appGoods.goodNum = count;
                BaseApp.isCartNeedRefresh = true;
                refreshCurrentPrice();
            }
        });


        headerAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateOrderActivity.this, AddressActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setAddress() {
        TextView receive_name = headerAddressView.findViewById(R.id.receiver_name);
        TextView receiver_phone = headerAddressView.findViewById(R.id.receiver_phone);
        TextView receiver_details = headerAddressView.findViewById(R.id.receiver_details);
        View empty = headerAddressView.findViewById(R.id.empty);
        View content = headerAddressView.findViewById(R.id.content);
        select_address = null;
        AddressBean default_address = null;
        if (BaseApp.addressBeanList != null) {
            if (BaseApp.addressBeanList.size() == 0) {
                empty.setVisibility(View.VISIBLE);
                content.setVisibility(View.GONE);
            } else {
                empty.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);


                for (AddressBean addressBean : BaseApp.addressBeanList) {
                    if (addressBean.isSelected) {

                        select_address = addressBean;

                    }

                    if (addressBean.isDefault == 1) default_address = addressBean;

                    if (BaseApp.addressBeanList.size() == 1) default_address = addressBean;

                }


                if (select_address == null) select_address = default_address;

                try {
                    receive_name.setText("收货人:" + select_address.receiveUserName);
                    receiver_phone.setText(select_address.receivePhone);
                    receiver_details.setText("收货地址:" + select_address.address);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            empty.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }
    }

    private void setData() {
        /** List appGoodsList = new ArrayList();
         for (AppGoodsSection cb : BaseApp.appGoodsSectionList) {
         if (cb.t.isChecked && !cb.isHeader) {
         appGoodsList.add(cb.t);
         }
         }*/

        createOrderAdapter.setNewData(BaseApp.appGoodsList);
    }


    public void refreshCurrentPrice() {
        double price = 0;
        double freight_price = 0;

        for (AppGoods appGoods : createOrderAdapter.getData()) {

            price = price + appGoods.unitPrice * appGoods.goodNum;

            if (appGoods.freightState == 1) {
                freight_price = appGoods.freight;
            } else {
                freight_price = 0;
            }

            if (freight_price != 0) price = price + freight_price * appGoods.goodNum;

        }


        tvProductPrice.setText(String.format("%s", price));
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {

        if (select_address == null) {
            showMessage("请添加收货地址");
            return;
        }

        if (BaseApp.appGoodsList.size() == 1) {
            AppGoods appGoods = BaseApp.appGoodsList.get(0);
            appClassId = appGoods.appClassId;
            mPresenter.addOrder(BaseApp.loginBean.getId() + "", appGoods.id + "", appGoods.goodNum + "", "", select_address.id + "", 0);
        } else {

            createOrder();

            if (appcarStore != null) {
                mPresenter.addCartOrder(BaseApp.loginBean.getToken(), appcarStore);
            }

            if (appcarStore1 != null) {
                mPresenter.addCartOrder(BaseApp.loginBean.getToken(), appcarStore1);
            }
        }
    }

    /**
     * {
     * "addressId": "2",
     * "orderType": "0", //0普通1月结
     * "storelist": [{
     * "storeId": "1",
     * "remarks": "111",
     * "carlist": "2, 5"
     * }, {
     * "storeId": "3",
     * "remarks": "222",
     * "carlist": "3，4"
     * }]
     * }
     *
     * @return
     */
    private void createOrder() {
        appcarStore = new AppcarStore();

        appcarStore.setAddressId(select_address.id + "");
        appcarStore.setOrderType("0");


        List<AppGoods> appGoodsList0 = new ArrayList<>(); //普通
        List<AppGoods> appGoodsList1 = new ArrayList<>(); //月结
        for (AppGoods appGoods : BaseApp.appGoodsList) {
            if (appGoods.isMonth) {
                appGoodsList1.add(appGoods);
            } else {
                appGoodsList0.add(appGoods);
            }
        }

        List<List<AppGoods>> storeList0 = new ArrayList<>(); //店铺列表

        int k = 0;
        for (AppGoods appGoods : appGoodsList0) {

            if (appGoods.isHead) {
                List<AppGoods> appGoodsList = new ArrayList<>();
                appGoodsList.add(appGoods);
                storeList0.add(appGoodsList);
            } else if (appGoods.isFoot) {
                storeList0.get(k).add(appGoods);
                k++;
            } else {
                storeList0.get(k).add(appGoods);
            }

        }

        List<List<AppGoods>> storeList1 = new ArrayList<>(); //店铺列表

        k = 0;
        for (AppGoods appGoods : appGoodsList1) {

            if (appGoods.isHead) {
                List<AppGoods> appGoodsList = new ArrayList<>();
                appGoodsList.add(appGoods);
                storeList1.add(appGoodsList);
            } else if (appGoods.isFoot) {
                storeList1.get(k).add(appGoods);
                k++;
            } else {
                storeList1.get(k).add(appGoods);
            }

        }

        List<AppcarStore.StorelistBean> storelistBeanList = new ArrayList<>();

        for (List<AppGoods> list0 : storeList0) {

            AppcarStore.StorelistBean storelistBean = new AppcarStore.StorelistBean();
            StringBuilder sb = new StringBuilder();
            for (AppGoods appGoods : list0) {
                if (appGoods.isHead == true) {
                    storelistBean.setStoreId(appGoods.storeId + "");

                }
                if (appGoods.isFoot == true) {
                    //if(appGoods.remark==null)appGoods.remark="";
                    storelistBean.setRemarks(appGoods.remark);

                }
                sb.append(appGoods.carId).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));

            storelistBean.setCarlist(sb.toString());

            storelistBeanList.add(storelistBean);

        }

        appcarStore.setStorelist(storelistBeanList);

        if (appGoodsList0.size() == 0) appcarStore = null;

        ////

        appcarStore1 = new AppcarStore();

        appcarStore1.setAddressId(select_address.id + "");
        appcarStore1.setOrderType("1");

        List<AppcarStore.StorelistBean> storelistBeanList1 = new ArrayList<>();

        for (List<AppGoods> list1 : storeList1) {

            AppcarStore.StorelistBean storelistBean1 = new AppcarStore.StorelistBean();
            StringBuilder sb1 = new StringBuilder();
            for (AppGoods appGoods : list1) {
                if (appGoods.isHead == true) {
                    storelistBean1.setStoreId(appGoods.storeId + "");
                }
                if (appGoods.isFoot == true) {
                    storelistBean1.setRemarks(appGoods.remark);
                }
                sb1.append(appGoods.carId).append(",");
            }
            sb1.deleteCharAt(sb1.lastIndexOf(","));

            storelistBean1.setCarlist(sb1.toString());

            storelistBeanList1.add(storelistBean1);

        }

        appcarStore1.setStorelist(storelistBeanList1);

        if (appGoodsList1.size() == 0) appcarStore1 = null;

    }

    /**
     * {
     * "addressId": "2",
     * "orderType": "0", //0普通1月结
     * "storelist": [{
     * "storeId": "1",
     * "remarks": "111",
     * "carlist": "2, 5"
     * }, {
     * "storeId": "3",
     * "remarks": "222",
     * "carlist": "3，4"
     * }]
     * }
     *
     * @return private void createOldOrder(){
     * json0=null;
     * json1=null;
     * <p>
     * List<AppGoods> appGoodsList0 = new ArrayList<>(); //普通
     * List<AppGoods> appGoodsList1 = new ArrayList<>(); //月结
     * for (AppGoods appGoods : BaseApp.appGoodsList) {
     * if(appGoods.isMonth){
     * appGoodsList1.add(appGoods);
     * }else{
     * appGoodsList0.add(appGoods);
     * }
     * }
     * <p>
     * List<List<AppGoods>> storeList0 = new ArrayList<>(); //店铺列表
     * <p>
     * int k = 0;
     * for (AppGoods appGoods : appGoodsList0) {
     * <p>
     * if (appGoods.isHead) {
     * List<AppGoods> appGoodsList = new ArrayList<>();
     * appGoodsList.add(appGoods);
     * storeList0.add(appGoodsList);
     * }else if (appGoods.isFoot) {
     * storeList0.get(k).add(appGoods);
     * k++;
     * }else{
     * storeList0.get(k).add(appGoods);
     * }
     * <p>
     * }
     * <p>
     * List<List<AppGoods>> storeList1 = new ArrayList<>(); //店铺列表
     * <p>
     * k = 0;
     * for (AppGoods appGoods : appGoodsList1) {
     * <p>
     * if (appGoods.isHead) {
     * List<AppGoods> appGoodsList = new ArrayList<>();
     * appGoodsList.add(appGoods);
     * storeList1.add(appGoodsList);
     * }else if (appGoods.isFoot) {
     * storeList1.get(k).add(appGoods);
     * k++;
     * }else{
     * storeList1.get(k).add(appGoods);
     * }
     * <p>
     * }
     * <p>
     * <p>
     * JsonObject root0 = new JsonObject();
     * root0.addProperty("addressId",select_address.id);
     * root0.addProperty("orderType","0");
     * JsonArray storelist_json_0 = new JsonArray();
     * <p>
     * for (List<AppGoods> list0 : storeList0) {
     * <p>
     * JsonObject store = new JsonObject();
     * JsonArray cartlist = new JsonArray();
     * for(AppGoods appGoods : list0){
     * if(appGoods.isHead==true){
     * store.addProperty("storeId",appGoods.storeId);
     * }
     * if(appGoods.isFoot==true){
     * store.addProperty("remarks",appGoods.remark);
     * }
     * cartlist.add(appGoods.carId);
     * }
     * store.add("carlist",cartlist);
     * <p>
     * storelist_json_0.add(store);
     * }
     * <p>
     * root0.add("storelist",storelist_json_0);
     * <p>
     * json0 = root0.toString();
     * <p>
     * if(appGoodsList0.size()==0)json0=null;
     * <p>
     * ////
     * <p>
     * JsonObject root1 = new JsonObject();
     * root1.addProperty("addressId",select_address.id);
     * root1.addProperty("orderType","1");
     * JsonArray storelist_json_1 = new JsonArray();
     * <p>
     * for (List<AppGoods> list1 : storeList1) {
     * <p>
     * JsonObject store = new JsonObject();
     * JsonArray cartlist = new JsonArray();
     * for(AppGoods appGoods : list1){
     * if(appGoods.isHead==true){
     * store.addProperty("storeId",appGoods.storeId);
     * }
     * if(appGoods.isFoot==true){
     * store.addProperty("remarks",appGoods.remark);
     * }
     * cartlist.add(appGoods.carId);
     * }
     * store.add("carlist",cartlist);
     * <p>
     * storelist_json_1.add(store);
     * }
     * <p>
     * root1.add("storelist",storelist_json_1);
     * <p>
     * json1 = root1.toString();
     * <p>
     * if(appGoodsList1.size()==0)json1=null;
     * <p>
     * }
     */

    @Override
    public void showLoading() {
        // progressDialogUtils = ProgressDialogUtils.getInstance(mContext).show();
    }

    @Override
    public void hideLoading() {
        // if (progressDialogUtils != null) progressDialogUtils.dismiss();
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
    public void addOrderSuccess(Order data) {
        showMessage("添加订单成功");
        BaseApp.isCartNeedRefresh = true;
        /**  if(from.equalsIgnoreCase("cart")) {
         Intent intent1 = new Intent(this, OrderActivity.class);
         startActivity(intent1);

         }else{*/

        Intent intent = new Intent(this, PayActivity.class);
        intent.putExtra("id", data.getId() + "");
        intent.putExtra("money", data.getTotalPrice());
        intent.putExtra("time", data.getRemainingTime());
        intent.putExtra("appClassId", appClassId);
        startActivity(intent);
        // Intent intent1 = new Intent(this, OrderActivity.class);
        // startActivity(intent1);
        // }
        finish();
    }

    @Override
    public void addOrderSuccess(List<Order> data) {
        showMessage("添加订单成功");
        BaseApp.isCartNeedRefresh = true;

        if (data.size() == 1) {

            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra("id", data.get(0).getId() + "");
            intent.putExtra("money", data.get(0).getTotalPrice());
            intent.putExtra("time", data.get(0).getRemainingTime());

            if(data.get(0).getOrderType()==0){
                appClassId = 0;
            }else{
                appClassId = 33;
            }

            intent.putExtra("appClassId", appClassId);
            startActivity(intent);

            finish();
        }else{
             Intent intent1 = new Intent(this, OrderActivity.class);
             startActivity(intent1);

        }
    }

    @Override
    public void alipayPaySuccess(String data) {

    }

    @Override
    public void alipayPayNotifySuccess(PayRet data) {

    }

    @Override
    public void cancelOrderSuccess(AppOrder data) {

    }

    @Override
    public void deleteOrderSuccess() {

    }

    @Override
    public void deliverGoodsSuccess(AppOrder data) {

    }

    @Override
    public void finishOrderSuccess(AppOrder data) {

    }

    @Override
    public void getOrderSuccess(AppOrder data) {

    }

    @Override
    public void getOrderDetailsSuccess(AppOrderForm data) {

    }

    @Override
    public void getStoreOrderSuccess(AppOrder data) {

    }

    @Override
    public void logisticsSuccess(ExpressState data) {

    }

    @Override
    public void logisticsAllSuccess(List<ExpressCode> list) {

    }

    @Override
    public void monthPaySuccess(PayRet data) {

    }

    @Override
    public void monthPayStatus(String status, String msg) {

    }

    @Override
    public void paymentSuccess(String data) {

    }

    @Override
    public void reminderShipmentSuccess(String data) {

    }

    @Override
    public void wxpaySuccess(PayRet data) {

    }

    @Override
    public void getCartListSuccess(AppCartStore data) {

    }

    @Override
    public void addGoodSuccess() {

    }


    @Override
    public void countPriceSuccess(String price) {

    }

    @Override
    public void delCartSuccess() {

    }

    @Override
    public void updateNumSuccess(int count) {
        BaseApp.isCartNeedRefresh = true;
        refreshCurrentPrice();
    }

    @Override
    public void getAddressList(List<AddressBean> list) {
        BaseApp.addressBeanList = list;
        setAddress();
    }

    @Override
    public void updateOrderSuccess() {

    }
}
