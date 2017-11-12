package com.hitv.viewtv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.kiven.view.tv.IMessage;
import com.kiven.view.tv.TVChatView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiven on 2017/11/9.
 * Details:
 */

public class MessageChatActivity extends Activity {
    private TVChatView chatView;
    private List<IMessage> datas = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        chatView = this.findViewById(R.id.chat_view);
        chatView.setTile("嘉文四世");
        initData();
        chatView.setData(datas);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            IMessage msg = new IMessage();
            if (i % 2 == 0) {
                msg.type = IMessage.TYPE_MESSAGE_RECEIVE;
            } else {
                msg.type = IMessage.TYPE_MESSAGE_SEND;
            }
            msg.detail = "this is a message " + i;
            datas.add(msg);
        }
    }
}
