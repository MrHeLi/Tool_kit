package com.hitv.viewtv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.kiven.view.tv.IMessage;
import com.kiven.view.tv.TVChatView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kiven on 2017/11/9.
 * Details:
 */

public class MessageChatActivity extends Activity {
    private String TAG = "MessageChatActivity";
    private TVChatView chatView;
    private RecyclerView contacts;
    private ContactAdapter contactAdapter;
    private List<IMessage> datas1 = new ArrayList<>();
    private List<IMessage> datas2 = new ArrayList<>();
    private List<String> contact = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        chatView = this.findViewById(R.id.chat_view);
        contacts = this.findViewById(R.id.rv_contact);
        chatView.setTile("嘉文四世");
        initData();
        initContact();
        chatView.setData(datas1);
    }

    private void initContact() {
        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contacts.setLayoutManager(llManager);
        contactAdapter = new ContactAdapter(this);
        contactAdapter.initData(contact);
        contactAdapter.setOnClickListener(new ContactAdapter.ContactClickListener() {
            @Override
            public void clickPosition(int index) {
                chatView.setTile(contact.get(index));
                if (index % 2 == 0) {
                    chatView.setData(datas1);
                } else {
                    chatView.setData(datas2);
                }
            }
        });
        contacts.setAdapter(contactAdapter);

    }

    private void initData() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            IMessage msg = new IMessage();
            int tmp = random.nextInt();
            if ((i * tmp) % 2 == 0) {
                msg.type = IMessage.TYPE_MESSAGE_RECEIVE;
            } else {
                msg.type = IMessage.TYPE_MESSAGE_SEND;
            }
            msg.detail = " data_1 this is a message " + i;
            datas1.add(msg);
        }

        for (int i = 0; i < 6; i++) {
            IMessage msg = new IMessage();
            int tmp = random.nextInt();
            if ((i * tmp) % 2 == 0) {
                msg.type = IMessage.TYPE_MESSAGE_RECEIVE;
            } else {
                msg.type = IMessage.TYPE_MESSAGE_SEND;
            }
            msg.detail = " data_2 哈哈哈哈哈" + i;
            datas2.add(msg);
        }

        for (int i = 0; i < 5; i++) {
            contact.add("name : " + i);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View focusedChild1 = contacts.getFocusedChild();
        Log.i(TAG, "" + focusedChild1);
        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (contacts.getFocusedChild() != null) {
                    chatView.setInputFocus();
                    return true;
                } else {
                    break;
                }
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                Log.i(TAG, " " + chatView.getMessageListChildFocusView());
//                if (chatView.getMessageListChildFocusView() != null) {
//                    if (!chatView.getMessageListFocuseChange()) {
//                        chatView.setInputFocus();
//                        return true;
//                    }
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//                return true;
        }

        return super.onKeyDown(keyCode,event);
    }
}
