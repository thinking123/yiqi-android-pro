package com.eshop.huanxin.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.eshop.R;
import com.eshop.huanxin.adapter.NewFriendsMsgAdapter;
import com.eshop.huanxin.db.InviteMessgeDao;
import com.eshop.huanxin.domain.InviteMessage;
import com.eshop.huanxin.utils.chatUtils;

import java.util.Collections;
import java.util.List;

/**
 * Application and notification
 *
 */
public class NewFriendsMsgActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_new_friends_msg);

        ListView listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);

//        chatUtils.setChatTitleBarStyle(titleBar);

    }

    public void back(View view) {
        finish();
    }
}
