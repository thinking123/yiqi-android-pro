package com.eshop.mvp.ui.activity.store;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.BankCard;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.CatProductPresenter;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.ui.activity.set.AddressActivity;
import com.eshop.mvp.ui.fragment.CityListDialogFragment;
import com.eshop.mvp.ui.fragment.DatePickerFragment;
import com.eshop.mvp.ui.widget.ExampleDialog;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.google.gson.Gson;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.SimpleSpinnerTextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Avatar;
import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 完善店铺信息
 * ================================================
 */
public class StoreInfoActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View, CityListDialogFragment.Listener {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.nice_spinner_cat)
    NiceSpinner nice_spinner_cat;

    @BindView(R.id.nice_spinner_sub)
    NiceSpinner nice_spinner_sub;

    @BindView(R.id.lable6)
    TextView lable6;

    @BindView(R.id.head)
    ImageView head;

    @BindView(R.id.name)
    EditText edit_name;

    @BindView(R.id.address)
    EditText edit_address;

    @BindView(R.id.store_bak)
    ImageView store_bak;

    @BindView(R.id.tip)
    TextView tip;


    private int catid = 0;
    private List<CatBean> catBeans;
    private List<CatBean> subCatBeans;

    private PicChooserHelper picChooserHelper;
    private String head_url;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                .storeManagerModule(new StoreManagerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_store_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("完善店铺信息");
        toolbarBack.setVisibility(View.VISIBLE);
        if(!LoginUtils.isLogin(this)) {
            return;
        }
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        //  nice_spinner_cat.attachDataSource(dataset);
        nice_spinner_sub.attachDataSource(dataset);

        nice_spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    CatBean catBean = catBeans.get(position);
                    catid = catBean.id;

                    mPresenter.getCats(catid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (mPresenter != null) {
            mPresenter.getCats(catid);
        }

        if(BaseApp.storeState!=null && BaseApp.storeState.page3State!=0){
            tip.setVisibility(View.VISIBLE);
            tip.setText(BaseApp.storeState.page3Info);
        }else{
            tip.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.city_select, R.id.btn_pay, R.id.head, R.id.store_bak})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.store_bak:
                uploadBackgroundImage();
                break;
            case R.id.head:
                uploadImage();
                break;
            case R.id.city_select:
                showCityDialog();
                break;
            case R.id.btn_pay:
                if (veryfy()) {
                    BaseApp.authInfo.setToken(BaseApp.loginBean.getToken());
                    mPresenter.store(BaseApp.authInfo);


                }
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
    public void accountCreatSuccess() {

    }

    @Override
    public void banCarAllSuccess(BankCards bankCard) {

    }

    @Override
    public void bankIdDelSuccess() {

    }

    @Override
    public void cashTypeSuccess(CashType cashType) {

    }

    @Override
    public void checkPendingGoodsSuccess(Audit audit) {

    }

    @Override
    public void drawingSuccess() {

    }

    @Override
    public void goodsSuccess() {

    }

    @Override
    public void goodsDelSuccess() {

    }

    @Override
    public void goodsPutSuccess() {

    }

    @Override
    public void inSalesGoodsSuccess(Audit audit) {

    }

    @Override
    public void opinionSuccess() {

    }

    @Override
    public void pwdCreatSuccess() {

    }

    @Override
    public void recordSuccess(WithDrawRecord withDrawRecord) {

    }

    @Override
    public void sellingGoodsSuccess() {

    }

    @Override
    public void stateSuccess(StoreState storeState) {

    }

    @Override
    public void stateResult(String status, String msg, StoreState storeState) {

    }



    @Override
    public void stayOnTheShelfGoodsSuccess(Audit audit) {

    }

    @Override
    public void stopSellingGoodsSuccess() {

    }

    @Override
    public void storeSuccess() {
        showMessage("提交成功.");
        finish();
    }

    @Override
    public void storeColumnResult(StoreCatBean data) {

    }

    @Override
    public void storeColumnSuccess() {

    }

    @Override
    public void storeColumnCreatSuccess() {

    }

    @Override
    public void storeColumnDelSuccess() {

    }

    @Override
    public void storeLogoPutSuccess() {

    }

    @Override
    public void transactionSuccess(TransList transList) {

    }

    @Override
    public void walletSuccess(Wallet wallet) {

    }

    @Override
    public void getAuthSuccess(Auth auth) {

    }

    @Override
    public void updateUserImageSuccess(String url) {
        if (BaseApp.authInfo == null) BaseApp.authInfo = new AuthInfo();
        if (url != null) {
            head_url = url;
            Glide.with(this)
                    .load(url)//new MultiTransformation(
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                    .into(head);
            BaseApp.authInfo.setStoreImg(url);


        }
    }

    @Override
    public void getCatBeanList(List<CatBean> data) {


        SimpleSpinnerTextFormatter textFormatter = new SimpleSpinnerTextFormatter() {
            @Override
            public Spannable format(Object item) {
                CatBean catBean = (CatBean) item;
                return new SpannableString(catBean.categoryName);
            }
        };

        if (catid == 0) {
            catBeans = data;
            nice_spinner_cat.setSpinnerTextFormatter(textFormatter);
            nice_spinner_cat.setSelectedTextFormatter(textFormatter);

            nice_spinner_cat.attachDataSource(data);

            catid = data.get(0).id;
            mPresenter.getCats(catid);

        } else {
            subCatBeans = data;
            nice_spinner_sub.setSpinnerTextFormatter(textFormatter);
            nice_spinner_sub.setSelectedTextFormatter(textFormatter);

            nice_spinner_sub.attachDataSource(data);
        }
    }

    @Override
    public void idStoreSuccess(StoreInfomation storeInfomation) {

    }

    @Override
    public void updateUserInfoSuccess(LoginBean msg) {

    }

    @Override
    public void getGoodDetailSuccess(ProductDetail good) {

    }

    @Override
    public void getMonthMsgSuccess(MonthMsg monthMsg) {

    }

    @Override
    public void getMonthMsgStatus(String status, String msg) {

    }

    @Override
    public void getIdMyQRCodeSuccess(QRCode qrCode) {

    }


    @Override
    public void onCitySelected(String city) {
        lable6.setText(city);
    }

    private void showCityDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CityListDialogFragment dialogFragment = CityListDialogFragment.newInstance(StoreInfoActivity.this);
        dialogFragment.show(fm, "fragment_edit_name");
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Avatar);
        picChooserHelper.setOnChooseResultListener(url -> {
           // if (mPresenter != null) {
           //     mPresenter.updateUserImage(url);
           // }
            if (BaseApp.authInfo == null) BaseApp.authInfo = new AuthInfo();
            if (url != null) {
                head_url = url;
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                        .into(head);
                BaseApp.authInfo.setStoreImg(url);


            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(StoreInfoActivity.this)
                .title("头像上传")
                .backgroundColorRes(R.color.white)
                .titleColorRes(R.color.color_3333)
                .itemsColorRes(R.color.color_3333)
                .negativeText(R.string.cancel_easy_photos)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                picChooserHelper.takePicFromCamera();
                                break;
                            case 1:
                                picChooserHelper.takePicFromAlbum();
                                break;
                        }
                    }
                }).show();

    }

    public void uploadBackgroundImage() {
        picChooserHelper = new PicChooserHelper(this, Cover);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (BaseApp.authInfo == null) BaseApp.authInfo = new AuthInfo();
            if (url != null) {

                Glide.with(this)
                        .load(url)//new MultiTransformation(
                       // .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                        .into(store_bak);

                BaseApp.authInfo.setBackground(url);


            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(StoreInfoActivity.this)
                .title("图片上传")
                .backgroundColorRes(R.color.white)
                .titleColorRes(R.color.color_3333)
                .itemsColorRes(R.color.color_3333)
                .negativeText(R.string.cancel_easy_photos)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                picChooserHelper.takePicFromCamera();
                                break;
                            case 1:
                                picChooserHelper.takePicFromAlbum();
                                break;
                        }
                    }
                }).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, resultCode, data);
    }

    private boolean veryfy() {

      /**  BaseApp.authInfo = new AuthInfo();
        BaseApp.authInfo.setBackground("http://117.50.55.103/file/bac899ae-7c85-495a-a651-5465f710ae97.png");
        BaseApp.authInfo.setLicenseDate("2019-1-10");
        BaseApp.authInfo.setLicenseImg("http://117.50.55.103/file/d4c7b876-318f-45a3-9000-7ab47cdb584c.png");
        BaseApp.authInfo.setIdCardHeadImg("http://117.50.55.103/file/d4c7b876-318f-45a3-9000-7ab47cdb584c.png");
        BaseApp.authInfo.setStoreCategory("2");
        BaseApp.authInfo.setStoreCategoryParent("1");
        BaseApp.authInfo.setIdCard("320102199003072530");
        BaseApp.authInfo.setCity("深圳");
        BaseApp.authInfo.setProvince("广东");
        BaseApp.authInfo.setDetailsAddress("测试");
        BaseApp.authInfo.setStreoName("测试");
        BaseApp.authInfo.setStorePhone("13928439999");
        BaseApp.authInfo.setLicenseIsLong("1");
        BaseApp.authInfo.setToken("f9131e064aefaf37483e6afe7d422f20");
        BaseApp.authInfo.setStoreImg("http://117.50.55.103/file/512e0d84-d417-4b58-83e2-9c65d65fdbab.png");
        BaseApp.authInfo.setLicenseNumber("110108000000016");
        BaseApp.authInfo.setLicenseName("测试");
        BaseApp.authInfo.setTrueName("测试");
        BaseApp.authInfo.setIdCardIMg("http://117.50.55.103/file/d4c7b876-318f-45a3-9000-7ab47cdb584c.png");

        Gson gson = new Gson();
        String json = gson.toJson(BaseApp.authInfo).toString();
        */
          if(edit_name.getText().toString().isEmpty()){
         showMessage("请输入店铺名称.");
         return false;
         }

         if(edit_address.getText().toString().isEmpty()){
         showMessage("请输入详细地址.");
         return false;
         }

         if(lable6.getText().toString().isEmpty()){
         showMessage("请选择地区.");
         return false;
         }

         if(BaseApp.authInfo==null)BaseApp.authInfo = new AuthInfo();

         BaseApp.authInfo.setStreoName(edit_name.getText().toString());
         BaseApp.authInfo.setDetailsAddress(edit_address.getText().toString());

         String city = lable6.getText().toString();
         String[] citys = city.split(" ");

         BaseApp.authInfo.setProvince(BaseApp.province);
         BaseApp.authInfo.setCity(BaseApp.city);

         int j = nice_spinner_cat.getSelectedIndex();
         int k = nice_spinner_sub.getSelectedIndex();
         CatBean catBean = catBeans.get(j);
         CatBean subBean = subCatBeans.get(k);

         BaseApp.authInfo.setStoreCategoryParent(catBean.id+"");
         BaseApp.authInfo.setStoreCategory(subBean.id+"");

        return true;
    }


}
