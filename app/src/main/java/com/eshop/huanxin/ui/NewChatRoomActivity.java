package com.eshop.huanxin.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.eshop.R;
import com.eshop.huanxin.Constant;
import com.eshop.mvp.ui.activity.EaseChatActivity;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.widget.EaseAlertDialog;

public class NewChatRoomActivity extends BaseActivity {
    private EditText chatRoomNameEditText;
    private EditText introductionEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_new_chat_room);
        chatRoomNameEditText = (EditText) findViewById(R.id.edit_chat_room_name);
        introductionEditText = (EditText) findViewById(R.id.edit_chat_room_introduction);
    }

    /**
     * @param v
     */
    public void save(View v) {
        String name = chatRoomNameEditText.getText().toString();
        if (TextUtils.isEmpty(name)) {
            new EaseAlertDialog(this, R.string.Group_name_cannot_be_empty).show();
        } else {
            String st1 = getResources().getString(R.string.The_new_chat_room);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(st1);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            final String chatRoomName = chatRoomNameEditText.getText().toString().trim();
            final String desc = introductionEditText.getText().toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final EMChatRoom chatRoom = EMClient.getInstance().chatroomManager().createChatRoom(chatRoomName, desc, "welcome join chat", 500, null);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(NewChatRoomActivity.this, EaseChatActivity.class);
                                intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
                                intent.putExtra("userId", chatRoom.getId());
                                startActivityForResult(intent, 0);

                                progressDialog.dismiss();
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    } catch (final Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                final String st2 = getResources().getString(R.string.Failed_to_create_chat_room);
                                Toast.makeText(NewChatRoomActivity.this, st2 + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    public void back(View view) {
        finish();
    }
}
