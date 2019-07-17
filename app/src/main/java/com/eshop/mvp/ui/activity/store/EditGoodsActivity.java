package com.eshop.mvp.ui.activity.store;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.app.base.BaseSupportActivity;
import com.eshop.di.component.DaggerStoreManagerComponent;
import com.eshop.di.module.StoreManagerModule;
import com.eshop.mvp.contract.StoreManagerContract;
import com.eshop.mvp.http.entity.auth.MonthMsg;
import com.eshop.mvp.http.entity.category.CatBean;
import com.eshop.mvp.http.entity.home.BrandBean;
import com.eshop.mvp.http.entity.home.Const;
import com.eshop.mvp.http.entity.login.LoginBean;
import com.eshop.mvp.http.entity.product.ProductDetail;
import com.eshop.mvp.http.entity.product.StoreCatBean;
import com.eshop.mvp.http.entity.store.Audit;
import com.eshop.mvp.http.entity.store.Auth;
import com.eshop.mvp.http.entity.store.BankCards;
import com.eshop.mvp.http.entity.store.CashType;
import com.eshop.mvp.http.entity.store.PublishGoods;
import com.eshop.mvp.http.entity.store.QRCode;
import com.eshop.mvp.http.entity.store.StoreInfomation;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.http.entity.store.TransList;
import com.eshop.mvp.http.entity.store.Wallet;
import com.eshop.mvp.http.entity.store.WithDrawRecord;
import com.eshop.mvp.presenter.StoreManagerPresenter;
import com.eshop.mvp.ui.activity.BrandActivity;
import com.eshop.mvp.ui.activity.product.ProductListActivity;
import com.eshop.mvp.ui.activity.product.StoreCatActivity;
import com.eshop.mvp.ui.adapter.BrandBeanSectionAdapter;
import com.eshop.mvp.ui.adapter.ImageListAdapter;
import com.eshop.mvp.ui.widget.GoodsItemDecoration;
import com.eshop.mvp.ui.widget.RecommendItemDecoration;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.PicChooserHelper;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.SimpleSpinnerTextFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.eshop.mvp.utils.PicChooserHelper.PicType.Cover;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * 发布商品
 * ================================================
 */
