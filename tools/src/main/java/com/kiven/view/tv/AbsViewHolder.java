package com.kiven.view.tv;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by SuperLi on 2017/11/12.
 */

public abstract class AbsViewHolder<T> extends RecyclerView.ViewHolder {
    public AbsViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(T t);
}
