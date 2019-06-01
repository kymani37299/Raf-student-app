package com.example.projekat2.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.example.projekat2.model.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public abstract class MessageAbstractViewHolder extends RecyclerView.ViewHolder {

    protected TextView tvText;
    protected TextView tvTime;

    public MessageAbstractViewHolder(@NonNull View v) {
        super(v);
        getReferences(v);
    }

    public abstract void getReferences(View v);

    public void bind(Message message, HashMap<String,String> indexNameMap) {
        tvText.setText(message.getContent());
        tvTime.setText(message.getTime());
    }
}