public class EditGoodsActivity extends BaseSupportActivity<StoreManagerPresenter> implements StoreManagerContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.inner_cat)
    TextView inner_cat;
    @BindView(R.id.brand)
    TextView brand;
    @BindView(R.id.stock)
    TextView stock;
    @BindView(R.id.unit)
    TextView unit;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.freight)
    TextView freight;


    @BindView(R.id.temai)
    RadioButton temai;
    @BindView(R.id.miaomiao)
    RadioButton miaomiao;
    @BindView(R.id.miaosha)
    RadioButton miaosha;
    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.detail)
    EditText detail;

    @BindView(R.id.recyclerView1)
    RecyclerView mRecyclerView1;

    ImageListAdapter imageListAdapter1;

    @BindView(R.id.recyclerView2)
    RecyclerView mRecyclerView2;

    ImageListAdapter imageListAdapter2;

    MaterialDialog dialog;

    NiceSpinner nice_spinner_cat;
    NiceSpinner nice_spinner_sub;

    private int catid = 0;
    private List<CatBean> catBeans;
    private List<CatBean> subCatBeans;

    private CatBean selectCatBean;

    private int subcat_index = 0;

    private String storeId;
    private String goodsId;

    public static final int REQUEST_CAT = 99;
    public static final int REQUEST_BRAND = 98;
    private String storeColumnId;
    private String categoryName;
    private boolean usecat = false;
    private String brandId;

    private EditText priceEditText;

    private int goods_img_index = 0;

    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();

    private PicChooserHelper picChooserHelper;

    private ProductDetail mProductDetail;

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
        return R.layout.activity_edit_goods; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("编辑商品");
        toolbarBack.setVisibility(View.VISIBLE);
        if (!LoginUtils.isLogin(this)) {
            return;
        }
        storeId = getIntent().getStringExtra("id");
        goodsId = getIntent().getStringExtra("goodsId");

        if(storeId==null)storeId = BaseApp.loginBean.getStoreId()+"";

        if(goodsId!=null){
            mPresenter.getGoodDetail(BaseApp.loginBean.getToken(),goodsId);
        }

        initRecyclerView();

        initRecyclerView2();

    }

    private void initRecyclerView() {
        list1 = new ArrayList<>();
        list1.add("+");
        imageListAdapter1 = new ImageListAdapter(list1);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView1.setLayoutManager(manager);
        mRecyclerView1.addItemDecoration(new GoodsItemDecoration(20));


        imageListAdapter1.setOnClickItemListener(new ImageListAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(String type, int position) {
                if (type.equalsIgnoreCase("del")) {
                    if(list1.size()<8) {
                        list1.remove(list1.get(position));
                        imageListAdapter1.setNewData(list1);
                    }else{
                        list1.remove(list1.get(position));
                        imageListAdapter1.setNewData(list1);
                        list1.add("+");
                    }
                } else {
                    goods_img_index = 0;
                    uploadImage();
                }
            }
        });

        mRecyclerView1.setAdapter(imageListAdapter1);


    }

    private void initRecyclerView2() {
        list2 = new ArrayList<>();
        list2.add("+");
        imageListAdapter2 = new ImageListAdapter(list2);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView2.setLayoutManager(manager);
        mRecyclerView2.addItemDecoration(new GoodsItemDecoration(20));


        imageListAdapter2.setOnClickItemListener(new ImageListAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(String type, int position) {
                if (type.equalsIgnoreCase("del")) {

                    if(list2.size()<8) {
                        list2.remove(list2.get(position));
                        imageListAdapter2.setNewData(list2);
                    }else{
                        list2.remove(list2.get(position));
                        imageListAdapter2.setNewData(list2);
                        list2.add("+");
                    }
                } else {
                    goods_img_index = 1;
                    uploadImage();
                }
            }
        });


        mRecyclerView2.setAdapter(imageListAdapter2);


    }

    private boolean veryfy() {
        PublishGoods p = BaseApp.publishGoods;
        if (p.getCategoryOne() == null) {
            showMessage("请选择类目");
            return false;
        }
        if (p.getCategoryTwo() == null) {
            showMessage("请选择类目");
            return false;
        }
        if (list2.size()==1) {
            showMessage("请选择商品图片");
            return false;
        }else{
            StringBuilder sb = new StringBuilder();
            for(String url : list2) {
                sb.append(url).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            BaseApp.publishGoods.setBannerImg(sb.toString());
        }

        if (temai.isChecked()) BaseApp.publishGoods.setAppClassId(Const.SALE7_ID);
        else if (miaomiao.isChecked()) BaseApp.publishGoods.setAppClassId(Const.MIAOMIAOGOU_ID);
        else BaseApp.publishGoods.setAppClassId(Const.SALE_ID);

        if (title.getText().toString() == null || title.getText().toString().isEmpty()) {
            showMessage("请输入标题");
            return false;
        } else {
            BaseApp.publishGoods.setTitle(title.getText().toString());
        }

        if (p.getBrandId() == null) {
           // showMessage("请选择品牌");
           // return false;
            p.setBrandId("");
        }

        if (p.getStock() == null) {
            showMessage("请输入库存");
            return false;
        }

        if (p.getUnit() == null) {
            showMessage("请输入商品单位");
            return false;
        }

        if (p.getUnitPrice() == null) {
            showMessage("请输入商品价格");
            return false;
        }

        if (p.getFreightState().equalsIgnoreCase("1") && p.getFreight() == null) {
            showMessage("请输入运费");
            return false;
        }

        if (detail.getText().toString() == null || detail.getText().toString().isEmpty()) {
            showMessage("请输入商品详情");
            return false;
        } else {
            BaseApp.publishGoods.setDetails(detail.getText().toString());
        }

        if (list1.size()==1) {
            showMessage("请选择文本介绍图片");
            return false;
        }else{
            StringBuilder sb = new StringBuilder();
            for(String url : list1) {
                sb.append(url).append(",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            BaseApp.publishGoods.setDetailsImg(sb.toString());
        }

        BaseApp.publishGoods.setGoodsId(goodsId);

        return true;

    }

    @OnClick({R.id.cat_set1, R.id.cat_set2, R.id.cat_set_brand, R.id.cat_set_stock, R.id.cat_set_unit,
            R.id.cat_set_price, R.id.cat_set_freight, R.id.publish,
           })
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.publish:
                if (veryfy() && mPresenter != null) {
                    mPresenter.goodsPut(BaseApp.loginBean.getToken(), BaseApp.publishGoods);
                }
                break;

            case R.id.cat_set1:

                dialog = new MaterialDialog.Builder(EditGoodsActivity.this)

                        .customView(R.layout.dialog_cat, false)
                        .backgroundColorRes(R.color.white)
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                selectCatBean = subCatBeans.get(subcat_index);
                                // catid = selectCatBean.id;
                                BaseApp.publishGoods.setCategoryOne(catid + "");
                                BaseApp.publishGoods.setCategoryTwo(selectCatBean.id + "");
                                all.setText(selectCatBean.categoryName);
                                dialog.dismiss();
                            }
                        })
                        .build();

                nice_spinner_cat = dialog.getCustomView().findViewById(R.id.nice_spinner_cat);
                nice_spinner_sub = dialog.getCustomView().findViewById(R.id.nice_spinner_sub);

                nice_spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {


                            CatBean catBean = catBeans.get(position);
                            catid = catBean.id;

                            BaseApp.publishGoods.setCategoryOne(catid + "");

                            mPresenter.getCats(catid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                nice_spinner_sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        subcat_index = position;
                        // selectCatBean = subCatBeans.get(position);
                        // catid = selectCatBean.id;

                        // all.setText(selectCatBean.categoryName);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                if (mPresenter != null) {
                    catid = 0;
                    mPresenter.getCats(catid);
                }

                dialog.show();

                break;

            case R.id.cat_set2:
                Intent intent = new Intent(this, StoreCatActivity.class);
                intent.putExtra("id", storeId);
                intent.putExtra("type", "PublishGoodsActivity");
                startActivityForResult(intent, REQUEST_CAT);
                break;

            case R.id.cat_set_brand:
                Intent intent1 = new Intent(this, BrandActivity.class);
                intent1.putExtra("type", "select");
                startActivityForResult(intent1, REQUEST_BRAND);

                break;

            case R.id.cat_set_stock:

                dialog = new MaterialDialog.Builder(EditGoodsActivity.this)

                        .customView(R.layout.dialog_edit, false)
                        .title("商品库存")
                        .backgroundColorRes(R.color.white)
                        .titleColorRes(R.color.color_3333)
                        .positiveText("确定")
                        .negativeText("取消")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                closeSoftKeyboard(priceEditText);
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                stock.setText(priceEditText.getText().toString());
                                closeSoftKeyboard(priceEditText);

                                BaseApp.publishGoods.setStock(priceEditText.getText().toString());
                            }
                        })
                        .build();

                priceEditText = dialog.getCustomView().findViewById(R.id.input);
                priceEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                //自动弹出软键盘
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(priceEditText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });


                dialog.show();

                break;

            case R.id.cat_set_unit:
                dialog = new MaterialDialog.Builder(EditGoodsActivity.this)

                        .customView(R.layout.dialog_edit, false)
                        .title("商品单位")
                        .backgroundColorRes(R.color.white)
                        .titleColorRes(R.color.color_3333)
                        .positiveText("确定")
                        .negativeText("取消")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                closeSoftKeyboard(priceEditText);
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                unit.setText(priceEditText.getText().toString());
                                closeSoftKeyboard(priceEditText);
                                BaseApp.publishGoods.setUnit(priceEditText.getText().toString());
                            }
                        })
                        .build();

                priceEditText = dialog.getCustomView().findViewById(R.id.input);
                priceEditText.setInputType(InputType.TYPE_CLASS_TEXT);

                //自动弹出软键盘
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(priceEditText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });

                dialog.show();

                break;

            case R.id.cat_set_price:

                dialog = new MaterialDialog.Builder(EditGoodsActivity.this)

                        .customView(R.layout.dialog_edit, false)
                        .title("商品价格")
                        .backgroundColorRes(R.color.white)
                        .titleColorRes(R.color.color_3333)
                        .positiveText("确定")
                        .negativeText("取消")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                closeSoftKeyboard(priceEditText);
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                price.setText(priceEditText.getText().toString());
                                closeSoftKeyboard(priceEditText);

                                BaseApp.publishGoods.setUnitPrice(priceEditText.getText().toString());
                            }
                        })
                        .build();

                priceEditText = dialog.getCustomView().findViewById(R.id.input);
                listenerMoney(priceEditText);

                //自动弹出软键盘
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(priceEditText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });

                dialog.show();

                break;

            case R.id.cat_set_freight:

                dialog = new MaterialDialog.Builder(EditGoodsActivity.this)

                        .customView(R.layout.dialog_edit_freight, false)
                        .title("运费")
                        .backgroundColorRes(R.color.white)
                        .titleColorRes(R.color.color_3333)
                        .positiveText("确定")
                        .negativeText("取消")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                closeSoftKeyboard(priceEditText);
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                closeSoftKeyboard(priceEditText);
                                RadioGroup radioGroup = dialog.getCustomView().findViewById(R.id.radioGroup);

                                if (radioGroup.getCheckedRadioButtonId() == R.id.saller) {
                                    BaseApp.publishGoods.setFreightState("0");
                                    freight.setText("卖家承担运费");
                                } else {
                                    BaseApp.publishGoods.setFreightState("1");
                                    BaseApp.publishGoods.setFreight(priceEditText.getText().toString());
                                    freight.setText(priceEditText.getText().toString());
                                }


                            }
                        })
                        .build();

                priceEditText = dialog.getCustomView().findViewById(R.id.input);
                listenerMoney(priceEditText);


                //自动弹出软键盘
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    public void onShow(DialogInterface dialog) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(priceEditText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });

                dialog.show();

                break;
        }
    }

    private void closeSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
        showMessage("发布成功");
        finish();
    }

    @Override
    public void goodsDelSuccess() {

    }

    @Override
    public void goodsPutSuccess() {
        showMessage("编辑商品提交成功");
        BaseApp.isGoodsNeedRefresh = true;
        finish();
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

        if (BaseApp.publishGoods == null) BaseApp.publishGoods = new PublishGoods();
        if (goods_img_index == 0) {
            list1.remove(list1.size() - 1);
            list1.add(url);
            if (list1.size() < 8)list1.add("+");
            imageListAdapter1.setNewData(list1);
        }

        if (goods_img_index == 1) {
            list2.remove(list2.size() - 1);
            list2.add(url);
            if (list2.size() < 8)list2.add("+");
            imageListAdapter2.setNewData(list2);
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
            subcat_index = 0;
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
    public void getGoodDetailSuccess(ProductDetail goods) {
        mProductDetail = goods;

        setEditData();


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

    private void setEditData(){

        ProductDetail good = mProductDetail;

        if(mProductDetail==null)return;

        BaseApp.publishGoods = new PublishGoods();

        list2 = good.rotationChartList;
       // if(list2.size()<8)list2.add("+");
        imageListAdapter2.setNewData(list2);

        list1 = good.detailMapList;
       // if(list1.size()<8)list1.add("+");
        imageListAdapter1.setNewData(list1);

        String appClassId = good.appClassId+"";
        if(appClassId.equalsIgnoreCase(Const.SALE7_ID)){
            temai.setChecked(true);
        }else if(appClassId.equalsIgnoreCase(Const.MIAOMIAOGOU_ID)){
            miaomiao.setChecked(true);
        }else {
            miaosha.setChecked(true);
        }

        title.setText(good.title);
        BaseApp.publishGoods.setTitle(good.title);

        price.setText(good.unitPrice+"");
        BaseApp.publishGoods.setUnitPrice(good.unitPrice+"");

        BaseApp.publishGoods.setFreightState(good.freightState+"");
        BaseApp.publishGoods.setFreight(good.freight);

        if(good.freightState==0){
            freight.setText(good.freightStateMsg);
        }else{
            freight.setText(good.freight+"元");
        }

        detail.setText(good.details);
        BaseApp.publishGoods.setDetails(good.details);

        BaseApp.publishGoods.setGoodsId(goodsId);

    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (picChooserHelper != null)
            picChooserHelper.onActivityResult(requestCode, responseCode, data);

        if (requestCode == REQUEST_CAT) {
            if (responseCode == RESULT_OK) {
                if (data != null) {
                    storeColumnId = data.getStringExtra("id");
                    categoryName = data.getStringExtra("name");
                    usecat = data.getBooleanExtra("usecat", false);
                    inner_cat.setText(categoryName);

                    BaseApp.publishGoods.setCategoryThree(storeColumnId);
                }
            }
        }

        if (requestCode == REQUEST_BRAND) {
            if (responseCode == RESULT_OK) {
                if (data != null) {
                    brandId = data.getStringExtra("id");
                    String brand_txt = data.getStringExtra("name");

                    brand.setText(brand_txt);

                    BaseApp.publishGoods.setBrandId(brandId);
                }
            }
        }
    }

    /**
     * 监听输入最小金额为0.01
     * 且只能输入两位小数
     */
    private void listenerMoney(EditText etGoodPrice) {
        etGoodPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    return;
                }
                if (s.equals(".") && s.toString().length() == 0) {
                    s = "0.";
                }
                // 判断小数点后只能输入两位
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        etGoodPrice.setText(s);
                        etGoodPrice.setSelection(s.length());
                    }
                }
                //如果第一个数字为0，第二个不为点，就不允许输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etGoodPrice.setText(s.subSequence(0, 1));
                        etGoodPrice.setSelection(1);
                        return;
                    }
                    if (s.toString().length() == 4) {
                        //针对输入0.00的特殊处理
                        if (Double.valueOf(s.toString()) < 0.01) {
                            Toast.makeText(mContext, "最小为0.01", Toast.LENGTH_SHORT).show();
                            etGoodPrice.setText("0.01");
                            etGoodPrice.setSelection(etGoodPrice.getText().toString().trim().length());
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void uploadImage() {
        picChooserHelper = new PicChooserHelper(this, Cover);
        picChooserHelper.setOnChooseResultListener(url -> {
            if (mPresenter != null) {
                mPresenter.updateUserImage(url);
            }
        });

        final String items[] = {"相机", "图库"};
        new MaterialDialog.Builder(EditGoodsActivity.this)
                .title("图片上传")
                .backgroundColorRes(R.color.white)
                .titleColorRes(R.color.color_3333)
                .itemsColorRes(R.color.color_3333)
                .negativeColorRes(R.color.color_3333)
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

}
