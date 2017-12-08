package com.hitv.viewtv.test;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitv.viewtv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiven on 2017/11/18.
 * Details:
 */

public class TestAdapter extends RecyclerView.Adapter<MyViewHolder> {

    LayoutInflater inflater;
    List<String> data = new ArrayList<>();

    public TestAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        for (int i = 0; i < 20; i++) {
            data.add("" + i);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {//有焦点放大
                    ViewCompat.animate(view).translationZ(20);
                } else {//无焦点缩小
                    ViewCompat.animate(view).translationZ(10);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
