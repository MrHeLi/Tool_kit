package com.hitv.viewtv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kiven.view.tv.TVChatView;

/**
 * Created by Kiven on 2017/11/9.
 * Details:
 */

public class MessageChatActivity extends Activity {
    private TVChatView chatView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatView = this.findViewById(R.id.chat_view);
        chatView.setTile("嘉文四世");
    }
}
