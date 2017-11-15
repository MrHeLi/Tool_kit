package com.kiven.view.tv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kiven.tools.R;

import java.util.List;

/**
 * Created by Kiven on 2017/11/10.
 * Details:
 */

public class TVChatView extends FrameLayout {

    private String TAG = "TVChatView";
    private TextView tv_title;
    private RecyclerView msgList;
    private List<IMessage> data;
    private MessageAdapter adapter;
    private EditText et_messge;
    private Button bt_send;

    public TVChatView(@NonNull Context context) {
        this(context, null);
    }

    public TVChatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TVChatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initView(context);
        initData(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.tv_chat, TVChatView.this);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        msgList = (RecyclerView) this.findViewById(R.id.rv_msgList);
        et_messge = (EditText) findViewById(R.id.et_message);
        bt_send = (Button) findViewById(R.id.bt_send);
        bt_send.setOnClickListener(sendListener);
    }

    private void initData(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        msgList.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(context);
        adapter.setOnNoneFocusedListtener(msgListNoFocusedListener);
        msgList.setAdapter(adapter);
        msgList.addOnLayoutChangeListener(layoutChangeListener);
        msgList.scrollToPosition(0);
        et_messge.requestFocus();
}

    public void setData(List<IMessage> data) {
        this.data = data;
        adapter.initData(this.data);
        adapter.notifyDataSetChanged();
    }

    public void setTile(String title) {
        tv_title.setText(title);
    }

    public View getFocusedItem() {
        if (msgList != null) {
            return msgList.getFocusedChild();
        }
        return null;
    }

    public void setInputFocus() {
        if (et_messge != null) {
            et_messge.requestFocus();
        }
    }

    public View getMessageListChildFocusView() {
        msgList.getNextFocusDownId();
        return msgList.getFocusedChild();
    }

    public boolean getMessageListFocuseChange() {
        if (msgList.getNextFocusDownId() == R.id.et_message) {
            return false;//表示失去焦点
        } else {
            return true;
        }
    }

    OnClickListener sendListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "send click");
            String msg = et_messge.getText().toString().trim();
            if (msg.isEmpty()) {
                Toast.makeText(TVChatView.this.getContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                IMessage iMessage = new IMessage();
                iMessage.type = IMessage.TYPE_MESSAGE_SEND;
                iMessage.detail = msg;
                adapter.addData(iMessage);
                msgList.scrollToPosition(0);
            }
        }
    };

    MessageAdapter.OnNoneFocusedListener msgListNoFocusedListener = new MessageAdapter.OnNoneFocusedListener() {
        @Override
        public void onNoneFocus() {
            setInputFocus();
        }
    };

    OnLayoutChangeListener layoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (bottom < oldBottom) {
                msgList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        msgList.scrollToPosition(0);
                    }

                },20);
            }

        }
    };

}
