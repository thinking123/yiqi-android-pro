package com.eshop.mvp.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshop.app.base.BaseSupportFragment;
import com.eshop.mvp.http.entity.home.HotLineBean;
import com.eshop.mvp.ui.activity.MainActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eshop.di.component.DaggerHotLineComponent;
import com.eshop.mvp.contract.HotLineContract;
import com.eshop.mvp.presenter.HotLinePresenter;

import com.eshop.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 12:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HotLineFragment extends BaseSupportFragment<HotLinePresenter> implements HotLineContract.View {

    @BindView(R.id.title1)
    TextView title1;

    @BindView(R.id.title2)
    TextView title2;

    @BindView(R.id.title3)
    TextView title3;

    @BindView(R.id.phone1)
    TextView phone1;

    @BindView(R.id.phone2)
    TextView phone2;

    @BindView(R.id.phone3)
    TextView phone3;

    private int pos = 1;

    @OnClick({R.id.btn_call_one, R.id.btn_call_two, R.id.btn_call_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_call_one:
                //callPhone(phone1.getText().toString());
                pos=1;
                break;
            case R.id.btn_call_two:
                //callPhone(phone2.getText().toString());
                pos=2;
                break;

            case R.id.btn_call_three:
                //callPhone(phone3.getText().toString());
                pos=3;
                break;
        }
        requestPermissions();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(getActivity());
        rxPermission
                .requestEach(
                        Manifest.permission.CALL_PHONE
                       )
                .subscribe(permission -> {
                    if (permission.granted) {
                        call(pos);
                        // 用户已经同意该权限
                        Timber.e("%s is granted.", permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Timber.d("%s is denied. More info should be provided.", permission.name);
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Timber.e("%s is denied.", permission.name);
                    }
                });


    }

    private void call(int pos){
        switch (pos){
            case 1:
                callPhone(phone1.getText().toString());
                break;
            case 2:
                callPhone(phone2.getText().toString());
                break;
            case 3:
                callPhone(phone3.getText().toString());
                break;
        }
    }
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    public static HotLineFragment newInstance() {
        HotLineFragment fragment = new HotLineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHotLineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_line, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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
    public void getHotLineResult(List<HotLineBean> data) {
        int k=0;
        for(HotLineBean hot:data){
            k++;
            if(k==1){
                title1.setText(hot.hotlinName);
                phone1.setText(transPhone(hot.hotlinPhone));
            }

            if(k==2){
                title2.setText(hot.hotlinName);
                phone2.setText(transPhone(hot.hotlinPhone));
            }

            if(k==3){
                title3.setText(hot.hotlinName);
                phone3.setText(transPhone(hot.hotlinPhone));
            }
        }


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        assert mPresenter != null;
        mPresenter.getHotLine();

    }

    private String transPhone(String phone ){
        if(phone==null | phone.isEmpty())
            return phone;
        String s1 = phone.substring(0,3);
        String s2 = phone.substring(3,6);
        String s3 = phone.substring(6);
        return  s1+"-"+s2+"-"+s3;
    }

}
