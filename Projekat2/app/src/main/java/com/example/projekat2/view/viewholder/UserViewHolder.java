package com.example.projekat2.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.projekat2.ChatActivity;
import com.example.projekat2.R;
import com.example.projekat2.model.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private FrameLayout flMain;

    private Context context;

    public UserViewHolder(@NonNull View v) {
        super(v);
        tvName = v.findViewById(R.id.vh_user_name);
        flMain = v.findViewById(R.id.fl_vh_user);
        context = v.getContext();
    }

    public void bind(User user,User currentUser) {
        tvName.setText(user.getName());
        flMain.setOnClickListener((v) -> {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("this",currentUser.getIndex());
            i.putExtra("other",user.getIndex());
            context.startActivity(i);
        });
    }
}
