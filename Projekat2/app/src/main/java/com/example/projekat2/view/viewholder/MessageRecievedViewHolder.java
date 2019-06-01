package com.example.projekat2.view.viewholder;

import android.view.View;

import com.example.projekat2.R;

import androidx.annotation.NonNull;

public class MessageRecievedViewHolder extends MessageAbstractViewHolder {
    public MessageRecievedViewHolder(@NonNull View v) {
        super(v);
    }

    @Override
    public void getReferences(View v) {
        tvText = v.findViewById(R.id.tv_vh_msg_recieved_text);
        tvTime = v.findViewById(R.id.tv_vh_msg_recieved_time);
    }
}
