package com.eshop.mvp.ui.fragments;


import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bin.david.dialoglib.BaseDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eshop.app.base.BaseApp;
import com.eshop.mvp.http.entity.AppData;
import com.eshop.mvp.http.entity.product.CollectNum;
import com.eshop.mvp.http.entity.store.StoreState;
import com.eshop.mvp.ui.activity.login.UserInfoActivity;
import com.eshop.mvp.ui.activity.order.AfterSaleActivity;
import com.eshop.mvp.ui.activity.order.OrderActivity;
import com.eshop.mvp.ui.activity.product.StoreActivity;
import com.eshop.mvp.ui.activity.set.HelpActivity;
import com.eshop.mvp.ui.activity.set.SetActivity;
import com.eshop.mvp.ui.activity.store.AuthActivity;
import com.eshop.mvp.ui.activity.store.CollectionActivity;
import com.eshop.mvp.ui.activity.store.StoreManagerActivity;
import com.eshop.mvp.ui.activity.store.StoreStateActivity;
import com.eshop.mvp.ui.widget.ShareDialog;
import com.eshop.mvp.ui.widget.SubCatDialog;
import com.eshop.mvp.utils.LoginUtils;
import com.eshop.mvp.utils.Util;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.eshop.R;
import com.eshop.app.base.BaseSupportFragment;
import com.eshop.di.component.DaggerSelfComponent;
import com.eshop.di.module.SelfModule;
import com.eshop.mvp.contract.SelfContract;
import com.eshop.mvp.http.entity.login.UserBean;
import com.eshop.mvp.presenter.SelfPresenter;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;
import per.goweii.anylayer.Alignment;
import per.goweii.anylayer.AnimHelper;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.LayerManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelfFragment extends BaseSupportFragment<SelfPresenter> implements SelfContract.View, View.OnClickListener {


    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.guan)
    TextView guanzhu;
    @BindView(R.id.shou)
    TextView shoucang;

    @BindView(R.id.toolbar_back)
    View toolbar_back;
    // private PicChooserHelper picChooserHelper;

    private IWXAPI api;

    @BindView(R.id.dianpuguanli_txt)
    TextView dianpuguanli_txt;

    @BindView(R.id.dianpuguanli_arror)
    ImageView dianpuguanli_arror;

    @BindView(R.id.dianpuguanli)
    ImageView dianpuguanli;

    @BindView(R.id.line3)
    View line3;

    String storeStatus;

    public SelfFragment() {
        // Required empty public constructor
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelfComponent.builder()
                .appComponent(appComponent)
                .selfModule(new SelfModule(this))
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar_back.setVisibility(View.GONE);
        if (LoginUtils.isLogin(getActivity())) {
            if (mPresenter != null){
                mPresenter.getCollectionNum(BaseApp.loginBean.getToken());
                mPresenter.state(BaseApp.loginBean.getToken());
            }
        } else {
            dianpuguanli.setVisibility(View.GONE);
            dianpuguanli_arror.setVisibility(View.GONE);
            dianpuguanli_txt.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
        }
        api = WXAPIFactory.createWXAPI(getActivity(), AppData.WECHAT_APPID, true);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (BaseApp.loginBean == null) {
            tv_name.setText("");
            guanzhu.setText("0");
            shoucang.setText("0");
            Glide.with(this)
                    .load(R.drawable.touxiang)//new MultiTransformation(
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                    .into(iv_header);
        } else {
            if (mPresenter != null) mPresenter.getCollectionNum(BaseApp.loginBean.getToken());

        }

        if (LoginUtils.isLogin(getActivity())) {
            dianpuguanli.setVisibility(View.VISIBLE);
            dianpuguanli_arror.setVisibility(View.VISIBLE);
            dianpuguanli_txt.setVisibility(View.VISIBLE);
            line3.setVisibility(View.VISIBLE);
        } else {
            dianpuguanli.setVisibility(View.GONE);
            dianpuguanli_arror.setVisibility(View.GONE);
            dianpuguanli_txt.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
        }
    }

    private void setData() {
        if (BaseApp.loginBean != null) {
            tv_name.setText(BaseApp.loginBean.getNickNmae());
            String head_url = BaseApp.loginBean.getLogo();
            if (head_url != null)
                Glide.with(this)
                        .load(head_url)//new MultiTransformation(
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(120)))
                        .into(iv_header);

            // guanzhu.setText(BaseApp.loginBean.getCollectStoreNum()+"");
            // shoucang.setText(BaseApp.loginBean.getCollectProductNum()+"");

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick({R.id.iv_header, R.id.set, R.id.all, R.id.daifu, R.id.daifa,
            R.id.daishou, R.id.wancheng, R.id.tuikuan,
            R.id.mystore,
            R.id.store_mgr,
            R.id.real_name,
            R.id.textView12,
            R.id.help, R.id.shoucang,
            R.id.hetong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mystore:

                if (LoginUtils.isLogin(getActivity())) {

                    if (storeStatus==null || storeStatus.equalsIgnoreCase("6003")) {
                        String storeId = BaseApp.loginBean.getStoreId() + "";
                        Intent intent = new Intent(getActivity(), StoreActivity.class);
                        intent.putExtra("id", storeId);
                        intent.putExtra("isstore", true);
                        startActivity(intent);
                    } else {
                        Intent intent4 = new Intent(getActivity(), StoreStateActivity.class);
                        startActivity(intent4);
                    }
                } else {
                    LoginUtils.login(getActivity());
                }
                break;
            case R.id.tuikuan:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent7 = new Intent(getActivity(), AfterSaleActivity.class);
                    startActivity(intent7);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;
            case R.id.iv_header:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent3 = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent3);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;
            case R.id.shoucang:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent11 = new Intent(getActivity(), CollectionActivity.class);
                    intent11.putExtra("position",0);
                    startActivity(intent11);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;
            case R.id.textView12: //guangzhu
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent11 = new Intent(getActivity(), CollectionActivity.class);
                    intent11.putExtra("position",1);
                    startActivity(intent11);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;
            case R.id.set:

                Intent intent3 = new Intent(getActivity(), SetActivity.class);
                startActivity(intent3);


                break;
            case R.id.all:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent1 = new Intent(getActivity(), OrderActivity.class);
                    startActivity(intent1);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;

            case R.id.daifu:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent_d = new Intent(getActivity(), OrderActivity.class);
                    intent_d.putExtra("position", 1);
                    startActivity(intent_d);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;

            case R.id.daifa:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent_a = new Intent(getActivity(), OrderActivity.class);
                    intent_a.putExtra("position", 2);
                    startActivity(intent_a);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;

            case R.id.daishou:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent_s = new Intent(getActivity(), OrderActivity.class);
                    intent_s.putExtra("position", 3);
                    startActivity(intent_s);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;

            case R.id.wancheng:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent_w = new Intent(getActivity(), OrderActivity.class);
                    intent_w.putExtra("position", 4);
                    startActivity(intent_w);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;

            case R.id.store_mgr:

                if (LoginUtils.isLogin(getActivity())) {

                    if (storeStatus==null || storeStatus.equalsIgnoreCase("6003")) {
                        Intent intent2 = new Intent(getActivity(), StoreManagerActivity.class);
                        startActivity(intent2);
                    } else {
                        Intent intent4 = new Intent(getActivity(), StoreStateActivity.class);
                        startActivity(intent4);
                    }
                } else {
                    LoginUtils.login(getActivity());
                }
                break;

            case R.id.real_name:
                if (LoginUtils.isLogin(getActivity())) {
                    Intent intent4 = new Intent(getActivity(), AuthActivity.class);
                    startActivity(intent4);
                } else {
                    LoginUtils.login(getActivity());
                }

                break;

            case R.id.help:

                Intent intent6 = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent6);

                break;

            case R.id.hetong:

                BaseDialog.Builder builder = new BaseDialog.Builder(getActivity());
                builder.setGravity(Gravity.BOTTOM);
                builder.setFillWidth(true).setContentViewBackground(R.drawable.dialog_white_bg).setMargin(0, 0, 0, 0); //设置margin;
                shareDialog = new ShareDialog(this, builder);
                shareDialog.show(getActivity());


                break;


        }
    }

    public void uploadImage() {
        /** picChooserHelper = new PicChooserHelper(this, Avatar);
         picChooserHelper.setOnChooseResultListener(url -> {
         if (mPresenter != null) {
         mPresenter.updateUserImage(url);
         }
         });

         final String items[] = {"相机", "图库"};
         AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity, 3);
         builder.setTitle("头像上传");
         builder.setIcon(R.mipmap.ic_launcher);
         builder.setItems(items, (dialog, which) -> {
         switch (which) {
         case 0:
         picChooserHelper.takePicFromCamera();
         break;
         case 1:
         picChooserHelper.takePicFromAlbum();
         break;
         }
         });
         builder.create().show();*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if (picChooserHelper != null)
        //     picChooserHelper.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void getCollectionNumSuccess(CollectNum data) {
        if (data != null) {
            guanzhu.setText(data.getFollowNum());
            shoucang.setText(data.getCollectionNum());
        }
    }

    @Override
    public void stateSuccess(StoreState storeState) {

    }

    @Override
    public void stateResult(String status, String msg, StoreState storeState) {
        BaseApp.storeState = storeState;
        BaseApp.storeStatus = status;
        BaseApp.storeMsg = msg;

        storeStatus = status;
        if(status.equalsIgnoreCase("6001")){
            //店铺未开通
            dianpuguanli.setVisibility(View.GONE);
            dianpuguanli_arror.setVisibility(View.GONE);
            dianpuguanli_txt.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
        }
    }

    ShareDialog shareDialog;

    @Override
    public void onClick(View v) {
        shareDialog.dismiss();
        if (v.getId() == R.id.wxfriend) {
            //share_text("测试分享文本",false);
            share_url(false);
        } else if (v.getId() == R.id.wxshare) {
            share_url(true);
        } else {

        }

    }

    private void share_text(String text, boolean isTimelineCb) {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        //req.openId = getOpenId();
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    private void share_url(boolean isTimelineCb) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.yqscmall.com/static/index.html";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "一器商城";
        msg.description = "下载一器商城App获得更多惊喜";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_launcher);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        //req.openId = getOpenId();
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


}
