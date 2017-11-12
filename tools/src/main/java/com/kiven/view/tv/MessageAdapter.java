package com.kiven.view.tv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kiven.tools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiven on 2017/11/10.
 * Details:
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private List<IMessage> data = new ArrayList<>();

    public MessageAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void addData(List<IMessage> data) {
        this.data.addAll(data);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMessage.TYPE_MESSAGE_RECEIVE:
                return new ReceiveViewHolder(inflater.inflate(R.layout.item_tv_receive_txt_message,
                        parent, false));
            case IMessage.TYPE_MESSAGE_SEND:
                return new SendViewHolder(inflater.inflate(R.layout.item_tv_send_txt_message,
                        parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AbsViewHolder) holder).onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
