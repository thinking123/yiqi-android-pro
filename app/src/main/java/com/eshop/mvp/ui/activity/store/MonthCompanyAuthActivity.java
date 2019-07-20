package com.eshop.mvp.ui.activity.store;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerMonthAuthComponent;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.MonthAuthModule;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.MonthAuthContract;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.auth.MonthData;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.AuthInfo;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.MonthAuthPresenter;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.activity.MainActivity;
import com.eshop.mvp.ui.fragment.DatePickerFragment;
import com.eshop.mvp.ui.widget.ExampleDialog;
import com.eshop.mvp.utils.PicChooserHelper;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 认证中心
 * ================================================
 */
public class MonthCompanyAuthActivity extends BaseSupportActivity<MonthAuthPresenter> implements MonthAuthContract.View,ExampleDialog.OnChangeListener,DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.name)
    EditText edit_name;
    @BindView(R.id.phone)
    EditText edit_phone;

    @BindView(R.id.close1)
    ImageView close1;
    @BindView(R.id.close2)
    ImageView close2;


    @BindView(R.id.add1)
    ImageView add1;

    @BindView(R.id.checkBox)
    CheckBox checkBox;

    @BindView(R.id.tip)
    TextView tip;

    @BindView(R.id.photo_card1)
    TextView photo_card1;
    @BindView(R.id.date_select)
    TextView date_select;

    @BindView(R.id.card1_img)
    ImageView card1_img;

    private PicChooserHelper picChooserHelper;

    private boolean isDateSelected = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMonthAuthComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                //.view(this)
                //.view(this)
                .monthAuthModule(new MonthAuthModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_company_month_auth; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("月结企业认证");
        toolbarBack.setVisibility(View.VISIBLE);

        mPresenter.getMonthMsg(BaseApp.loginBean.getToken());

        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0)close1.setVisibility(View.VISIBLE);
                else close1.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0)close2.setVisibility(View.VISIBLE);
                else close2.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    @OnClick({R.id.date_select,R.id.btn_pay,R.id.close1, R.id.close2, R.id.add1, R.id.photo_card1,R.id.card1_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.date_select:
                if(!checkBox.isChecked())
                    showDatePickerDialog();
                break;
            case R.id.btn_pay:
                if(veryfy()){
                    mPresenter.monthAdd(BaseApp.loginBean.getToken(),BaseApp.monthData);

                }

                break;
            case R.id.close1:
                edit_name.setText("");
                break;
            case R.id.close2:
                edit_phone.setText("");
                break;

            case R.id.add1:
            case R.id.card1_img:

                uploadImage();
                break;

            case R.id.photo_card1:

                uploadImage();
                break;


        }
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Cover);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(MonthCompanyAuthActivity.this)
                .title("上传身份证照片")
                .backgroundColorRes(R.color.white)
                .titleColorRes(R.color.color_3333)
                .itemsColorRes(R.color.color_3333)
                .negativeColorRes(R.color.color_3333)
                .negativeText(R.string.cancel_easy_photos)
                .inputType(InputType.TYPE_CLASS_TEXT )
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
                        }}
                }).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, resultCode, data);
    }

    private boolean veryfy(){

        if(edit_name.getText().toString().isEmpty()){
            showMessage("请输入营业执照名称.");
            return false;
        }

        if(edit_phone.getText().toString().isEmpty()){
            showMessage("请输入营业执照号码.");
            return false;
        }

        if(!checkBox.isChecked() && !isDateSelected){
            showMessage("请选择日期.");
            return false;
        }


        if(BaseApp.monthData==null || BaseApp.monthData.getLicenseImg()==null ){
            showMessage("请上传营业执照相片.");
            return false;
        }

        BaseApp.monthData.setBusinessLicenseName(edit_name.getText().toString());
        BaseApp.monthData.setBusinessLicenseNumber(edit_phone.getText().toString());

        if(checkBox.isChecked())
        {
            BaseApp.monthData.setLicenseIsLong("1");
            BaseApp.monthData.setLicenseDate("");
        }
        else{
            BaseApp.monthData.setLicenseIsLong("0");
        }

        return true;
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
    public void getAuthSuccess(Auth auth) {

    }

    @Override
    public void getMonthMsgSuccess(MonthMsg monthMsg) {
        BaseApp.monthMsg = monthMsg;
        tip.setVisibility(View.GONE);

        if(monthMsg!=null){
            setData(monthMsg);
        }
    }

    @Override
    public void getMonthMsgStatus(String status, String msg,MonthMsg monthMsg) {
        if(monthMsg != null){
            tip.setVisibility(View.VISIBLE);
            tip.setText(monthMsg.getPage2Info());
            setData(monthMsg);
        }

    }

    private void setData(MonthMsg monthMsg){
        if(monthMsg!=null){

            edit_name.setText(monthMsg.getBusinessLicenseName());
            edit_phone.setText(monthMsg.getBusinessLicenseNumber());
            if(monthMsg.getLicenseIsLong().equalsIgnoreCase("1"))checkBox.setChecked(true);
            else checkBox.setChecked(false);

            add1.setVisibility(View.GONE);
            photo_card1.setVisibility(View.GONE);

            Glide.with(this)
                    .load(monthMsg.getLicenseImg())
                    .into(card1_img);

            if(BaseApp.monthData==null)BaseApp.monthData = new MonthData();
            BaseApp.monthData.setLicenseImg(monthMsg.getLicenseImg());

            if(checkBox.isChecked())BaseApp.monthData.setLicenseIsLong("1");
            else BaseApp.monthData.setLicenseIsLong("0");

            BaseApp.monthData.setBusinessLicenseName(edit_name.getText().toString());
            BaseApp.monthData.setBusinessLicenseNumber(edit_phone.getText().toString());

            date_select.setText(monthMsg.getLicenseDate());
            isDateSelected=true;
            BaseApp.monthData.setLicenseDate(monthMsg.getLicenseDate());

        }
    }

    @Override
    public void monthAddSuccess() {
        showMessage("月结认证提交成功");
        finish();
    }

    @Override
    public void monthAddSuccess(String msg) {



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setTitle("提示")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       Intent intent = new Intent(MonthCompanyAuthActivity.this , MainActivity.class);
                       startActivity(intent);
                       finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void monthEditSuccess() {

    }

    @Override
    public void monthPaySuccess() {

    }

    @Override
    public void updateUserImageSuccess(String url) {
        if(BaseApp.monthData==null)BaseApp.monthData = new MonthData();

            add1.setVisibility(View.GONE);
            photo_card1.setVisibility(View.GONE);
            if (url != null) {
                Glide.with(this)
                        .load(url)//new MultiTransformation(
                        .into(card1_img);
                BaseApp.monthData.setLicenseImg(url);

            }


    }



    @Override
    public void onItemClick(String id, boolean isclose) {

    }


    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        date_select.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        isDateSelected=true;

        if(BaseApp.monthData==null)BaseApp.monthData = new MonthData();
        BaseApp.monthData.setLicenseDate(year+"-"+monthOfYear+"-"+dayOfMonth);
    }
}
