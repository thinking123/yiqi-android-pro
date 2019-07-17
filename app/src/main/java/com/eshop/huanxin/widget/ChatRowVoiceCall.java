package com.eshop.huanxin.widget;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.huanxin.Constant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

public class ChatRowVoiceCall extends EaseChatRow {

    private TextView contentvView;

    public ChatRowVoiceCall(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    com.hyphenate.easeui.R.layout.ease_row_received_voice_call : com.hyphenate.easeui.R.layout.ease_row_sent_voice_call, this);
            // video call
        }else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    com.hyphenate.easeui.R.layout.ease_row_received_video_call : com.hyphenate.easeui.R.layout.ease_row_sent_video_call, this);
        }
    }

    @Override
    protected void onFindViewById() {
        contentvView = (TextView) findViewById(com.hyphenate.easeui.R.id.tv_chatcontent);
    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        contentvView.setText(txtBody.getMessage());
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
    }
}
