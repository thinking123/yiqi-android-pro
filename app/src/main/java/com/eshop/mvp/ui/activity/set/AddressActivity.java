package com.eshop.mvp.ui.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bin.david.dialoglib.BaseDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.module.AddressModule;
import com.eshop.mvp.http.entity.home.MockAddress;
import com.eshop.mvp.http.entity.home.MockCats;
import com.eshop.mvp.http.entity.product.StoreCat;
import com.eshop.mvp.http.entity.ship.AddressBean;
import com.eshop.mvp.http.entity.ship.CityBean;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.ui.adapter.AddressQuickAdapter;
import com.eshop.mvp.ui.adapter.StoreCatQuickAdapter;
import com.eshop.mvp.ui.fragment.CityListDialogFragment;
import com.eshop.mvp.ui.widget.SubCatDialog;
import com.eshop.mvp.utils.LoginUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerAddressComponent;
import com.eshop.mvp.contract.AddressContract;
import com.eshop.mvp.presenter.AddressPresenter;

import com.eshop.R;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/28/2019 14:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AddressActivity extends BaseSupportActivity<AddressPresenter> implements AddressContract.View,CityListDialogFragment.Listener {

    @Inject
    List<AddressBean> list;

    @Inject
    AddressQuickAdapter addressQuickAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.empty_address)
    View empty_address;

    private AddressBean selectedAddressBean;

    MaterialDialog dialog;

    EditText receive_name;
    EditText phone;
    TextView region;
    EditText address;
    Button save;

    private String from= "order";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .addressModule(new AddressModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("收货地址");
        toolbarBack.setVisibility(View.VISIBLE);
        if(!LoginUtils.isLogin(this)) {
            return;
        }
        initRecycler();
        from = getIntent().getStringExtra("from");
        if(from==null)from="order";

        selectedAddressBean = BaseApp.addressBean;

        mPresenter.get(BaseApp.loginBean.getToken(),BaseApp.loginBean.getId()+"");

    }

    private void getAddress(){
        //MockAddress.init();
        //BaseApp.addressBeanList = MockAddress.addressList;
        if(BaseApp.addressBeanList==null || BaseApp.addressBeanList.size()==0){
            empty_address.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            empty_address.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            addressQuickAdapter.setNewData(BaseApp.addressBeanList);
        }
    }

    private void initRecycler() {
        Timber.e("initRecycler");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // mRecyclerView.addItemDecoration(new RecommendItemDecoration(10));

        // storeCatQuickAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null));
        addressQuickAdapter.setOnClickAddressItemListener(new AddressQuickAdapter.OnClickAddressItemListener() {
            @Override
            public void onClickItem(String type, AddressBean item) {
                switch (type){
                    case "del":
                        new MaterialDialog.Builder(AddressActivity.this)
                                .content("确定要删除该地址吗？")
                                .contentColor(getResources().getColor(R.color.color_3333))
                                .backgroundColorRes(R.color.white)
                                .negativeText(R.string.cancel)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        if (mPresenter != null)
                                            mPresenter.del(item.id+"");
                                    }
                                })

                                .show();

                        break;
                    case "use":
                        for(AddressBean addressBean : BaseApp.addressBeanList){
                            addressBean.isSelected = false;
                            if(addressBean.id.equals(item.id)){
                                addressBean.isSelected=true;
                                selectedAddressBean = addressBean;
                            }
                        }

                        BaseApp.addressBean = selectedAddressBean;

                        addressQuickAdapter.setNewData(BaseApp.addressBeanList);
                        break;
                    case "set":

                        if (mPresenter != null)
                            mPresenter.setDefault(BaseApp.loginBean.getId()+"",item.id+"");
                        break;
                    case "edit":
                        dialog =  new MaterialDialog.Builder(AddressActivity.this)
                                .title("添加收货地址")
                                .titleColor(getResources().getColor(R.color.color_3333))
                                .customView(R.layout.dialog_address,false)
                                .backgroundColorRes(R.color.white).build();

                        receive_name = dialog.getCustomView().findViewById(R.id.name);
                        phone = dialog.getCustomView().findViewById(R.id.phone);
                        region = dialog.getCustomView().findViewById(R.id.city);
                        address = dialog.getCustomView().findViewById(R.id.address);

                        receive_name.setText(item.receiveUserName);
                        phone.setText(item.receivePhone);
                        address.setText(item.address);


                        dialog.getCustomView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(veryfyData()){

                                    String token = BaseApp.loginBean.getToken();
                                    String recevie_address = region.getText().toString()+address.getText().toString();
                                    String receiveUserName = receive_name.getText().toString();
                                    String receivePhone = phone.getText().toString();
                                    mPresenter.edit(token,item.id+"",recevie_address,receiveUserName,receivePhone,item.isDefault+"");
                                }

                            }
                        });

                        dialog.getCustomView().findViewById(R.id.area).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                showCityDialog();

                            }
                        });
                        dialog.show();
                        break;
                }
            }
        });

        mRecyclerView.setAdapter(addressQuickAdapter);
    }

    @OnClick({R.id.btn_add})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_add:
                dialog =  new MaterialDialog.Builder(AddressActivity.this)
                        .title("添加收货地址")
                        .titleColor(getResources().getColor(R.color.color_3333))
                        .customView(R.layout.dialog_address,false)
                        .backgroundColorRes(R.color.white).build();

                receive_name = dialog.getCustomView().findViewById(R.id.name);
                phone = dialog.getCustomView().findViewById(R.id.phone);
                region = dialog.getCustomView().findViewById(R.id.city);
                address = dialog.getCustomView().findViewById(R.id.address);

                dialog.getCustomView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(veryfyData()){
                            String userId = BaseApp.loginBean.getId()+"";
                            String recevie_address = region.getText().toString()+address.getText().toString();
                            String receiveUserName = receive_name.getText().toString();
                            String receivePhone = phone.getText().toString();
                            mPresenter.add(userId,recevie_address,receiveUserName,receivePhone);
                        }

                    }
                });

                dialog.getCustomView().findViewById(R.id.area).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showCityDialog();

                    }
                });
                dialog.show();
                break;
        }
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
    public void selectProSuccess(List<CityBean> list) {

    }

    @Override
    public void selectCitySuccess(List<CityBean> list) {

    }

    @Override
    public void getSuccess(List<AddressBean> list) {
        MockAddress.init();
        BaseApp.addressBeanList = list;
               // MockAddress.addressList;//list;

        for(AddressBean addressBean : BaseApp.addressBeanList){
            addressBean.isSelected = false;
            if(selectedAddressBean!=null && addressBean.id.equals(selectedAddressBean.id)){
                addressBean.isSelected=true;
                selectedAddressBean = addressBean;
            }
        }

        getAddress();

    }

    @Override
    public void addSuccess() {
      //  showMessage("地址添加成功");
        mPresenter.get(BaseApp.loginBean.getToken(),BaseApp.loginBean.getId()+"");
        dialog.dismiss();
    }

    @Override
    public void delSuccess() {
        showMessage("删除地址成功.");
        mPresenter.get(BaseApp.loginBean.getToken(),BaseApp.loginBean.getId()+"");
    }

    @Override
    public void setDefaultSuccess() {
        mPresenter.get(BaseApp.loginBean.getToken(),BaseApp.loginBean.getId()+"");
    }

    private void showCityDialog() {
        FragmentManager fm =  getSupportFragmentManager();
        CityListDialogFragment dialogFragment = CityListDialogFragment.newInstance(AddressActivity.this);
        dialogFragment.show(fm, "fragment_edit_name");
    }


    @Override
    public void onCitySelected(String city) {
        region.setText(city);
    }

    private boolean veryfyData(){
        if(receive_name.getText().length()==0){
            showMessage("请输入收货人姓名");
            return false;
        }

        if(phone.getText().length()==0){
            showMessage("请输入联系电话");
            return false;
        }

        if(region.getText().toString().equalsIgnoreCase("选择地区")){
           // showMessage("请选择地区");
           // return false;
        }

        if(address.getText().length()==0){
            showMessage("请输入详细地址");
            return false;
        }

        return true;
    }
}
