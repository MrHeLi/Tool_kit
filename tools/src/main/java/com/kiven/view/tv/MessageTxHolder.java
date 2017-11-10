package com.kiven.view.tv;

import android.view.View;

/**
 * Created by Kiven on 2017/11/10.
 * Details:
 */

public class MessageTxHolder<MESSAGE extends IMessage> extends ViewHolder<MESSAGE> {

    public MessageTxHolder(View itemView) {
        super(itemView);

    }

    @Override
    public void onBind(MESSAGE message) {

    }
}
