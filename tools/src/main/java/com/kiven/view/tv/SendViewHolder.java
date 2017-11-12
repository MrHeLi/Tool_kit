package com.kiven.view.tv;

import android.view.View;
import android.widget.TextView;

import com.kiven.tools.R;

/**
 * Created by SuperLi on 2017/11/12.
 */

public class SendViewHolder extends AbsViewHolder<IMessage> {
    TextView msgView;
    public SendViewHolder(View itemView) {
        super(itemView);
        msgView = (TextView) itemView.findViewById(R.id.tv_msgitem_message);
    }

    @Override
    public void onBind(IMessage message) {
        msgView.setText(message.detail);
    }
}
