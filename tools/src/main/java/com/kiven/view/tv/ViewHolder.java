package com.kiven.view.tv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kiven on 2017/11/10.
 * Details:
 */

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(T data);
}
