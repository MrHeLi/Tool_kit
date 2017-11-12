package com.kiven.view.tv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kiven.tools.R;

import java.util.List;

/**
 * Created by Kiven on 2017/11/10.
 * Details:
 */

public class TVChatView extends FrameLayout {

    private TextView tv_title;
    private RecyclerView msgList;
    private List<IMessage> data;
    private MessageAdapter adapter;

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
        View.inflate(context, R.layout.tv_chat,TVChatView.this);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        msgList = (RecyclerView) this.findViewById(R.id.rv_msgList);
    }
    private void initData(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, true);//TODO 第三个参数尝试一下false看看效果。
        layoutManager.setStackFromEnd(true);//TODO 参数尝试一下false看看效果。
        msgList.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(context);
        msgList.setAdapter(adapter);

    }

    public void setData(List<IMessage> data) {
        this.data = data;
        adapter.addData(this.data);
        adapter.notifyDataSetChanged();
    }

    public void setTile(String title) {
        tv_title.setText(title);
    }
}
