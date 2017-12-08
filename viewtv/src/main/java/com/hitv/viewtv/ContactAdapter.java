package com.hitv.viewtv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiven on 2017/11/13.
 * Details:
 */

public class ContactAdapter extends RecyclerView.Adapter {

    private String TAG = "ContactAdapter";

    private LayoutInflater inflater;
    private List<String> datas = new ArrayList<>();

    public ContactAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void initData(List<String> data) {
        datas.addAll(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(inflater.inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ContactViewHolder mHolder = ((ContactViewHolder) holder);
        mHolder.onBind();
        mHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.clickPosition(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;
        public ContactViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.ib_contact);
        }

        public void onBind() {}
    }

    interface ContactClickListener{
        void clickPosition(int index);
    }

    public ContactClickListener clickListener;

    public void setOnClickListener(ContactClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
