package com.example.projekat2.view.viewholder;

import android.view.View;

import com.example.projekat2.R;

import androidx.annotation.NonNull;

public class MessageSentViewHolder extends MessageAbstractViewHolder{

    public MessageSentViewHolder(@NonNull View v) {
        super(v);
    }

    @Override
    public void getReferences(View v) {
        tvText = v.findViewById(R.id.tv_vh_msg_sent_text);
        tvTime = v.findViewById(R.id.tv_vh_msg_sent_time);
    }
}
