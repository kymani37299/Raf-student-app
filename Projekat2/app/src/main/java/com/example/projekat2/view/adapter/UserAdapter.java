package com.example.projekat2.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekat2.R;
import com.example.projekat2.model.User;
import com.example.projekat2.util.UserDiffCallback;
import com.example.projekat2.view.viewholder.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> data;
    private User currentUser;

    public UserAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<User> newData) {
        UserDiffCallback diffCallback = new UserDiffCallback(data,newData);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
        data.clear();
        data.addAll(newData);
        result.dispatchUpdatesTo(this);
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_user,parent,false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(data.get(position),currentUser);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
