package com.eshop.huanxin.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eshop.R;
import com.eshop.app.base.BaseApp;
import com.eshop.di.component.DaggerEaseChatComponent;
import com.eshop.di.component.DaggerHuanXinComponent;
import com.eshop.di.module.HuanXinModule;
import com.eshop.huanxin.Constant;
import com.eshop.mvp.contract.HuanXinContract;
import com.eshop.mvp.model.HuanXinModel;
import com.eshop.mvp.presenter.HuanXinPresenter;
import com.eshop.mvp.ui.activity.EaseChatActivity;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class NewChatRoomActivity extends BaseActivity implements HuanXinContract.View , ActivityLifecycleable {
    private EditText chatRoomNameEditText;
    private EditText introductionEditText;
    private ProgressDialog progressDialog;

//
//    @BindView(R.id.new_char_room_save_btn)
//    Button button;
    @Inject
    @Nullable
    HuanXinPresenter huanXinPresenter;


    @Override
    public void addChatRoomResult() {
        Intent intent = new Intent(NewChatRoomActivity.this, EaseChatActivity.class);
        intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
        intent.putExtra("userId", BaseApp.loginBean.getHuanxinId());
        startActivityForResult(intent, 0);

        progressDialog.dismiss();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//DaggerHuanXinComponent
        AppComponent appComponent = ArmsUtils.obtainAppComponentFromContext(this);
        DaggerHuanXinComponent.builder()
                .appComponent(appComponent)
                .huanXinModule(new HuanXinModule(this))
                .build()
                .inject(this);


        setContentView(R.layout.em_activity_new_chat_room);
        chatRoomNameEditText = (EditText) findViewById(R.id.edit_chat_room_name);
        introductionEditText = (EditText) findViewById(R.id.edit_chat_room_introduction);


        String st1 = getResources().getString(R.string.The_new_chat_room);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(st1);
        progressDialog.setCanceledOnTouchOutside(false);


        Button button = (Button)findViewById(R.id.new_char_room_save_btn);

        NewChatRoomActivity newChatRoomActivity = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newChatRoomActivity.save(view);
            }
        });

    }

    /**
     * @param v
     */
//    @OnClick({R.id.new_char_room_save_btn})
    public void save(View v) {
//        showMessage("start room");
        String name = chatRoomNameEditText.getText().toString();
        if (TextUtils.isEmpty(name)) {
            new EaseAlertDialog(this, R.string.Group_name_cannot_be_empty).show();
        } else {
//            String st1 = getResources().getString(R.string.The_new_chat_room);
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage(st1);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();


            final String chatRoomName = chatRoomNameEditText.getText().toString().trim();
            final String desc = introductionEditText.getText().toString();






//            if(huanXinModel != null){
//                Toast.makeText(this , "no null" , Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(this , "null" , Toast.LENGTH_LONG).show();
//
//            }

            if(huanXinPresenter != null){
                huanXinPresenter.addChatRoom(
                        desc,
                        Integer.toString(500),
                        chatRoomName,
                        BaseApp.loginBean.getHuanxinId()
                );
            }

//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        final EMChatRoom chatRoom = EMClient.getInstance().chatroomManager().createChatRoom(chatRoomName, desc, "welcome join chat", 500, null);
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                Intent intent = new Intent(NewChatRoomActivity.this, EaseChatActivity.class);
//                                intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
//                                intent.putExtra("userId", chatRoom.getId());
//                                startActivityForResult(intent, 0);
//
//                                progressDialog.dismiss();
//                                setResult(RESULT_OK);
//                                finish();
//                            }
//                        });
//                    } catch (final Exception e) {
//                        e.printStackTrace();
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                progressDialog.dismiss();
//                                final String st2 = getResources().getString(R.string.Failed_to_create_chat_room);
//                                Toast.makeText(NewChatRoomActivity.this, st2 + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                }
//            }).start();
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void showLoading() {
        if(progressDialog != null)
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        final String st2 = getResources().getString(R.string.Failed_to_create_chat_room);
        Toast.makeText(NewChatRoomActivity.this, st2 + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    @NonNull
    @Override
    public Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }
}
