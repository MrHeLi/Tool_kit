package com.kiven.view.tv;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kiven.tools.R;

import java.util.List;

/**
 * Created by Kiven on 2017/11/10.
 * Details:
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "MessageAdapter";
    private LayoutInflater inflater;
    private List<IMessage> data;

    public MessageAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public List<IMessage> getData() {
        return data;
    }

    public void initData(List<IMessage> data) {
        this.data= data;
    }

    public void addData(IMessage message) {
        this.data.add(0, message);
        notifyItemRangeInserted(0, 1);
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
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((AbsViewHolder) holder).onBind(data.get(position));
        holder.itemView.setFocusable(true);
        holder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        if (position == 0) {
                            if (noFocusedListener != null) {
                                noFocusedListener.onNoneFocus();
                            }
                            return true;
                        }
                        break;
                }
                return false;
            }
        });
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    focusStatus(v.findViewById(R.id.rl_msg_detail));
                } else {
                    normalStatus(v.findViewById(R.id.rl_msg_detail));
                }
            }

            private void focusStatus(View itemView) {
                if (itemView == null) {
                    return;
                }
                ViewCompat.animate(itemView).scaleX(0.95f).scaleY(1.10f).translationZ(0.5f).start();
            }

            private void normalStatus(View itemView) {
                if (itemView == null) {
                    return;
                }
                ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).translationZ(0).start();
            }

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    interface OnNoneFocusedListener {
        void onNoneFocus();
    }

    private OnNoneFocusedListener noFocusedListener;

    public void setOnNoneFocusedListtener(OnNoneFocusedListener listtener) {
        noFocusedListener = listtener;
    }
}
